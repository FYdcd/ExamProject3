package com.example.examproject3;

import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.Monster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    private List<Character> party;
    private List<Monster> monsters;
    private int currentCharacterIndex; // 現在行動中のキャラクターのインデックス

    public GameState() {
        this.party = new ArrayList<>();
        this.monsters = new ArrayList<>();
        this.currentCharacterIndex = 0;
    }

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
}
