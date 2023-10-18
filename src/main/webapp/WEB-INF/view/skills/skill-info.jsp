<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="for" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Skill Info</title>
</head>
<body>

<c:choose>
    <c:when test="${skill.id == null}">
        <h2>New Skill</h2>
    </c:when>
    <c:when test="${skill.id != null}">
        <h2> Update skill "${skill.name}"</h2>
    </c:when>
</c:choose>
<br>

<form:form id="saveSkillForm" action="saveNewSkill" modelAttribute="skill">

    <label id="nameInputLabel" for="nameInput">Name:    </label>
    <form:input id="nameInput" path="name"/>
    <br><br>

    <label id="domainLabel" for="domain">Domain: </label>
    <form:select id="domainSelectForm" path="domain" name="domains">
        <for:forEach var="domain" items="${domains}">
            <form:option value="${domain.name()}">${domain.name()}</form:option>
        </for:forEach>
    </form:select>
    <br><br>

    <label for="level">Level:     </label>
    <form:select path="level" name="levels" id="level">
        <c:forEach var="level" items="${levels}">
            <form:option value="${level.name()}">${level.name()}</form:option>
        </c:forEach>
    </form:select>
    <br><br><br>
    <input type="submit" value="Submit">
</form:form>

<input type="button" value="Back" onclick="history.back()">
<input type="button" value="All People" onclick="window.location.href = '/api/people'">

<!-- Script for disabling changing skill.name and skill.domain and permit only updating skill.level -->
<script>
    if ("${skill.id}".length !== 0)  {
        let idNewLabel = document.createElement('label');
        idNewLabel.textContent = 'Name:     ${skill.name}'

        let idOldLabel = document.getElementById('nameInputLabel');
        idOldLabel.replaceWith(idNewLabel);

        let domainNewLabel =document.createElement('label');
        domainNewLabel.textContent = 'Domain:  ${skill.domain}'

        let domainOldLabel = document.getElementById("domainLabel");
        domainOldLabel.replaceWith(domainNewLabel)

        document.getElementById("nameInput").hidden = true
        document.getElementById("domainSelectForm").hidden = true
    }
</script>

<!-- Script to detect previous page, this changes the sva method used -->
<script>
    let element = document.getElementById("saveSkillForm");

    if (document.referrer.includes("personId")) {
        element.action = "saveAddedSkill"
    }
</script>

</body>
</html>
