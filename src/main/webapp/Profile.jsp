<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
<c:when test="${user != null}">
<div id="${user.id}" class="w3-container w3-card w3-round w3-white w3-section w3-center">
  <h4>My Profile</h4>
  <p><img src="${user.picture}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
  <hr>
  <p class="w3-left-align"> <i class="fa fa-id-card fa-fw w3-margin-right"></i> ${user.name} </p>
  <p class="w3-left-align"> <i class="fa fa-envelope fa-fw w3-margin-right"></i> ${user.email} </p>
  <p class="w3-left-align"> <i class="fa fa-user fa-fw w3-margin-right"></i> ${user.role} </p>
  <p class="w3-left-align"> <i class="fa fa-calendar fa-fw w3-margin-right"></i> ${user.age} </p>
  <p class="w3-left-align"> <i class="fa fa-align-left fa-fw w3-margin-right"></i> ${user.biography} </p>
  <a href="EditProfile" class="menu editUser w3-row w3-button w3-green w3-section"><i class="fa fa-user-plus"></i> &nbsp;Edit</a>
 </div>
<br>

<c:if test="${user.role eq 'professional' and featuredTweet != null}">
<div class="w3-container w3-card w3-round w3-white w3-section">
  <h4><i class="fa fa-star w3-text-amber"></i> Featured post</h4>
  <div class="w3-pale-yellow w3-border w3-border-amber w3-round-large w3-padding">
    <div class="w3-opacity w3-right">${featuredTweet.postDateTime}</div>
    <strong>${featuredTweet.uname}</strong>
    <p>${featuredTweet.content}</p>
  </div>
</div>
</c:if>
</c:when>
<c:otherwise>
<p/>
</c:otherwise>
</c:choose>