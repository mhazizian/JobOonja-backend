<%--
  Created by IntelliJ IDEA.
  User: mohammadhosein
  Date: 2019-03-04
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gratz!!</title>
</head>
<body>
<c:catch var="exception">${requestScope.message}</c:catch>
<c:if test="${not empty exception}">Page not Found.</c:if>
</body>
</html>