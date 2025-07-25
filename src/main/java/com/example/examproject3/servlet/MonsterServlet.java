package com.example.examproject3.servlet;

import com.example.examproject3.GameState;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.creature.character.Thief;
import com.example.examproject3.until.MessageHolder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@WebServlet("/monster_attack")
public class MonsterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null || !gameState.isPartyAlive() || !gameState.areMonstersAlive()) {
            response.sendRedirect(request.getContextPath() + "/battle_end");
            return;
        }

        MessageHolder.addMessage("\n--- 敵の攻撃フェーズ ---");

        Random rand = new Random();
        List<Monster> attackingMonsters = new ArrayList<>(gameState.getMonsters()); // ConcurrentModificationException対策

        for (Monster attackerM : attackingMonsters) {
            if (!attackerM.isAlive()) {
                continue;
            }
            if (!gameState.isPartyAlive()) {
                break; // 味方が全滅したら攻撃終了
            }

            // ランダムな味方をターゲット
            List<Character> alivePartyMembers = new ArrayList<>();
            for (Character member : gameState.getParty()) {
                if (member.isAlive()) {
                    alivePartyMembers.add(member);
                }
            }

            if (alivePartyMembers.isEmpty()) {
                break; // 攻撃対象がいなければ終了
            }

            Character targetCharacter = alivePartyMembers.get(rand.nextInt(alivePartyMembers.size()));

            // 盗賊のガード判定を先に処理
            if (targetCharacter instanceof Thief) {
                Thief thief = (Thief) targetCharacter;
                if (thief.isGuard()) {
                    thief.setHp(thief.getHp()); // ガードが発動し、HPは変わらずガードが解除される
                    MessageHolder.addMessage(thief.getName() + "への攻撃は回避された！"); // メッセージを追加
                } else {
                    attackerM.attack(targetCharacter);
                }
            } else {
                attackerM.attack(targetCharacter);
            }


            // 味方キャラクターのHPチェックと死亡処理
            if (!targetCharacter.isAlive()) {
                targetCharacter.die();
                // Iteratorを使って安全にリストから削除
                Iterator<Character> it = gameState.getParty().iterator();
                while (it.hasNext()) {
                    if (it.next() == targetCharacter) {
                        it.remove();
                        break;
                    }
                }
            }
        }

        // ゲーム終了条件の確認
        if (!gameState.isPartyAlive()) {
            response.sendRedirect(request.getContextPath() + "/battle_end");
            return;
        }
        if (!gameState.areMonstersAlive()) { // 敵が全滅した場合も終了
            response.sendRedirect(request.getContextPath() + "/battle_end");
            return;
        }

        // 次のターンへ
        gameState.setCurrentCharacterIndex(0); // 次のターン開始時にキャラクターのインデックスをリセット
        request.setAttribute("messages", MessageHolder.getMessages());
        MessageHolder.clearMessages();
        request.getRequestDispatcher("/monster_turn.jsp").forward(request, response);
    }
}
