<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <title>All People</title>
</head>
<body>

<h2>All People</h2>
<br>

<c:choose>
    <c:when test="${empty allPeople}">
        No people yet ...
        <br>
    </c:when>
    <c:when test="${!empty allPeople}">
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Email</th>
                <th>TotalCost</th>
                <th>Operations</th>
            </tr>

            <jsp:useBean id="allPeople" scope="request" type="java.util.List"/>
            <c:forEach var="person" items="${allPeople}">

                <c:url var="skillsButton" value="/people/personSkills">
                    <c:param name="personId" value="${person.id}"/>
                </c:url>

                <c:url var="updateButton" value="/people/updatePerson">
                    <c:param name="personId" value="${person.id}"/>
                </c:url>

                <c:url var="deleteButton" value="/people/deletePerson">
                    <c:param name="personId" value="${person.id}"/>
                </c:url>


                <tr>
                    <td>${person.id}</td>
                    <td>${person.name}</td>
                    <td>${person.surname}</td>
                    <td>${person.email}</td>
                    <td>${person.totalCost}</td>
                    <td>
                        <input type="button" value="Skills" onclick="window.location.href = '${skillsButton}'"/>
                        <input type="button" value="Update" onclick="window.location.href = '${updateButton}'"/>
                        <input type="button" value="Delete" onclick="window.location.href = '${deleteButton}'"/>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </c:when>
</c:choose>

<br>

<input type="button" value="Add" onclick="window.location.href = 'people/addNewPerson'">
<input type="button" value="Back" onclick="history.back()">
<input type="button" value="All Skills" onclick="window.location.href = '/api/skills'">

</body>
</html>

