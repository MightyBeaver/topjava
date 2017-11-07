<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Новый прием пищи</title>
    <style type="text/css">
        input{
            width: 200px;
        }
    </style>
</head>
<body>
<h2 align="center">Новый прием пищи</h2>
<form action="meals" method="POST" style="text-align: center">
    <table align="center">
        <tr>
            <td><input type="hidden" readonly="readonly" name="id" value="${meal.id}"/></td>
        </tr>
        <tr>
            <td>Дата</td>
            <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></td>
        </tr>
        <tr>
            <td>Описание</td>
            <td><input type="text" name="description" value="${meal.description}"/></td>
        </tr>
        <tr>
            <td>Калории</td>
            <td><input type="text" name="calories" value="${meal.calories}"/></td>
        </tr>
    </table>
    <br>
    <input type="submit" value="Подтвердить">
</form>
</body>
</html>