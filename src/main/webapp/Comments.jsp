<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
  <c:when test="${empty comments}">
    <div class="w3-container w3-light-grey w3-padding-small">No comments yet.</div>
  </c:when>
  <c:otherwise>
    <div class="w3-container w3-small">
      <c:forEach var="cmt" items="${comments}">
        <div class="w3-row-padding w3-margin-bottom ${cmt.userRole eq 'professional' ? 'professional-comment-container' : ''}">
          <div class="w3-col s2">
            <img src="${cmt.userPicture}" alt="Avatar" class="w3-circle" style="width:36px">
          </div>
          <div class="w3-col s10">
            <strong>${cmt.userName}</strong>
            <c:if test="${cmt.userRole eq 'professional'}">
              <span class="professional-comment-badge"><i class="fa fa-certificate"></i> Professional</span>
            </c:if>
            <span class="w3-opacity w3-small">${cmt.timestamp}</span>
            <div>${cmt.content}</div>
            <c:if test="${user != null and (user.role eq 'admin' or user.id == cmt.userId)}">
              <button type="button" class="delComment w3-button w3-red w3-small w3-margin-top" data-comment-id="${cmt.id}" data-tweet-id="${cmt.tweetId}">
                <i class="fa fa-trash"></i> &nbsp;Delete comment
              </button>
            </c:if>
          </div>
        </div>
      </c:forEach>
    </div>
  </c:otherwise>
</c:choose>