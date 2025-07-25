package com.example.examproject3.servlet;

import com.example.examproject3.GameState;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.creature.character.Hero;
import com.example.examproject3.creature.character.Thief;
import com.example.examproject3.creature.character.Wizard;
import com.example.examproject3.creature.monster.Goblin;
import com.example.examproject3.creature.monster.Matango;
import com.example.examproject3.creature.monster.Slime;
import com.example.examproject3.until.MessageHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = new GameState();

        // パーティの初期化
        List<Character> party = new ArrayList<>();
        try {
            party.add(new Hero("勇者", 100));
            party.add(new Wizard("魔法使い", 60, 30));
            party.add(new Thief("盗賊", 70));
        } catch (IllegalArgumentException e) {
            MessageHolder.addMessage("エラーが発生しました。\nゲームを開始できません。: " + e.getMessage());
            request.setAttribute("messages", MessageHolder.getMessages());
            MessageHolder.clearMessages();
            request.getRequestDispatcher("/hello.jsp").forward(request, response);
            return;
        }
        gameState.setParty(party);

        // 敵グループの生成 (合計5体、ランダムな種類と枝番)
        List<Monster> monsters = new ArrayList<>();
        Random rand = new Random();
        int matangoCount = 0;
        int goblinCount = 0;
        int slimeCount = 0;

        try {
            for (int i = 0; i < 5; i++) {
                int monsterType = rand.nextInt(3); // 0:マタンゴ, 1:ゴブリン, 2:スライム
                char suffix;
                int hp;

                switch (monsterType) {
                    case 0:
                        suffix = (char) ('A' + matangoCount++);
                        hp = 45;
                        monsters.add(new Matango(suffix, hp));
                        break;
                    case 1:
                        suffix = (char) ('A' + goblinCount++);
                        hp = 50;
                        monsters.add(new Goblin(suffix, hp));
                        break;
                    case 2:
                        suffix = (char) ('A' + slimeCount++);
                        hp = 40;
                        monsters.add(new Slime(suffix, hp));
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            MessageHolder.addMessage("エラーが発生しました。\nゲームを開始できません。: " + e.getMessage());
            request.setAttribute("messages", MessageHolder.getMessages());
            MessageHolder.clearMessages();
            request.getRequestDispatcher("/hello.jsp").forward(request, response);
            return;
        }
        gameState.setMonsters(monsters);
        session.setAttribute("gameState", gameState);

        MessageHolder.addMessage("---味方パーティ---");
        for (Character member : party) {
            member.showStatus();
        }
        MessageHolder.addMessage("---敵グループ---");
        for (Monster monster : monsters) {
            monster.showStatus();
        }
        MessageHolder.addMessage("\n戦闘開始！");

        request.setAttribute("messages", MessageHolder.getMessages());
        MessageHolder.clearMessages(); // メッセージをクリア
        request.getRequestDispatcher("/hello.jsp").forward(request, response);
    }
}
