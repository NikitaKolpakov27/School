<%--
  Created by IntelliJ IDEA.
  User: Колпаков Сергей
  Date: 24.12.2021
  Time: 22:12
  To change this template use File | Settings | File Templates.

  Перед добавлением request.getParam
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%--    <meta charset="UTF-8">--%>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>School</title>
</head>
<body>

    <label>Welcome, <%= request.getParameter("login") %> Please, choose what you want to do:</label>

    <br>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Display students">
    </form>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Display class schedule">
    </form>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Display students grades">
    </form>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Take a grade to the student">
    </form>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Add student">
    </form>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Remove student">
    </form>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="Display subjects">
    </form>

    <br>

    <form action="${pageContext.request.contextPath}/choice" method="post">
        <input type="submit" name="button" value="exit">
    </form>


</body>
</html>
