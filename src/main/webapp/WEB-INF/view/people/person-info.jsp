<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Person Info</title>
</head>
<body>

<h2>Person Info</h2>
<br>

<form:form action="savePerson" modelAttribute="person">

    <form:hidden path="id"/>
    <label for="nameInput">Name:       </label>
    <form:input id="nameInput" path="name"/>
    <br><br>
    <label for="emailInput">Surname:   </label>
    <form:input id="surnameInput" path="surname"/>
    <br><br>
    <label for="emailInput">Email:       </label>
    <form:input id="emailInput" path="email"/>
    <br><br><br>

    <input type="submit" value="Submit">
</form:form>

<input type="button" value="Back" onclick="history.back()">
<input type="button" value="All People" onclick="window.location.href = '/api/people'">
<input type="button" value="All Skills" onclick="window.location.href = '/api/skills'">
</body>
</html>
