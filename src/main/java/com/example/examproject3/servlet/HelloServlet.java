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
import java.util.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GETリクエストの場合は初期画面を表示
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = new GameState();

        // パーティ名の設定
        String partyName = request.getParameter("partyName");
        if (partyName == null || partyName.trim().isEmpty()) {
            partyName = "名もなきパーティ"; // デフォルト名
        }
        gameState.setPartyName(partyName);

        // パーティの初期化 (4人編成)
        List<Character> party = new ArrayList<>();
        try {
            party.add(new Hero("勇者1", 100)); // 勇者1人目
            party.add(new Hero("勇者2", 90)); // 勇者2人目 (例として追加)
            party.add(new Wizard("魔法使い", 60, 30));
            party.add(new Thief("盗賊", 70));
        } catch (IllegalArgumentException e) {
            MessageHolder.addMessage("エラーが発生しました。\nゲームを開始できません。: " + e.getMessage());
            request.setAttribute("messages", MessageHolder.getMessages());
            MessageHolder.clearMessages();
            request.getRequestDispatcher("/hello.jsp").forward(request, response); // エラー表示後、再試行させる
            return;
        }
        gameState.setParty(party);

        // 敵グループの生成 (合計5体、乱数とMapを使用)
        List<Monster> monsters = new ArrayList<>();
        Random rand = new Random();
        // 敵の種類ごとの枝番を管理するMap
        Map<String, Integer> monsterCounts = new HashMap<>();
        monsterCounts.put("Matango", 0);
        monsterCounts.put("Goblin", 0);
        monsterCounts.put("Slime", 0);

        try {
            for (int i = 0; i < 5; i++) {
                int monsterType = rand.nextInt(3); // 0:マタンゴ, 1:ゴブリン, 2:スライム
                char suffix;
                int hp;
                String monsterKind;

                switch (monsterType) {
                    case 0:
                        monsterKind = "Matango";
                        suffix = (char) ('A' + monsterCounts.get(monsterKind));
                        monsterCounts.put(monsterKind, monsterCounts.get(monsterKind) + 1);
                        hp = 45;
                        monsters.add(new Matango(suffix, hp));
                        break;
                    case 1:
                        monsterKind = "Goblin";
                        suffix = (char) ('A' + monsterCounts.get(monsterKind));
                        monsterCounts.put(monsterKind, monsterCounts.get(monsterKind) + 1);
                        hp = 50;
                        monsters.add(new Goblin(suffix, hp));
                        break;
                    case 2:
                        monsterKind = "Slime";
                        suffix = (char) ('A' + monsterCounts.get(monsterKind));
                        monsterCounts.put(monsterKind, monsterCounts.get(monsterKind) + 1);
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

        MessageHolder.addMessage("---" + gameState.getPartyName() + "---");
        for (Character member : party) {
            member.showStatus();
        }
        MessageHolder.addMessage("---敵グループ---");
        for (Monster monster : monsters) {
            monster.showStatus();
        }
        MessageHolder.addMessage("\n戦闘開始！");

        request.setAttribute("messages", MessageHolder.getMessages());
        MessageHolder.clearMessages();
        request.getRequestDispatcher("/hello.jsp").forward(request, response);
    }
}
