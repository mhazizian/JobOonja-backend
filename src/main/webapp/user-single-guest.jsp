<%@page import="ir.aziz.karam.manager.UserManager"%>
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
                            ${skill.name}: ${skill.points}
                            <%
                                ir.aziz.karam.types.User current = (ir.aziz.karam.types.User) request.getAttribute("currenUser");
                                String currentId = current.getId();
                                String skillName = (java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${skill.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null);
                                String userId = ((ir.aziz.karam.types.User)request.getAttribute("user")).getId();
                            %>
                            <c:if test="<%=!UserManager.getInstance().userEndorseThisEndorse(current, userId, skillName)%>">
                                <form action="${pageContext.request.contextPath}/addEndorse" method="post">
                                    <input type="hidden" name="userId" value="<%=userId%>"/>
                                    <input type="hidden" name="currentUserId" value="<%=currentId%>"/>
                                    <input type="hidden" name="skillName" value="<%=skillName%>"/>
                                    <button>Endorse</button>
                                </form>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
    </body>
</html>