<%--
  Created by IntelliJ IDEA.
  User: 241005
  Date: 2025/07/28
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="com.example.examproject3.creature.Character" %>
<%@ page import="com.example.examproject3.creature.character.Hero" %>
<%@ page import="com.example.examproject3.creature.character.SuperHero" %>
<%@ page import="com.example.examproject3.creature.character.Wizard" %>
<%@ page import="com.example.examproject3.creature.character.Thief" %>
<%@ page import="com.example.examproject3.creature.Monster" %>
<%@ page import="com.example.examproject3.weapon.Weapon" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>行動選択</title>
    <style>
        body { font-family: sans-serif; }
        .messages { border: 1px solid #ccc; padding: 10px; margin-bottom: 20px; background-color: #f9f9f9; }
        .status-table { border-collapse: collapse; width: 100%; margin-top: 10px; }
        .status-table th, .status-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        .status-table th { background-color: #f2f2f2; }
    </style>
</head>
<body>
<h1>行動選択</h1>

<div class="messages">
    <c:forEach var="message" items="${messages}">
        <p>${message}</p>
    </c:forEach>
</div>

<%
    Character currentCharacter = (Character) request.getAttribute("currentCharacter");
    List<Monster> monsters = (List<Monster>) request.getAttribute("monsters");
    List<Weapon> availableWeapons = (List<Weapon>) request.getAttribute("availableWeapons");
%>

<h2><%= currentCharacter.getName() %>のターン</h2>
<p>現在の武器: <%= currentCharacter.getWeapon().getName() %> (攻撃力: <%= currentCharacter.getWeapon().getDamage() %>)</p>


<h3>敵一覧:</h3>
<table class="status-table">
    <thead>
    <tr>
        <th>No.</th>
        <th>名前</th>
        <th>HP</th>
        <th>状態</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="monster" items="${monsters}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${monster.name}${monster.suffix}</td>
            <td>${monster.hp}</td>
            <td>${monster.alive ? "生存" : "討伐済み"}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>行動選択:</h3>
<form action="battle" method="post">
    <input type="hidden" name="currentCharacterIndex" value="<%= ((com.example.game.GameState)session.getAttribute("gameState")).getCurrentCharacterIndex() %>">

    <p><strong>行動:</strong></p>
    <c:choose>
        <c:when test="<%= currentCharacter instanceof Hero && !(currentCharacter instanceof SuperHero) %>">
            <input type="radio" id="action_hero1" name="actionChoice" value="1" checked>
            <label for="action_hero1">基本攻撃</label><br>
            <input type="radio" id="action_hero2" name="actionChoice" value="2">
            <label for="action_hero2">強攻撃</label><br>
            <input type="radio" id="action_hero3" name="actionChoice" value="3">
            <label for="action_hero3">薙ぎ払い</label><br>
            <input type="radio" id="action_hero4" name="actionChoice" value="4">
            <label for="action_hero4">SuperHeroになる</label><br>
        </c:when>
        <c:when test="<%= currentCharacter instanceof SuperHero %>">
            <input type="radio" id="action_superhero1" name="actionChoice" value="1" checked>
            <label for="action_superhero1">攻撃</label><br>
        </c:when>
        <c:when test="<%= currentCharacter instanceof Wizard %>">
            <input type="radio" id="action_wiz1" name="actionChoice" value="1" checked>
            <label for="action_wiz1">攻撃 (石を投げる)</label><br>
            <input type="radio" id="action_wiz2" name="actionChoice" value="2">
            <label for="action_wiz2">魔法攻撃 (火の玉 MP:<%= ((Wizard)currentCharacter).getMp() %> / 消費MP:<%= currentCharacter.getWeapon().getCost() %>)</label><br>
            <input type="radio" id="action_wiz3" name="actionChoice" value="3">
            <label for="action_wiz3">氷の魔法 (MP:<%= ((Wizard)currentCharacter).getMp() %> / 消費MP:<%= currentCharacter.getWeapon().getCost() + 2 %>)</label><br>
        </c:when>
        <c:when test="<%= currentCharacter instanceof Thief %>">
            <input type="radio" id="action_thief1" name="actionChoice" value="1" checked>
            <label for="action_thief1">攻撃 (二回攻撃)</label><br>
            <input type="radio" id="action_thief2" name="actionChoice" value="2">
            <label for="action_thief2">不意打ち</label><br>
            <input type="radio" id="action_thief3" name="actionChoice" value="3">
            <label for="action_thief3">守る (ガード)</label><br>
        </c:when>
        <c:otherwise>
            <input type="radio" id="action1" name="actionChoice" value="1" checked>
            <label for="action1">攻撃</label><br>
        </c:otherwise>
    </c:choose>

    <br>
    <label for="targetMonster">攻撃対象のモンスターを選択:</label><br>
    <select id="targetMonster" name="targetMonsterIndex">
        <c:forEach var="monster" items="${monsters}" varStatus="loop">
            <c:if test="${monster.alive}">
                <option value="${loop.index}">${monster.name}${monster.suffix} (HP:${monster.hp})</option>
            </c:if>
        </c:forEach>
    </select>
    <br><br>

    <p><strong>武器を変更する:</strong></p>
    <input type="radio" id="weapon_none" name="weaponIndex" value="-1" checked>
    <label for="weapon_none">変更しない</label><br>
    <c:forEach var="weapon" items="${availableWeapons}" varStatus="loop">
        <input type="radio" id="weapon_${loop.index}" name="weaponIndex" value="${loop.index}">
        <label for="weapon_${loop.index}">${weapon.name} (攻撃力: ${weapon.damage}, 消費MP: ${weapon.cost})</label><br>
    </c:forEach>
    <br>

    <input type="submit" value="決定">
</form>
</body>
</html>
