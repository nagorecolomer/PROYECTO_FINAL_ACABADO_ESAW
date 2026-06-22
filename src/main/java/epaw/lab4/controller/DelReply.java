package epaw.lab4.controller;

import epaw.lab4.model.Comment;
import epaw.lab4.model.User;
import epaw.lab4.service.TweetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet({ "/DelReply", "/DelComment" })
public class DelReply extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		User user = (User) session.getAttribute("user");
		String replyIdStr = request.getParameter("replyId");
		if (replyIdStr == null || replyIdStr.trim().isEmpty()) {
			replyIdStr = request.getParameter("commentId");
		}
		if (replyIdStr == null || replyIdStr.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			int replyId = Integer.parseInt(replyIdStr);
			TweetService tweetService = TweetService.getInstance();
			Comment reply = tweetService.getReplyById(replyId);
			if (reply == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			boolean isAdmin = "admin".equals(user.getRole());
			boolean isOwner = reply.getUserId() == user.getId();
			if (!isAdmin && !isOwner) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			boolean removed = tweetService.deleteReply(replyId);
			if (!removed) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}

			String replyCount = String.valueOf(tweetService.getReplyCount(reply.getTweetId()));
			response.setHeader("X-Reply-Count", replyCount);
			response.setHeader("X-Comment-Count", replyCount);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
