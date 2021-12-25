<%@ page import="java.util.Map" %>
<%@ page import="com.company.model.Klass" %>
<%@ page import="com.company.Main" %><%--
  Created by IntelliJ IDEA.
  User: Колпаков Сергей
  Date: 25.12.2021
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    out.println("Choose the class:");

    for (Map.Entry<Integer, Klass> pair : Main.klassManager.klasses.entrySet()) {
    }
%>

</body>
</html>
