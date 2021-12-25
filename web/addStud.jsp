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
    <title>Adding a student</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/add-student" method="post">
        <fieldset>
            <legend>Adding a new student:</legend>
            <label>Name<input name="name" type="text" /></label>
            <label>Surname<input name="surname" type="text" /></label>
            <label>Patronymic<input name="patronymic" type="text" /></label>
            <label>KlassID<input name="klassID" type="text" list="klasses"/>
                <datalist id="klasses">
                    <% for (Klass klass : Main.klassManager.klasses.values()) {
                        out.println("<option value = " + "\"" + klass.klassId + "\"" + "/>");
                    }%>
                </datalist>
            </label>
        </fieldset>
        <button type="submit">OK</button>
    </form>

</body>
</html>
