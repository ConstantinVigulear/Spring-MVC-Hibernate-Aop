<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <title>Skills of ${person.name} ${person.surname}</title>
</head>
<body>

<h2>Skills of ${person.name} ${person.surname}</h2>
<br>

<c:choose>
    <c:when test="${empty skills}">
        No skills yet ...
        <br>
    </c:when>
    <c:when test="${skills != null}">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Domain</th>
                <th>Level</th>
                <th>Operations</th>
            </tr>

            <c:forEach var="skill" items="${skills}">

                <c:url var="updateButton" value="/skills/updateSkill">
                    <c:param name="skillId" value="${skill.id}"/>
                    <c:param name="personId" value="${person.id}"/>
                    <c:param name="personName" value="${person.name}"/>
                    <c:param name="personSurname" value="${person.surname}"/>
                </c:url>

                <c:url var="deleteButton" value="/people/deleteSkillFromPerson">
                    <c:param name="skillId" value="${skill.id}"/>
                    <c:param name="skillCost" value="${skill.skillCost}"/>
                    <c:param name="personId" value="${person.id}"/>
                </c:url>

                <tr>
                    <td>${skill.id}</td>
                    <td>${skill.name}</td>
                    <td>${skill.domain}</td>
                    <td>${skill.level}</td>
                    <td>
                        <input type="button" value="Update" onclick="window.location.href = '${updateButton}'"/>
                        <input type="button" value="Delete" onclick="window.location.href = '${deleteButton}'"/>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </c:when>
</c:choose>


<br>

<c:url var="addNewSkillButton" value="/skills/addNewSkill">
    <c:param name="personId" value="${person.id}"/>
</c:url>

<input type="button" value="Add" onclick="window.location.href = '${addNewSkillButton}'">
<br><br>
<input type="button" value="Back" onclick="window.location.href = '/api/people'">
<input type="button" value="All People" onclick="window.location.href = '/api/people'">
<input type="button" value="All Skills" onclick="window.location.href = '/api/skills'">

</body>
</html>

