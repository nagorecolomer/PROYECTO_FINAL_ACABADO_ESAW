<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<div class="w3-container w3-card w3-round w3-white w3-section">
  <h4>Admin - Manage Users</h4>
  <table class="w3-table-all">
    <thead>
      <tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Age</th><th>Actions</th></tr>
    </thead>
    <tbody>
      <c:forEach var="u" items="${users}">
        <tr id="user-${u.id}">
          <td>${u.id}</td>
          <td>${u.name}</td>
          <td>${u.email}</td>
          <td>
            <select class="userRole" data-user-id="${u.id}">
              <option value="regular" <c:if test="${u.role == 'regular'}">selected</c:if>>Regular</option>
              <option value="professional" <c:if test="${u.role == 'professional'}">selected</c:if>>Professional</option>
              <option value="admin" <c:if test="${u.role == 'admin'}">selected</c:if>>Admin</option>
            </select>
          </td>
          <td>${u.age}</td>
          <td>
            <button class="deleteUser w3-button w3-red" data-user-id="${u.id}">Delete</button>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

<script>
$(document).on('change', '.userRole', function() {
  const userId = $(this).data('user-id');
  const role = $(this).val();
  $.post('UpdateUserRole', { id: userId, role: role }).done(function(){
    alert('Role updated');
  }).fail(function(){
    alert('Failed to update role');
  });
});

$(document).on('click', '.deleteUser', function(){
  if (!confirm('Delete this user?')) return;
  const userId = $(this).data('user-id');
  $.post('DeleteUser', { id: userId }).done(function(){
    $('#user-' + userId).remove();
  }).fail(function(){
    alert('Failed to delete user');
  });
});
</script>
