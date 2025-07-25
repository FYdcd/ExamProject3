package com.example.examproject3.servlet;

import com.example.examproject3.GameState;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.until.MessageHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/battle_end")
public class BattleEndServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            MessageHolder.addMessage("ゲームの状態が読み込めませんでした。");
        } else if (!gameState.isPartyAlive()) {
            MessageHolder.addMessage(gameState.getPartyName() + "は全滅してしまった…");
        } else if (!gameState.areMonstersAlive()) {
            MessageHolder.addMessage("敵を全て倒した" + gameState.getPartyName() + "は勝利した!");
        } else {
            MessageHolder.addMessage("戦闘が終了しました。"); // 想定外の終了
        }

        // 最終ステータス表示
        MessageHolder.addMessage("\n---" + gameState.getPartyName() + "最終ステータス---");
        for (Character member : gameState.getParty()) {
            member.showStatus();
            if (member.isAlive()) {
                MessageHolder.addMessage(member.getName() + "：生存");
            } else {
                MessageHolder.addMessage(member.getName() + "：死亡");
            }
        }

        MessageHolder.addMessage("\n---敵グループ最終ステータス---");
        for (Monster monster : gameState.getMonsters()) {
            monster.showStatus();
            if (monster.isAlive()) {
                MessageHolder.addMessage(monster.getName() + monster.getSuffix() + "：生存");
            } else {
                MessageHolder.addMessage(monster.getName() + monster.getSuffix() + "：討伐済み");
            }
        }

        request.setAttribute("messages", MessageHolder.getMessages());
        MessageHolder.clearMessages();
        session.invalidate(); // セッションを終了

        request.getRequestDispatcher("/battle_end.jsp").forward(request, response);
    }
}