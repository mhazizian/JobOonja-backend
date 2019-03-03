<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>JSTL!</title>
    </head>
    <body>
        <ul>
            <li>id:<c:out value="${requestScope.user.id}"/></li>
            <li>title: <c:out value="${requestScope.user.title}"/></li>
            <li>budget: <c:out value="${requestScope.user.budget}"/></li>
        </ul>
        <!-- display form if user has not bidded before -->
        <form action="" method="">
            <label for="bidAmount">Bid Amount:</label>
            <input type="number" name="bidAmount">

            <button>Submit</button>
        </form>
    </body>
</html>
