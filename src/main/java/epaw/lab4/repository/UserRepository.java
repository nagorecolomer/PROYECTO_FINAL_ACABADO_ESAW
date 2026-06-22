package epaw.lab4.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epaw.lab4.model.User;

public class UserRepository extends BaseRepository {

    private static UserRepository instance;

    private UserRepository() {
        super();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public boolean existsByUsername(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE name = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkLogin(User user) {
        String query = "SELECT id, name, password, age, email, role, biography, picture, featuredTweetId from users where name=? AND password=?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    int age = rs.getInt("age");
                    if (!rs.wasNull()) {
                        user.setAge(age);
                    }
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setBiography(rs.getString("biography"));
                    user.setPicture(rs.getString("picture"));
                    int featuredTweetId = rs.getInt("featuredTweetId");
                    if (!rs.wasNull()) {
                        user.setFeaturedTweetId(featuredTweetId);
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void save(User user) {
        String query = "INSERT INTO users (name, password, age, email, role, biography, picture, featuredTweetId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            if (user.getAge() != null) {
                statement.setInt(3, user.getAge());
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getBiography());
            statement.setString(7, user.getPicture());
            if (user.getFeaturedTweetId() != null) {
                statement.setInt(8, user.getFeaturedTweetId());
            } else {
                statement.setNull(8, java.sql.Types.INTEGER);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        String query = "UPDATE users SET name = ?, age = ?, email = ?, role = ?, biography = ?, picture = ?, featuredTweetId = ? WHERE id = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getName());
            if (user.getAge() != null) {
                statement.setInt(2, user.getAge());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
            }
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getBiography());
            statement.setString(6, user.getPicture());
            if (user.getFeaturedTweetId() != null) {
                statement.setInt(7, user.getFeaturedTweetId());
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
            }
            statement.setInt(8, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByName(String name) {
        String query = "SELECT id, name, password, age, email, role, biography, picture, featuredTweetId FROM users WHERE name = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                int age = rs.getInt("age");
                if (!rs.wasNull()) {
                    user.setAge(age);
                }
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setBiography(rs.getString("biography"));
                user.setPicture(rs.getString("picture"));
                int featuredTweetId = rs.getInt("featuredTweetId");
                if (!rs.wasNull()) {
                    user.setFeaturedTweetId(featuredTweetId);
                }
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findByNameExcludeId(String name, Integer excludeId) {
        String query = "SELECT id, name, password, age, email, role, biography, picture, featuredTweetId FROM users WHERE name = ? AND id <> ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setInt(2, excludeId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                int age = rs.getInt("age");
                if (!rs.wasNull()) {
                    user.setAge(age);
                }
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setBiography(rs.getString("biography"));
                user.setPicture(rs.getString("picture"));
                int featuredTweetId = rs.getInt("featuredTweetId");
                if (!rs.wasNull()) {
                    user.setFeaturedTweetId(featuredTweetId);
                }
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Find a user by their name
    public Optional<User> findById(Integer id) {
        String query = "SELECT id, name, age, email, role, biography, picture, featuredTweetId FROM users WHERE id = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
        	statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                User user = new User();
	                user.setId(rs.getInt("id"));
	                user.setName(rs.getString("name"));
                    int age = rs.getInt("age");
                    if (!rs.wasNull()) {
                        user.setAge(age);
                    }
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setBiography(rs.getString("biography"));
	                user.setPicture(rs.getString("picture"));
                    int featuredTweetId = rs.getInt("featuredTweetId");
                    if (!rs.wasNull()) {
                        user.setFeaturedTweetId(featuredTweetId);
                    }
	                return Optional.of(user);
	            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    // Retrieve all users from the database
    public Optional<List<User>> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, name, age, email, role, biography, picture, featuredTweetId FROM users";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                User user = new User();
	                user.setId(rs.getInt("id"));
	                user.setName(rs.getString("name"));
                    int age = rs.getInt("age");
                    if (!rs.wasNull()) {
                        user.setAge(age);
                    }
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setBiography(rs.getString("biography"));
	                user.setPicture(rs.getString("picture"));
                    int featuredTweetId = rs.getInt("featuredTweetId");
                    if (!rs.wasNull()) {
                        user.setFeaturedTweetId(featuredTweetId);
                    }
	                users.add(user);
	            }
	            return Optional.of(users);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    // Delete user by id
    public void deleteById(Integer id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	// Follow a user
	public void followUser(Integer uid, Integer fid) {
		String query = "INSERT INTO follows (uid,fid) VALUES (?,?)";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, uid);
			statement.setInt(2, fid);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Unfollow a user
	public void unfollowUser(Integer uid, Integer fid) {
		String query = "DELETE FROM follows WHERE uid = ? AND fid = ?";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, uid);
			statement.setInt(2, fid);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}    

    public void setFeaturedTweet(Integer userId, Integer tweetId) {
        String query = "UPDATE users SET featuredTweetId = ? WHERE id = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            if (tweetId != null) {
                statement.setInt(1, tweetId);
            } else {
                statement.setNull(1, java.sql.Types.INTEGER);
            }
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public Optional<List<User>> findNotFollowed(Integer id, Integer start, Integer end) {
		String query = "SELECT id,name,picture FROM users WHERE id NOT IN (SELECT id FROM users,follows WHERE id = fid AND uid = ?) AND id <> ? ORDER BY name LIMIT ?,?;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, id);
			statement.setInt(3, start);
			statement.setInt(4, end);
			try (ResultSet rs = statement.executeQuery()) {
				List<User> users = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPicture(rs.getString("picture"));
					users.add(user);
				}
				return Optional.of(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

    public boolean isFollowing(Integer uid, Integer fid) {
        String query = "SELECT COUNT(*) FROM follows WHERE uid = ? AND fid = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, uid);
            statement.setInt(2, fid);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	public Optional<List<User>> findFollowed(Integer id, Integer start, Integer end) {
		String query = "SELECT id,name,picture FROM users,follows WHERE id = fid AND uid = ? ORDER BY name LIMIT ?,?;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, start);
			statement.setInt(3, end);
			try (ResultSet rs = statement.executeQuery()) {
				List<User> users = new ArrayList<User>();
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setPicture(rs.getString("picture"));
					users.add(user);
				}
				return Optional.of(users);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
