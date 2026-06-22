<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:if test="${not empty message}">
	<div class="w3-panel w3-pale-green w3-leftbar w3-border-green w3-padding">
		<p>${message}</p>
	</div>
</c:if>

<form id="editProfileForm" action="UpdateProfile" method="POST" enctype="multipart/form-data">

    <input type="hidden" name="id" value="${user.id}" />

    <div>
        <label for="name" class="w3-text-theme">Username</label>
        <input class="w3-input w3-border w3-light-grey" type="text" id="name" name="name" required minlength="5" maxlength="20"
            value="${user.name}" title="Username must be between 5 and 20 characters." />
    </div>

    <div>
        <label for="age" class="w3-text-theme">Age</label>
        <input class="w3-input w3-border w3-light-grey" type="number" id="age" name="age" required min="13" max="120"
            value="${user.age}" title="Age must be between 13 and 120." />
    </div>

    <div>
        <label for="email" class="w3-text-theme">Email</label>
        <input class="w3-input w3-border w3-light-grey" type="email" id="email" name="email" required
            value="${user.email}" title="Enter a valid email address." />
    </div>

    <div>
        <label for="role" class="w3-text-theme">Role</label>
        <c:set var="selectedRole" value="${empty user.role ? 'regular' : user.role}" />
        <select class="w3-select w3-border w3-light-grey" id="role" name="role" required>
            <option value="" disabled <c:if test="${empty user.role}">selected</c:if>>Choose a role</option>
            <option value="regular" <c:if test="${selectedRole == 'regular'}">selected</c:if>>Regular user</option>
            <option value="professional" <c:if test="${selectedRole == 'professional'}">selected</c:if>>Verified professional</option>
            <option value="admin" <c:if test="${selectedRole == 'admin'}">selected</c:if>>Admin</option>
        </select>
    </div>

    <div>
        <label for="biography" class="w3-text-theme">Biography</label>
        <textarea class="w3-input w3-border w3-light-grey" id="biography" name="biography" required minlength="10" maxlength="500"
            rows="4" title="Biography must be between 10 and 500 characters."><c:out value="${user.biography}" /></textarea>
    </div>

    <div>
        <label for="picture" class="w3-text-theme">Profile Picture</label>
        <input class="w3-input w3-border w3-light-grey" type="file" id="picture" name="picture" accept="image/*" />
    </div>

    <button type="submit" class="w3-button w3-theme w3-section">Save changes</button>
    <a class="w3-button w3-light-grey w3-section menu" href="Profile">Cancel</a>

</form>

<script>
    window.App = window.App || {};
    App.Errors = {};
    <c:forEach var="error" items="${errors}">
    App.Errors["${error.key}"] = "${error.value}";
    </c:forEach>

	App.initRegisterValidation(App.Errors, 'editProfileForm');
</script>
