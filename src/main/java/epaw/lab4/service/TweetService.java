package epaw.lab4.service;

import epaw.lab4.repository.TweetRepository;
import java.util.List;
import java.util.Optional;
import epaw.lab4.model.Comment;
import epaw.lab4.model.Tweet;

public class TweetService {
	
	private static TweetService instance;
	private TweetRepository tweetRepository;
	
	private TweetService() {
        this.tweetRepository = TweetRepository.getInstance();
    }
	
	public static synchronized TweetService getInstance() {
		if (instance == null) {
			instance = new TweetService();
		}
		return instance;
	}
	
	public void add(Tweet tweet) {
		tweetRepository.save(tweet);	
	}
	
	public void delete(Integer id, Integer uid) {
		tweetRepository.delete(id, uid);
	}

	public void deleteById(Integer id) {
		tweetRepository.deleteById(id);
	}

	public Tweet getTweetById(Integer id) {
		Optional<Tweet> tweet = tweetRepository.findById(id);
		return tweet.orElse(null);
	}

	public List<Tweet> getTweetsByUser(Integer uid, Integer start, Integer end) {
		Optional<List<Tweet>> tweets = tweetRepository.findByUser(uid,start,end);
    	if (tweets.isPresent())
    	    return tweets.get();
        return null;
	}

public List<Tweet> getTweetsFromFollowedUsers(Integer uid, Integer start, Integer end) {
Optional<List<Tweet>> tweets = tweetRepository.findByFollowedUsers(uid, start, end);
if (tweets.isPresent())
return tweets.get();
return null;
}

public List<Tweet> getRecentTweets(Integer start, Integer end) {
	Optional<List<Tweet>> tweets = tweetRepository.findAllRecent(start, end);
	if (tweets.isPresent())
		return tweets.get();
	return null;
}

	public boolean addReply(Integer tweetId, Integer userId, String content) {
		if (content == null) {
			return false;
		}
		String trimmedContent = content.trim();
		if (trimmedContent.isEmpty() || trimmedContent.length() > 500) {
			return false;
		}
		return tweetRepository.addReply(tweetId, userId, trimmedContent);
	}

	public boolean deleteReply(Integer replyId) {
		return tweetRepository.deleteReply(replyId);
	}

	public List<Comment> getRepliesForTweet(Integer tweetId) {
		return tweetRepository.findRepliesByTweet(tweetId);
	}

	public Comment getReplyById(Integer replyId) {
		return tweetRepository.findReplyById(replyId);
	}

	public int getReplyCount(Integer tweetId) {
		return tweetRepository.countReplies(tweetId);
	}

	public List<Comment> getRepliesByUser(Integer userId, Integer start, Integer limit) {
		return tweetRepository.findRepliesByUser(userId, start, limit);
	}
}
