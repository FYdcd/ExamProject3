<%--
  Created by IntelliJ IDEA.
  User: 241005
  Date: 2025/07/28
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ゲーム開始</title>
</head>
<body>
<h1>戦闘開始！</h1>
<div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 20px; background-color: #f9f9f9;">
    <c:forEach var="message" items="${messages}">
        <p>${message}</p>
    </c:forEach>
</div>
<form action="select" method="get">
    <input type="submit" value="戦闘開始">
</form>
</body>
</html>
