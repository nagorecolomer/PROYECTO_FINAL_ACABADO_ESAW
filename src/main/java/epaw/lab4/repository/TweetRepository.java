package epaw.lab4.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import epaw.lab4.model.Comment;
import epaw.lab4.model.Tweet;

public class TweetRepository extends BaseRepository {

    private static TweetRepository instance;

    private TweetRepository() {
        super();
    }

    public static synchronized TweetRepository getInstance() {
        if (instance == null) {
            instance = new TweetRepository();
        }
        return instance;
    }
	
	public void save(Tweet tweet) {
		String query = "INSERT INTO tweets (uid,postdatetime,content) VALUES (?,?,?)";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, tweet.getUid());
			statement.setTimestamp(2, tweet.getPostDateTime());
			statement.setString(3, tweet.getContent());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Delete existing tweet */
	public void delete(Integer id, Integer uid) {
		String query = "DELETE FROM tweets WHERE id = ? AND uid=?";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.setInt(2, uid);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Admin: delete any tweet by id */
	public void deleteById(Integer id) {
		String query = "DELETE FROM tweets WHERE id = ?";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Optional<Tweet> findById(Integer id) {
		String query = "SELECT tweets.id,tweets.uid,tweets.postdatetime,tweets.content,users.name,users.picture,users.featuredTweetId FROM tweets INNER JOIN users ON tweets.uid = users.id WHERE tweets.id = ?";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					Tweet tweet = new Tweet();
					tweet.setId(rs.getInt("id"));
					tweet.setUid(rs.getInt("uid"));
					tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
					tweet.setContent(rs.getString("content"));
					tweet.setUname(rs.getString("name"));
					tweet.setPicture(rs.getString("picture"));
					int featuredTweetId = rs.getInt("featuredTweetId");
					tweet.setFeatured(!rs.wasNull() && featuredTweetId == tweet.getId());
					return Optional.of(tweet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/* Get tweets from a user given start and end */
	public Optional<List<Tweet>> findByUser(Integer uid, Integer start, Integer end) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		String query = "SELECT tweets.id,tweets.uid,tweets.postdatetime,tweets.content,users.name,users.picture,users.featuredTweetId FROM tweets INNER JOIN users ON tweets.uid = users.id where tweets.uid = ? AND tweets.pid IS NULL ORDER BY tweets.postdatetime DESC LIMIT ?,? ;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, uid);
			statement.setInt(2, start);
			statement.setInt(3, end);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Tweet tweet = new Tweet();
					tweet.setId(rs.getInt("id"));
					tweet.setUid(rs.getInt("uid"));
					tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
					tweet.setContent(rs.getString("content"));
					tweet.setUname(rs.getString("name"));
					tweet.setPicture(rs.getString("picture"));
					int featuredTweetId = rs.getInt("featuredTweetId");
					tweet.setFeatured(!rs.wasNull() && featuredTweetId == tweet.getId());
					tweets.add(tweet);
				}
				return Optional.of(tweets);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<List<Tweet>> findByFollowedUsers(Integer uid, Integer start, Integer end) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		String query = "SELECT tweets.id,tweets.uid,tweets.postdatetime,tweets.content,users.name,users.picture "
				+ ",users.featuredTweetId "
				+ "FROM tweets INNER JOIN users ON tweets.uid = users.id "
				+ "WHERE tweets.uid IN (SELECT fid FROM follows WHERE uid = ?) "
				+ "AND tweets.pid IS NULL "
				+ "ORDER BY tweets.postdatetime DESC LIMIT ?,? ;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, uid);
			statement.setInt(2, start);
			statement.setInt(3, end);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Tweet tweet = new Tweet();
					tweet.setId(rs.getInt("id"));
					tweet.setUid(rs.getInt("uid"));
					tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
					tweet.setContent(rs.getString("content"));
					tweet.setUname(rs.getString("name"));
					tweet.setPicture(rs.getString("picture"));
					int featuredTweetId = rs.getInt("featuredTweetId");
					tweet.setFeatured(!rs.wasNull() && featuredTweetId == tweet.getId());
					tweets.add(tweet);
				}
				return Optional.of(tweets);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public Optional<List<Tweet>> findAllRecent(Integer start, Integer end) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		String query = "SELECT tweets.id,tweets.uid,tweets.postdatetime,tweets.content,users.name,users.picture "
				+ ",users.featuredTweetId "
				+ "FROM tweets INNER JOIN users ON tweets.uid = users.id "
				+ "WHERE tweets.pid IS NULL "
				+ "ORDER BY tweets.postdatetime DESC LIMIT ?,? ;";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, start);
			statement.setInt(2, end);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Tweet tweet = new Tweet();
					tweet.setId(rs.getInt("id"));
					tweet.setUid(rs.getInt("uid"));
					tweet.setPostDateTime(rs.getTimestamp("postdatetime"));
					tweet.setContent(rs.getString("content"));
					tweet.setUname(rs.getString("name"));
					tweet.setPicture(rs.getString("picture"));
					int featuredTweetId = rs.getInt("featuredTweetId");
					tweet.setFeatured(!rs.wasNull() && featuredTweetId == tweet.getId());
					tweets.add(tweet);
				}
				return Optional.of(tweets);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public boolean addReply(Integer tweetId, Integer userId, String content) {
		String query = "INSERT INTO tweets (uid,postdatetime,content,pid) VALUES (?,CURRENT_TIMESTAMP,?,?)";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setString(2, content);
			statement.setInt(3, tweetId);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteReply(Integer replyId) {
		String query = "DELETE FROM tweets WHERE id = ? AND pid IS NOT NULL";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, replyId);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Comment> findRepliesByTweet(Integer tweetId) {
		List<Comment> comments = new ArrayList<>();
		String query = "SELECT t.id, t.pid AS tweetId, t.uid AS userId, t.content, t.postdatetime AS timestamp, "
				+ "u.name, u.picture, u.role "
				+ "FROM tweets t "
				+ "JOIN users u ON t.uid = u.id "
				+ "WHERE t.pid = ? "
				+ "ORDER BY t.postdatetime ASC";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, tweetId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Comment comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setTweetId(rs.getInt("tweetId"));
					comment.setUserId(rs.getInt("userId"));
					comment.setUserName(rs.getString("name"));
					comment.setUserPicture(rs.getString("picture"));
					comment.setUserRole(rs.getString("role"));
					comment.setContent(rs.getString("content"));
					comment.setTimestamp(rs.getTimestamp("timestamp"));
					comments.add(comment);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}

	public Comment findReplyById(Integer replyId) {
		String query = "SELECT t.id, t.pid AS tweetId, t.uid AS userId, t.content, t.postdatetime AS timestamp, "
				+ "u.name, u.picture, u.role "
				+ "FROM tweets t "
				+ "JOIN users u ON t.uid = u.id "
				+ "WHERE t.id = ? AND t.pid IS NOT NULL";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, replyId);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					Comment comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setTweetId(rs.getInt("tweetId"));
					comment.setUserId(rs.getInt("userId"));
					comment.setUserName(rs.getString("name"));
					comment.setUserPicture(rs.getString("picture"));
					comment.setUserRole(rs.getString("role"));
					comment.setContent(rs.getString("content"));
					comment.setTimestamp(rs.getTimestamp("timestamp"));
					return comment;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int countReplies(Integer tweetId) {
		String query = "SELECT COUNT(*) FROM tweets WHERE pid = ?";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, tweetId);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Comment> findRepliesByUser(Integer userId, Integer start, Integer limit) {
		List<Comment> comments = new ArrayList<>();
		String query = "SELECT t.id, t.pid AS tweetId, t.uid AS userId, t.content, t.postdatetime AS timestamp, "
				+ "u.name, u.picture, u.role "
				+ "FROM tweets t "
				+ "JOIN users u ON t.uid = u.id "
				+ "WHERE t.uid = ? AND t.pid IS NOT NULL "
				+ "ORDER BY t.postdatetime DESC LIMIT ?, ?";
		try (PreparedStatement statement = db.prepareStatement(query)) {
			statement.setInt(1, userId);
			statement.setInt(2, start);
			statement.setInt(3, limit);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Comment comment = new Comment();
					comment.setId(rs.getInt("id"));
					comment.setTweetId(rs.getInt("tweetId"));
					comment.setUserId(rs.getInt("userId"));
					comment.setUserName(rs.getString("name"));
					comment.setUserPicture(rs.getString("picture"));
					comment.setUserRole(rs.getString("role"));
					comment.setContent(rs.getString("content"));
					comment.setTimestamp(rs.getTimestamp("timestamp"));
					comments.add(comment);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}

}

