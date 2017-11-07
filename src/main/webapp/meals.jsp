<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Users</title>
    <style type="text/css">
    td{
        text-align: center;
    }
</style>
</head>
<body>
<h3 align="center"><a href="index.html">Домашнаяя страница</a></h3>
<h2 align="center">Приемы пищи</h2>
<table border="1" align="center">
    <tr>
        <th>ID</th>
        <th>Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2>Действия</th>
    </tr>
    <c:forEach items="${mealsWithExceed}" var="meal">
        <tr style="color:${meal.exceed ? "red" : "green"}">
            <c:set var="dateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}" />
            <td>${dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">Изменить</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
<br>
<div style="text-align:center">
    <a href="meals?action=insert">Новый прием пищи</a>
</div>
</body>
</html>