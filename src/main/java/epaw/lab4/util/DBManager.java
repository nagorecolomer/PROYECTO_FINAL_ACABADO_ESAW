package epaw.lab4.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.stream.Collectors;

public class DBManager {

	private static DBManager instance;
	private Connection connection = null;
	private static final String DB_FILE = "lab4.db";

	private DBManager() {
		try {
			// SQLite connection
			Class.forName("org.sqlite.JDBC");
			boolean dbExists = Files.exists(Paths.get(DB_FILE));
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);

			// Enable foreign keys in SQLite
			try (Statement stmt = connection.createStatement()) {
				stmt.execute("PRAGMA foreign_keys = ON;");
			}

			if (!dbExists) {
				initDatabase();
			} else {
				migrateDatabase();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

	private void initDatabase() throws Exception {
		String schemaPath = "DB.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(schemaPath))) {
			String schema = reader.lines().collect(Collectors.joining("\n"));
			String[] statements = schema.split(";");
			try (Statement stmt = connection.createStatement()) {
				for (String sql : statements) {
					if (!sql.trim().isEmpty()) {
						stmt.execute(sql);
					}
				}
			}
		}
	}

	private void migrateDatabase() {
		try {
			if (!tableExists("users")) {
				initDatabase();
				return;
			}

			addColumnIfMissing("users", "age", "INTEGER");
			addColumnIfMissing("users", "email", "VARCHAR(255)");
			addColumnIfMissing("users", "role", "VARCHAR(50)");
			addColumnIfMissing("users", "biography", "VARCHAR(500)");
			addColumnIfMissing("users", "featuredTweetId", "INTEGER");
			
			createTableIfMissing("likes",
				"CREATE TABLE likes (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"tweetId INTEGER NOT NULL, " +
				"userId INTEGER NOT NULL, " +
				"timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, " +
				"FOREIGN KEY(tweetId) REFERENCES tweets(id) ON DELETE CASCADE, " +
				"FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE, " +
				"UNIQUE(tweetId, userId)" +
				");");

			migrateLegacyCommentsIntoTweets();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void migrateLegacyCommentsIntoTweets() throws SQLException {
		if (!tableExists("comments")) {
			return;
		}

		String insertSql = "INSERT INTO tweets (uid, postdatetime, content, pid) " +
				"SELECT c.userId, c.timestamp, c.content, c.tweetId " +
				"FROM comments c " +
				"WHERE EXISTS (SELECT 1 FROM tweets p WHERE p.id = c.tweetId) " +
				"AND NOT EXISTS (" +
				"SELECT 1 FROM tweets t " +
				"WHERE t.uid = c.userId AND t.pid = c.tweetId " +
				"AND t.content = c.content AND t.postdatetime = c.timestamp" +
				")";

		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(insertSql);
			stmt.execute("DROP TABLE comments");
		}
	}

	private void createTableIfMissing(String tableName, String createTableSQL) throws SQLException {
		if (!tableExists(tableName)) {
			try (Statement stmt = connection.createStatement()) {
				stmt.execute(createTableSQL);
			}
		}
	}

	private boolean tableExists(String tableName) throws SQLException {
		String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, tableName);
			try (ResultSet rs = statement.executeQuery()) {
				return rs.next();
			}
		}
	}

	private void addColumnIfMissing(String tableName, String columnName, String columnDefinition) throws SQLException {
		if (columnExists(tableName, columnName)) {
			return;
		}

		String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnDefinition;
		try (Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		}
	}

	private boolean columnExists(String tableName, String columnName) throws SQLException {
		String query = "PRAGMA table_info(" + tableName + ")";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				if (columnName.equalsIgnoreCase(rs.getString("name"))) {
					return true;
				}
			}
		}
		return false;
	}

	public PreparedStatement prepareStatement(String query) throws SQLException {
		return connection.prepareStatement(query);
	}

	public void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}