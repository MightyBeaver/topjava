<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3 align="center"><a href="index.html">Home</a></h3>
<h2 align="center">Приемы пищи</h2>
<table border="1" align="center">
    <tr>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach items="${mealsWithExceed}" var="meal">
        <c:if test="${meal.exceed == false}">
            <tr style="color: green">
        </c:if>
        <c:if test="${meal.exceed == true}">
            <tr style="color: red">
        </c:if>
            <c:set var="dateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}" />
            <td align="center">${dateTime}</td>
            <td align="center">${meal.description}</td>
            <td align="center">${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>