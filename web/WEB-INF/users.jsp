<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <c:if test="${not empty users}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.role.roleName}</td>
                            <td class="text-center">
                                <a href="<c:url value='/users?action=edit'>
                                       <c:param name="email" value="${user.email}"></c:param>
                                   </c:url>">Edit</a>
                            </td>
                            <td>
                                <a href="<c:url value='/users?action=delete'>
                                       <c:param name="email" value="${user.email}"></c:param>
                                   </c:url>">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty users}">
            <h4>No users found. Please add a user.</h4>
        </c:if>

        <c:if test="${isEdit eq null}">
            <h2>Add User</h2>
            <form action="users" method="post">
                Email: <input type="email" name="email"> <br>
                First name: <input type="text" name="firstname"/> <br>
                Last name: <input type="text" name="lastname"> <br>
                Password: <input type="password" name="password">  <br>
                Role: <select name="roleId">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select> <br>
                <input type="hidden" name="action" value="add" />
                <input class="input-primary" type="submit" value="Add user"/>
            </form>

        </c:if>

        <c:if test="${isEdit eq true}">
            <h2>Edit User</h2>
            <form action="users" method="post">
                Email ${user.email} <br>
                First name: <input type="text" name="firstname" value="${user.firstName}"> <br>
                Last name: <input type="text" name="lastname" value="${user.lastName}"><br>
                Password: <input type="password" name="password"><br>
                Role: <select name="roleId">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role.roleId}" ${role.roleId == user.role.roleId ? "selected" : ""}>${role.roleName}</option>
                    </c:forEach>
                </select><br>
                <input type="hidden" name="email" value="${user.email}">
                <input type="hidden" name="action" value="edit">
                <input type="submit" value="Update">
                <a href="/">
                    <input type="button" value="Cancel" >
                </a>
            </form>
        </c:if>


        <c:if test="${error ne null}">
            <div>
                <span>${error}</span>
            </div>
        </c:if>
    </body>
</html>
