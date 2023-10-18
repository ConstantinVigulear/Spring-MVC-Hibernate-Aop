<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <title>All Skills</title>
</head>
<body>

<h2>All Skills</h2>
<br>

<c:choose>
    <c:when test="${empty skills}">
        No skills yet ...
        <br>
    </c:when>
    <c:when test="${!empty skills}">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Domain</th>
                <th>Level</th>
                <th>Operations</th>
            </tr>

            <jsp:useBean id="skills" scope="request" type="java.util.List"/>
            <c:forEach var="skill" items="${skills}">

                <c:url var="peopleButton" value="/people/peopleBySkill">
                    <c:param name="skillId" value="${skill.id}"/>
                </c:url>

                <c:url var="deleteButton" value="/skills/deleteSkill">
                    <c:param name="skillId" value="${skill.id}"/>
                </c:url>


                <tr>
                    <td>${skill.id}</td>
                    <td>${skill.name}</td>
                    <td>${skill.domain}</td>
                    <td>${skill.level}</td>
                    <td>
                        <input type="button" value="People" onclick="window.location.href = '${peopleButton}'"/>
                        <input type="button" value="Delete" onclick="window.location.href = '${deleteButton}'"/>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </c:when>
</c:choose>

<br>

<input type="button" value="Add" onclick="window.location.href = 'skills/createNewSkill'">
<input type="button" value="Back" onclick="history.back()">
<input type="button" value="All People" onclick="window.location.href = '/api/people'">

</body>
</html>

