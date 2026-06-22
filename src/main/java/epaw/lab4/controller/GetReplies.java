package epaw.lab4.controller;

import epaw.lab4.service.TweetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({ "/GetReplies", "/GetComments" })
public class GetReplies extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tweetIdStr = request.getParameter("tweetId");
		if (tweetIdStr == null || tweetIdStr.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		try {
			int tweetId = Integer.parseInt(tweetIdStr);
			TweetService tweetService = TweetService.getInstance();
			HttpSession session = request.getSession(false);
			if (session != null && session.getAttribute("user") != null) {
				request.setAttribute("user", session.getAttribute("user"));
			}
			request.setAttribute("replies", tweetService.getRepliesForTweet(tweetId));
			request.setAttribute("comments", request.getAttribute("replies"));
			request.getRequestDispatcher("Comments.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
