<%-- 
    Document   : projects
    Created on : Mar 3, 2019, 12:37:56 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Project</title>
    </head>
    <body>
        <table border="1">
            <tbody>
                <c:forEach var="project" items="${requestScope.allProject}">
                    <tr>
                        <td><c:out value="${project.id}"/></td>
                        <td><c:out value="${project.title}"/></td>
                        <td><c:out value="${project.getDescrption()}"/></td>
                        <td><img src="<c:out value="${project.getImageURL()}"/>" style="width: 50px; height: 50px;"></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
