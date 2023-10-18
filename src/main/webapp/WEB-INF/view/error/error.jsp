<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <title>Error</title>
</head>
<body>

<h2>Error</h2>
<p>${exception}</p>

<input type="button" value="Back" onclick="history.back()">
<input type="button" value="All People" onclick="window.location.href = '/api/people'">
<input type="button" value="All Skills" onclick="window.location.href = '/api/skills'">

</body>
</html>

