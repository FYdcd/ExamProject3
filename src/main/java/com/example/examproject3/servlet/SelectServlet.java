package com.example.examproject3.servlet;

import com.example.examproject3.GameState;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.until.MessageHolder;
import com.example.examproject3.weapon.Weapon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/select")
public class SelectServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null || !gameState.isPartyAlive() || !gameState.areMonstersAlive()) {
            response.sendRedirect(request.getContextPath() + "/battle_end");
            return;
        }

        // 行動できる次のキャラクターを探す
        Character currentCharacter = gameState.getCurrentCharacter();
        while (currentCharacter == null || !currentCharacter.isAlive()) {
            gameState.nextCharacter();
            if (gameState.getCurrentCharacterIndex() >= gameState.getParty().size()) {
                // 全員のターンが終わった場合、モンスターのターンへ
                gameState.setCurrentCharacterIndex(0); // ターン終了後、インデックスをリセット
                response.sendRedirect(request.getContextPath() + "/monster_attack");
                return;
            }
            currentCharacter = gameState.getCurrentCharacter();
        }

        request.setAttribute("currentCharacter", currentCharacter);
        request.setAttribute("monsters", gameState.getMonsters());
        request.setAttribute("availableWeapons", gameState.getAvailableWeapons()); // 利用可能な武器リストを渡す

        // メッセージ表示
        MessageHolder.addMessage("\n--- " + currentCharacter.getName() + " のターン ---");
        MessageHolder.addMessage("--- " + gameState.getPartyName() + " ---");
        for (Character member : gameState.getParty()) {
            member.showStatus();
        }
        MessageHolder.addMessage("--- 敵グループ ---");
        for (Monster monster : gameState.getMonsters()) {
            monster.showStatus();
        }

        request.setAttribute("messages", MessageHolder.getMessages());
        MessageHolder.clearMessages();

        request.getRequestDispatcher("/select.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}