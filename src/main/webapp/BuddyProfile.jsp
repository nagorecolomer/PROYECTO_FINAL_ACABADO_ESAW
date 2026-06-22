<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
    <c:when test="${buddy != null}">
        <div class="w3-container w3-card w3-round w3-white w3-section">
            <h4><i class="fa fa-user-circle"></i> Profile</h4>
            <p><img src="${buddy.picture}" class="w3-circle" style="height:106px;width:106px" alt="Avatar"></p>
            <hr>
            <p><i class="fa fa-id-card fa-fw w3-margin-right"></i> ${buddy.name}</p>
            <p><i class="fa fa-envelope fa-fw w3-margin-right"></i> ${buddy.email}</p>
            <p><i class="fa fa-user fa-fw w3-margin-right"></i> ${buddy.role}</p>
            <p><i class="fa fa-calendar fa-fw w3-margin-right"></i> ${buddy.age}</p>
            <p><i class="fa fa-align-left fa-fw w3-margin-right"></i> ${buddy.biography}</p>
        </div>

        <div class="w3-container w3-card w3-round w3-white w3-section">
            <h5 class="w3-opacity"><i class="fa fa-comment"></i> Latest comments by ${buddy.name}</h5>
            <c:choose>
                <c:when test="${empty buddyComments}">
                    <div class="w3-panel w3-pale-blue w3-leftbar w3-border-blue w3-padding">No comments yet.</div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="cmt" items="${buddyComments}">
                        <div class="w3-padding-small w3-border-bottom ${cmt.userRole eq 'professional' ? 'professional-comment-container' : ''}">
                            <c:if test="${cmt.userRole eq 'professional'}">
                                <span class="professional-comment-badge"><i class="fa fa-certificate"></i> Professional</span>
                            </c:if>
                            <span class="w3-opacity w3-small">${cmt.timestamp}</span>
                            <p class="w3-margin-top">${cmt.content}</p>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="w3-container w3-card w3-round w3-white w3-section">
            <h5 class="w3-opacity"><i class="fa fa-file-text"></i> Latest tweets by ${buddy.name}</h5>
            <c:choose>
                <c:when test="${empty buddyTweets}">
                    <div class="w3-panel w3-pale-blue w3-leftbar w3-border-blue w3-padding">No tweets yet.</div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="t" items="${buddyTweets}">
                        <div class="w3-padding-small w3-border-bottom">
                            <span class="w3-opacity w3-small">${t.postDateTime}</span>
                            <p class="w3-margin-top">${t.content}</p>
                            <c:if test="${user != null and user.role eq 'admin'}">
                                <button type="button" class="delTweet w3-button w3-red w3-small w3-margin-bottom" data-tweet-id="${t.id}" data-refresh-url="BuddyProfile?id=${buddy.id}">
                                    <i class="fa fa-trash"></i> &nbsp;Delete
                                </button>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <div class="w3-panel w3-pale-red w3-leftbar w3-border-red w3-padding">Unable to load this profile.</div>
    </c:otherwise>
</c:choose>
