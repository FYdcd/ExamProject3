package com.example.examproject3.servlet;

import com.example.examproject3.GameState;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.creature.character.Hero;
import com.example.examproject3.creature.character.SuperHero;
import com.example.examproject3.creature.character.Thief;
import com.example.examproject3.creature.character.Wizard;
import com.example.examproject3.until.MessageHolder;
import com.example.examproject3.weapon.Weapon;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/battle")
public class BattleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null || !gameState.isPartyAlive() || !gameState.areMonstersAlive()) {
            response.sendRedirect(request.getContextPath() + "/battle_end");
            return;
        }

        Character attacker = gameState.getCurrentCharacter();
        if (attacker == null || !attacker.isAlive()) {
            gameState.nextCharacter();
            response.sendRedirect(request.getContextPath() + "/select");
            return;
        }

        int actionChoice = -1;
        int targetMonsterIndex = -1;
        int weaponIndex = -1; // 武器変更用

        try {
            actionChoice = Integer.parseInt(request.getParameter("actionChoice"));
            if (request.getParameter("targetMonsterIndex") != null && !request.getParameter("targetMonsterIndex").isEmpty()) {
                targetMonsterIndex = Integer.parseInt(request.getParameter("targetMonsterIndex"));
            }
            if (request.getParameter("weaponIndex") != null && !request.getParameter("weaponIndex").isEmpty()) {
                weaponIndex = Integer.parseInt(request.getParameter("weaponIndex"));
            }
        } catch (NumberFormatException e) {
            MessageHolder.addMessage("無効な入力です。");
            request.setAttribute("messages", MessageHolder.getMessages());
            MessageHolder.clearMessages();
            request.getRequestDispatcher("/select.jsp").forward(request, response);
            return;
        }

        Monster targetMonster = null;
        if (targetMonsterIndex != -1 && targetMonsterIndex < gameState.getMonsters().size()) {
            targetMonster = gameState.getMonsters().get(targetMonsterIndex);
        }

        // 武器変更処理 (選択された場合)
        if (weaponIndex != -1) {
            List<Weapon> availableWeapons = gameState.getAvailableWeapons();
            if (weaponIndex >= 0 && weaponIndex < availableWeapons.size()) {
                Weapon newWeapon = availableWeapons.get(weaponIndex);
                // CharacterクラスにchangeWeaponメソッドがあることを前提
                if (attacker instanceof Hero) { // Heroとそのサブクラス
                    ((Hero) attacker).changeWeapon(newWeapon);
                } else if (attacker instanceof Wizard) {
                    ((Wizard) attacker).changeWeapon(newWeapon);
                } else if (attacker instanceof Thief) {
                    ((Thief) attacker).changeWeapon(newWeapon);
                }
            } else {
                MessageHolder.addMessage("選択された武器は存在しません。");
            }
        }


        // 攻撃処理
        if (attacker instanceof Hero) {
            Hero currentHero = (Hero) attacker;
            switch (actionChoice) {
                case 1: // 基本攻撃
                    if (targetMonster != null) currentHero.basicAttack(targetMonster);
                    break;
                case 2: // 強攻撃
                    if (targetMonster != null) currentHero.heavyAttack(targetMonster);
                    break;
                case 3: // 薙ぎ払い
                    if (targetMonster != null) currentHero.slashAttack(targetMonster);
                    break;
                case 4: // SuperHeroになる
                    if (!(currentHero instanceof SuperHero)) {
                        SuperHero superHero = new SuperHero(currentHero);
                        if (!superHero.isAlive()) {
                            superHero.die();
                            gameState.getParty().remove(currentHero);
                            MessageHolder.addMessage(currentHero.getName() + "はスーパーヒーローになろうとしたが、力尽きてしまった！");
                        } else {
                            int index = gameState.getParty().indexOf(currentHero);
                            if (index != -1) {
                                gameState.getParty().set(index, superHero);
                            }
                            MessageHolder.addMessage(currentHero.getName() + "はスーパーヒーローに進化した！");
                        }
                    }
                    break;
            }
        } else if (attacker instanceof Wizard) {
            Wizard currentWizard = (Wizard) attacker;
            switch (actionChoice) {
                case 1: // 石を投げる
                    if (targetMonster != null) currentWizard.attack(targetMonster);
                    break;
                case 2: // 火の玉
                    if (targetMonster != null) currentWizard.fireBall(targetMonster);
                    break;
                case 3: // 氷の魔法
                    if (targetMonster != null) currentWizard.iceBlast(targetMonster);
                    break;
            }
        } else if (attacker instanceof Thief) {
            Thief currentThief = (Thief) attacker;
            switch (actionChoice) {
                case 1: // 通常攻撃 (2回攻撃)
                    if (targetMonster != null) {
                        currentThief.doubleAttack(targetMonster);
                        response.sendRedirect(request.getContextPath() + "/monster_attack"); // 盗賊は攻撃後すぐに敵ターンへ
                        return; // ここで処理を終了
                    }
                    break;
                case 2: // 不意打ち
                    if (targetMonster != null) {
                        currentThief.sneakAttack(targetMonster);
                        response.sendRedirect(request.getContextPath() + "/monster_attack"); // 盗賊は攻撃後すぐに敵ターンへ
                        return; // ここで処理を終了
                    }
                    break;
                case 3: // ガード
                    currentThief.guard();
                    break;
            }
        }

        // モンスターのHPチェックと状態変化
        if (targetMonster != null && !targetMonster.isAlive()) {
            targetMonster.die();
            Iterator<Monster> it = gameState.getMonsters().iterator();
            while (it.hasNext()) {
                if (it.next() == targetMonster) {
                    it.remove();
                    break;
                }
            }
        } else if (targetMonster != null && targetMonster.getHp() <= 5) {
            targetMonster.run();
            Iterator<Monster> it = gameState.getMonsters().iterator();
            while (it.hasNext()) {
                if (it.next() == targetMonster) {
                    it.remove();
                    break;
                }
            }
        }

        // ゲーム終了条件の確認
        if (!gameState.areMonstersAlive()) {
            response.sendRedirect(request.getContextPath() + "/battle_end");
            return;
        }

        // 次のキャラクターのターンへ
        gameState.nextCharacter();
        response.sendRedirect(request.getContextPath() + "/select");
    }
}