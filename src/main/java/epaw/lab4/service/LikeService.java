package epaw.lab4.service;

import epaw.lab4.model.Like;
import epaw.lab4.repository.LikeRepository;
import java.sql.Timestamp;
import java.util.List;

public class LikeService {

	private static LikeService instance;
	private LikeRepository repository = LikeRepository.getInstance();

	private LikeService() {
	}

	public static synchronized LikeService getInstance() {
		if (instance == null) {
			instance = new LikeService();
		}
		return instance;
	}

	public boolean toggleLike(int tweetId, int userId) {
		if (repository.hasLiked(tweetId, userId)) {
			return repository.removeLike(tweetId, userId);
		} else {
			Like like = new Like(tweetId, userId);
			like.setTimestamp(new Timestamp(System.currentTimeMillis()));
			return repository.addLike(like);
		}
	}

	public boolean addLike(int tweetId, int userId) {
		if (!repository.hasLiked(tweetId, userId)) {
			Like like = new Like(tweetId, userId);
			like.setTimestamp(new Timestamp(System.currentTimeMillis()));
			return repository.addLike(like);
		}
		return false;
	}

	public boolean removeLike(int tweetId, int userId) {
		return repository.removeLike(tweetId, userId);
	}

	public boolean hasLiked(int tweetId, int userId) {
		return repository.hasLiked(tweetId, userId);
	}

	public int getLikeCount(int tweetId) {
		return repository.getLikeCount(tweetId);
	}

	public List<Like> getLikesForTweet(int tweetId) {
		return repository.getLikesForTweet(tweetId);
	}

	public List<Integer> getLikedTweetIdsByUser(int userId) {
		return repository.getLikedTweetIdsByUser(userId);
	}
}
