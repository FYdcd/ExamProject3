package com.example.examproject3;

import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.weapon.Weapon;
import com.example.examproject3.weapon.Sword;
import com.example.examproject3.weapon.Wand;
import com.example.examproject3.weapon.Dagger;
import com.example.examproject3.weapon.Axe;
import com.example.examproject3.weapon.Bow;

import java.io.Serializable;
import java.util.*;

public class GameState implements Serializable {
    private List<Character> party;
    private List<Monster> monsters;
    private int currentCharacterIndex;
    private String partyName; // パーティ名を追加
    private List<Weapon> availableWeapons; // 利用可能な武器リストを追加

    public GameState() {
        this.party = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.currentCharacterIndex = 0;
        this.partyName = "未設定のパーティ"; // デフォルト名
        initializeAvailableWeapons();
    }

    private void initializeAvailableWeapons() {
        availableWeapons = new ArrayList<>();
        availableWeapons.add(new Sword());
        availableWeapons.add(new Wand());
        availableWeapons.add(new Dagger());
        availableWeapons.add(new Axe()); // 新しい武器を追加
        availableWeapons.add(new Bow()); // 新しい武器を追加
    }

    // GetterとSetter (以前のものは省略)
    public List<Character> getParty() {
        return party;
    }

    public void setParty(List<Character> party) {
        this.party = party;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public int getCurrentCharacterIndex() {
        return currentCharacterIndex;
    }

    public void setCurrentCharacterIndex(int currentCharacterIndex) {
        this.currentCharacterIndex = currentCharacterIndex;
    }

    public Character getCurrentCharacter() {
        if (currentCharacterIndex >= 0 && currentCharacterIndex < party.size()) {
            return party.get(currentCharacterIndex);
        }
        return null;
    }

    public void nextCharacter() {
        currentCharacterIndex++;
        while (currentCharacterIndex < party.size() && !party.get(currentCharacterIndex).isAlive()) {
            currentCharacterIndex++;
        }
    }

    public boolean isPartyAlive() {
        for (Character member : party) {
            if (member.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public boolean areMonstersAlive() {
        for (Monster monster : monsters) {
            if (monster.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public String getPartyName() { // Getter for partyName
        return partyName;
    }

    public void setPartyName(String partyName) { // Setter for partyName
        this.partyName = partyName;
    }

    public List<Weapon> getAvailableWeapons() { // Getter for availableWeapons
        return availableWeapons;
    }
}
