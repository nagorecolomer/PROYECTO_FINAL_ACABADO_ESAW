package epaw.lab4.repository;

import epaw.lab4.model.Like;
import epaw.lab4.util.DBManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LikeRepository extends BaseRepository {

	private static LikeRepository instance;

	private LikeRepository() {
	}

	public static synchronized LikeRepository getInstance() {
		if (instance == null) {
			instance = new LikeRepository();
		}
		return instance;
	}

	public boolean addLike(Like like) {
		String sql = "INSERT INTO likes (tweetId, userId, timestamp) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = DBManager.getInstance().prepareStatement(sql)) {
			stmt.setInt(1, like.getTweetId());
			stmt.setInt(2, like.getUserId());
			stmt.setTimestamp(3, like.getTimestamp());
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeLike(int tweetId, int userId) {
		String sql = "DELETE FROM likes WHERE tweetId = ? AND userId = ?";
		try (PreparedStatement stmt = DBManager.getInstance().prepareStatement(sql)) {
			stmt.setInt(1, tweetId);
			stmt.setInt(2, userId);
			stmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean hasLiked(int tweetId, int userId) {
		String sql = "SELECT COUNT(*) FROM likes WHERE tweetId = ? AND userId = ?";
		try (PreparedStatement stmt = DBManager.getInstance().prepareStatement(sql)) {
			stmt.setInt(1, tweetId);
			stmt.setInt(2, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getLikeCount(int tweetId) {
		String sql = "SELECT COUNT(*) FROM likes WHERE tweetId = ?";
		try (PreparedStatement stmt = DBManager.getInstance().prepareStatement(sql)) {
			stmt.setInt(1, tweetId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Like> getLikesForTweet(int tweetId) {
		List<Like> likes = new ArrayList<>();
		String sql = "SELECT * FROM likes WHERE tweetId = ? ORDER BY timestamp DESC";
		try (PreparedStatement stmt = DBManager.getInstance().prepareStatement(sql)) {
			stmt.setInt(1, tweetId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Like like = new Like();
					like.setId(rs.getInt("id"));
					like.setTweetId(rs.getInt("tweetId"));
					like.setUserId(rs.getInt("userId"));
					like.setTimestamp(rs.getTimestamp("timestamp"));
					likes.add(like);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return likes;
	}

	public List<Integer> getLikedTweetIdsByUser(int userId) {
		List<Integer> likedTweetIds = new ArrayList<>();
		String sql = "SELECT tweetId FROM likes WHERE userId = ? ORDER BY timestamp DESC";
		try (PreparedStatement stmt = DBManager.getInstance().prepareStatement(sql)) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					likedTweetIds.add(rs.getInt("tweetId"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return likedTweetIds;
	}
}
