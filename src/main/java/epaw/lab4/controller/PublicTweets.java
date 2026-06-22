package epaw.lab4.controller;

import epaw.lab4.model.Tweet;
import epaw.lab4.service.TweetService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/PublicTweets")
public class PublicTweets extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tweet> tweets = TweetService.getInstance().getRecentTweets(0, 20);
        request.setAttribute("tweets", tweets);
        request.getRequestDispatcher("PublicTweets.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
