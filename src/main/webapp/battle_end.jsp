<%--
  Created by IntelliJ IDEA.
  User: 241005
  Date: 2025/07/28
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>戦闘終了</title>
</head>
<body>
<h1>戦闘終了</h1>
<c:forEach var="message" items="${messages}">
    <p>${message}</p>
</c:forEach>
<br>
<form action="index.jsp" method="get">
    <input type="submit" value="タイトルへ戻る">
</form>
</body>
</html>
