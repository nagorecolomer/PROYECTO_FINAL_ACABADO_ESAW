<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="w3-bar">
	<a class="w3-bar-item w3-button" href="index.html"> <i class="fa fa-home" aria-hidden="true"></i> </a>
	<a class="menu w3-bar-item w3-button w3-hide-small" href="Timeline"> MyPosts </a>
	<a class="menu w3-bar-item w3-button w3-hide-small" href="Followed"> Buddies </a>
	<a class="menu w3-bar-item w3-button w3-hide-small" href="LikeComment"> Like / Comment </a>
	<c:if test="${user != null && user.role eq 'admin'}">
		<a class="menu w3-bar-item w3-button w3-hide-small" href="AdminUsers"> Admin </a>
	</c:if>
	<a class="menu w3-bar-item w3-button w3-hide-small w3-right" href="Logout"> <i class="fa fa-sign-out"></i> </a>
	<a href="javascript:void(0)" class="w3-bar-item w3-button w3-right w3-hide-large w3-hide-medium" onclick="App.stack()">&#9776;</a>
</div>

<div id="stack" class="w3-bar-block w3-hide w3-hide-large w3-hide-medium">
	<a class="menu w3-bar-item w3-button" href="Timeline"> MyPosts </a>
	<a class="menu w3-bar-item w3-button" href="Followed"> Buddies </a>
	<a class="menu w3-bar-item w3-button" href="LikeComment"> Like / Comment </a>
	<a class="menu w3-bar-item w3-button" href="Profile"> Profile </a>
	<a class="menu w3-bar-item w3-button" href="NotFollowed"> Discover </a>
	<c:if test="${user != null && user.role eq 'admin'}">
		<a class="menu w3-bar-item w3-button" href="AdminUsers"> Admin </a>
	</c:if>
	<a class="menu w3-bar-item w3-button" href="Logout"> Logout </a>
</div>
