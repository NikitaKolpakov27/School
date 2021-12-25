<%@ page import="com.company.model.Student" %>
<%@ page import="com.company.Main" %>
<%@ page import="com.company.model.Subject" %>
<%--
  Created by IntelliJ IDEA.
  User: Колпаков Сергей
  Date: 25.12.2021
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Giving a grade</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/giving-grade" method="post">
        <fieldset>
            <legend>Give a grade to a student:</legend>
            <label>Student<input name="stud" type="text" list="studs" /></label>
            <datalist id="studs">
                <% for (Student stud : Main.studentManager.students.values()) {
                    out.println("<option value = " + "\"" + stud.toString() + "; Class = " +
                            Main.klassManager.klasses.get(stud.klassId).name + " (" + stud.klassId + ", "
                            + stud.studID + ")"
                            + "\"" + "/>");
                }%>
            </datalist>
            <label>Subject<input name="subj" type="text" list="subjs" /></label>
            <datalist id="subjs">
                <% for (Subject subj : Main.subjectManager.subjects.values()) {
                    out.println("<option value = " + "\"" + subj.name + "; subjID = " + "(" + subj.subjectId + ")"
                            + "\"" + "/>");
                }%>
            </datalist>

            <label>Grade<input name="grade" type="text" list="grades" /></label>
            <datalist id="grades">
                <% for (int i = 2; i < 6; i++){
                    out.println("<option value = " + "\"" + i + "\"" + "/>");
                }%>
            </datalist>
        </fieldset>
        <button type="submit">OK</button>
    </form>


</body>
</html>
