<%-- 
    Document   : projects
    Created on : Mar 3, 2019, 12:37:56 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <tbody>
                <c:forEach var="user" items="${requestScope.allUser}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
