<%@ page import="com.company.model.Student" %>
<%@ page import="com.company.Main" %><%--
  Created by IntelliJ IDEA.
  User: Колпаков Сергей
  Date: 25.12.2021
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Removing a student</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/remove-student" method="post">
        <fieldset>
            <legend>Removing a student:</legend>
            <label>Student<input name="stud" type="text" list="studs" /></label>
<%--            <label>Surname<input name="surname" type="text" /></label>--%>
<%--            <label>Patronymic<input name="patronymic" type="text" /></label>--%>
<%--            <label>KlassID<input name="klassID" type="text" list="klasses"/>--%>
                <datalist id="studs">
                    <% for (Student stud : Main.studentManager.students.values()) {
                        out.println("<option value = " + "\"" + stud.toString() + "; Class = " +
                                Main.klassManager.klasses.get(stud.klassId).name + " (" + stud.klassId + ", "
                                + stud.studID + ")"
                                + "\"" + "/>");
                    }%>
                </datalist>
<%--            </label>--%>
        </fieldset>
        <button type="submit">OK</button>
    </form>

</body>
</html>
