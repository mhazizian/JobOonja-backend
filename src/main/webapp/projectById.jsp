<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>JSTL!</title>
    </head>
    <body>
        <ul>
            <li>id:<c:out value="${requestScope.project.id}"/></li>
            <li>title: <c:out value="${requestScope.project.title}"/></li>
            <li>description: <c:out value="${requestScope.project.getDescrption()}"/></li>
            <li>imageUrl: <img src="<c:out value="${requestScope.project.getImageURL()}"/>" style="width: 50px; height: 50px;"></li>
            <li>budget: <c:out value="${requestScope.project.budget}"/></li>
        </ul>
        <!-- display form if user has not bidded before -->
        <c:if test="${!requestScope.hasBided}">
            <form action="${pageContext.request.contextPath}/bid" method="POST">
                <label for="bidAmount">Bid Amount:</label>
                <input type="number" name="bidAmount">
                <input type="hidden" name="projectId", value="${requestScope.project.id}">

                <button>Submit</button>
            </form>
        </c:if>
    </body>
</html>
