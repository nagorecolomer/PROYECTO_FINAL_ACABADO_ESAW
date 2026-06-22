<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<script type="text/javascript">
$(document).ready(function(){
	$('#rcolumn').load('NotFollowed');
	$('#lcolumn').load('Profile');
});
</script>

<div class="w3-container w3-card w3-round w3-white w3-section">
	<h5 class="w3-opacity"> Posts from people you follow </h5>
	<p class="w3-small w3-text-grey">Like and comment on the latest activity from your buddies.</p>
</div>

<div id="iterator">
	<c:choose>
		<c:when test="${empty tweets}">
			<div class="w3-panel w3-pale-blue w3-leftbar w3-border-blue w3-padding">
				<p>No posts from followed users yet.</p>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="t" items="${tweets}">
				<div id="${t.id}" class="w3-container w3-card w3-section w3-white w3-round w3-animate-opacity ${t.featured ? 'w3-pale-yellow w3-border w3-border-amber' : ''}"><br>
					<img src="${empty t.picture ? user.picture : t.picture}" alt="Avatar" class="w3-left w3-circle w3-margin-right" style="width:60px">
					<span class="w3-right w3-opacity"> ${t.postDateTime} </span>
					<c:if test="${t.featured}">
						<span class="w3-right w3-tag w3-amber w3-round w3-margin-right">Featured</span>
					</c:if>
					<h4> ${t.uname} </h4><br>
					<hr class="w3-clear">
					<p> ${t.content} </p>
					<button type="button" class="likeTweet w3-button ${likedTweetIds.contains(t.id) ? 'w3-green' : 'w3-theme'} w3-margin-bottom"><i class="fa fa-thumbs-up"></i> &nbsp;Like <span class="likeCount">(${likeCounts[t.id]})</span></button>
					<button type="button" class="commentTweet w3-button w3-blue w3-margin-bottom"><i class="fa fa-comment"></i> &nbsp;Comment <span class="commentCount">(${commentCounts[t.id]})</span></button>
					<c:if test="${user != null and user.role eq 'admin'}">
						<button type="button" class="delTweet w3-button w3-red w3-margin-bottom" data-tweet-id="${t.id}" data-refresh="LikeComment"><i class="fa fa-trash"></i> &nbsp;Delete</button>
					</c:if>
					<div class="w3-margin-bottom">
						<textarea class="w3-input w3-border commentInput" rows="2" maxlength="500" placeholder="Write a comment..."></textarea>
						<button type="button" class="submitComment w3-button w3-small w3-margin-top w3-theme-d1">Send</button>
					</div>
					<div class="tweetCommentsSection w3-margin-bottom" data-tweet-id="${t.id}"></div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</div>
