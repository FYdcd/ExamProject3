<%--
  Created by IntelliJ IDEA.
  User: 241005
  Date: 2025/07/28
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page  contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ゲーム開始</title>
</head>
<body>
    <h1>新しいゲームを開始</h1>
    <form action="hello" method="post">
        <label for="partyName">パーティ名を入力してください:</label><br>
        <input type="text" id="partyName" name="partyName" value="わがパーティ"><br><br>
        <input type="submit" value="ゲーム開始">
    </form>
</body>
</html>
