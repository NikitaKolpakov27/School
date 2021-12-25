<%--
  Created by IntelliJ IDEA.
  User: Колпаков Сергей
  Date: 24.12.2021
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Главная страница</title>
  </head>
  <body style="font-size:17pt">
    <label style="font-size:17pt">Войдите или зарегистрируйтесь</label>
    <form style="font-size:17pt" action="${pageContext.request.contextPath}/login">
        <button type="submit">Войти</button>
    </form>

    <form style="font-size:17pt" action="${pageContext.request.contextPath}/registration">
      <button type="submit">Зарегистрироваться</button>
    </form>

  </body>
</html>
