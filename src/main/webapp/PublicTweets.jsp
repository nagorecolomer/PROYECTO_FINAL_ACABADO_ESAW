<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<script type="text/javascript">
$(document).off('click', '.guestToggleComments').on('click', '.guestToggleComments', function(event) {
  event.preventDefault();
  const tweetId = $(this).data('tweetId');
  const $section = $('.tweetCommentsSection[data-tweet-id="' + tweetId + '"]');
  $section.load('GetReplies?tweetId=' + tweetId);
});
</script>

<div class="guest-feed-hero w3-container w3-card w3-round w3-white w3-section w3-center">
  <h4><i class="fa fa-globe"></i> Guest timeline</h4>
  <p class="w3-opacity">Read-only mode · Explore latest community posts</p>
</div>

<c:choose>
  <c:when test="${empty tweets}">
    <div class="w3-panel w3-pale-blue w3-leftbar w3-border-blue w3-padding w3-center">
      <p>No tweets yet.</p>
    </div>
  </c:when>
  <c:otherwise>
    <c:forEach var="t" items="${tweets}">
      <div class="guest-tweet-card w3-container w3-card w3-section w3-white w3-round w3-animate-opacity ${t.featured ? 'w3-pale-yellow w3-border w3-border-amber' : ''}"><br>
        <img src="${t.picture}" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">
        <span class="w3-right w3-opacity"> ${t.postDateTime} </span>
        <c:if test="${t.featured}">
          <span class="w3-right w3-tag w3-amber w3-round w3-margin-right">Featured</span>
        </c:if>
        <h4> ${t.uname} </h4><br>
        <hr class="w3-clear">
        <p class="guest-tweet-content">${t.content}</p>
        <button type="button" class="guestToggleComments w3-button w3-small w3-theme-d1 w3-margin-bottom" data-tweet-id="${t.id}">
          <i class="fa fa-comment"></i> &nbsp;Read messages
        </button>
        <div class="tweetCommentsSection w3-margin-bottom" data-tweet-id="${t.id}"></div>
      </div>
    </c:forEach>
  </c:otherwise>
</c:choose>
