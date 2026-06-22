package epaw.lab4.controller;

import epaw.lab4.model.Comment;
import epaw.lab4.model.Tweet;
import epaw.lab4.model.User;
import epaw.lab4.service.TweetService;
import epaw.lab4.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/BuddyProfile")
public class BuddyProfile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing user id");
            return;
        }

        Integer buddyId;
        try {
            buddyId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user id");
            return;
        }

        UserService userService = UserService.getInstance();
        User buddy = userService.getUserById(buddyId);

        if (buddy == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        boolean canView = buddyId.equals(currentUser.getId()) || userService.isFollowing(currentUser.getId(), buddyId);
        if (!canView) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You can only view followed users");
            return;
        }

        List<Tweet> tweets = TweetService.getInstance().getTweetsByUser(buddyId, 0, 10);
        List<Comment> comments = TweetService.getInstance().getRepliesByUser(buddyId, 0, 20);

        request.setAttribute("user", currentUser);
        request.setAttribute("buddy", buddy);
        request.setAttribute("buddyTweets", tweets);
        request.setAttribute("buddyComments", comments);
        request.getRequestDispatcher("BuddyProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
