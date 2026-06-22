<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${not empty message}">
	<div class="w3-panel w3-pale-green w3-leftbar w3-border-green w3-padding">
		<p>${message}</p>
	</div>
</c:if>

<script type="text/javascript">
$(document).ready(function(){
	const $leftCol = $('#lcolumn').parent();
	const $rightCol = $('#rcolumn').parent();
	const $mainCol = $('#content').parent();
	<c:choose>
		<c:when test="${user != null}">
			$leftCol.removeClass('guest-side-hidden');
			$rightCol.removeClass('guest-side-hidden');
			$mainCol.removeClass('guest-content-full');
			$('#rcolumn').load('NotFollowed');
			$('#lcolumn').load('Profile');
			$('#iterator').load('Tweets');
		</c:when>
		<c:otherwise>
			$leftCol.addClass('guest-side-hidden');
			$rightCol.addClass('guest-side-hidden');
			$mainCol.addClass('guest-content-full');
			$('#rcolumn').html('');
			$('#lcolumn').html('');
			$('#iterator').load('PublicTweets');
		</c:otherwise>
	</c:choose>
});
</script>

<c:if test="${user != null}">
<form id="tweetForm" class="w3-container w3-card w3-round w3-white w3-section">
	<h6 class="w3-opacity"> ${user.name}, what are you thinking? </h6>
	<textarea id="tweetContent" name="content" class="w3-border w3-padding w3-input" rows="4" maxlength="100" required></textarea>
	<button id="addTweet" type="submit" class="w3-button w3-theme w3-section"><i class="fa fa-pencil"></i> &nbsp;Post</button>
</form>
</c:if>
 
<div id="iterator">
<!-- Tweets will be loaded here -->
</div>




