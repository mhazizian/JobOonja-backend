<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>User</title>
    </head>
    <body>
        <ul>
            <li>id: ${requestScope.user.id}</li>
            <li>first name: ${requestScope.user.firstName}</li>
            <li>last name: ${requestScope.user.lastName}</li>
            <li>jobTitle: ${requestScope.user.jobTitle}</li>
            <li>bio: ${requestScope.user.bio}</li>
            <li>
                skills:
                <ul>
                    <c:forEach var="skill" items="${requestScope.user.skills}">
                        <li>
                            <c:out value="${skill.name}"/>:
                            <c:out value="${skill.points}"/>
                            <form action="${pageContext.request.contextPath}/deleteSkillUser" method="post">
                                <input type="hidden" name="user" value="${requestScope.user.id}" />
                                <input type="hidden" name="skill" value="${skill.name}" />
                                <button type="submit" name="${requestScope.user.id}/${skill.name}">Delete</button>
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
        Add Skill:
        <form action="${pageContext.request.contextPath}/addSkillUser" method="post">
            <select name="skill">
                <c:forEach var="skill" items="${requestScope.skills}">
                    <option><c:out value="${skill.name}"/></option>
                </c:forEach>
            </select>
            <input type="hidden" name="user" value="${requestScope.user.id}" />
            <button type="submit">Add</button>
        </form>
    </body>
</html>