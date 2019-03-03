<%-- 
    Document   : projects
    Created on : Mar 3, 2019, 12:37:56 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Users</title>
        <style>
            table {
                text-align: center;
                margin: 0 auto;
            }
            td {
                min-width: 300px;
                margin: 5px 5px 5px 5px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <table>
            <tbody>
                <tr>
                    <th>id</th>
                    <th>name</th>
                    <th>jobTitle</th>
                </tr>
                <c:forEach var="user" items="${requestScope.allUser}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.jobTitle}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
