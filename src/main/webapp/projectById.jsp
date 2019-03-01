<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>
    <ul>
        <li>id:<c:out value="${requestScope.project.id}"/></li>
        <li>title: <c:out value="${requestScope.project.title}"/></li>
        <li>description: <c:out value="${requestScope.project.description}"/></li>
        <li>imageUrl: <img src="<c:out value="${requestScope.project.imageUrl}"/>" style="width: 50px; height: 50px;"></li>
        <li>budget: <c:out value="${requestScope.project.budget}"/></li>
    </ul>
    <!-- display form if user has not bidded before -->
    <form action="" method="">
    	<label for="bidAmount">Bid Amount:</label>
    	<input type="number" name="bidAmount">

    	<button>Submit</button>
    </form>
</body>
</html>