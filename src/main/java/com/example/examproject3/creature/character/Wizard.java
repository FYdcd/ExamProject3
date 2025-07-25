package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.weapon.Wand; // Wandをインポート
import until.MessageHolder;

public class Wizard extends Character {
    private int mp;

    public Wizard(String name, int hp, int mp) {
        super(name, hp, new Wand());
        this.mp = mp;
    }

    public String magic(Creature target) { // 戻り値をStringに変更
        if (this.mp < getWeapon().getCost()) {
            String message = "MPが足りない！";
            MessageHolder.addMessage(message);
            return message;
        }
        String message = getName() + getWeapon().attackMessage() + target.getName() + "に" + getWeapon().getDamage() + "のダメージを与えた！";
        target.setHp(target.getHp() - getWeapon().getDamage());
        setMp(getMp() - getWeapon().getCost());
        MessageHolder.addMessage(message);
        return message;
    }

    @Override
    public String attack(Creature target) { // 戻り値をStringに変更
        String message = getName() + "は石を投げた！" + target.getName() + "に3のダメージを与えた！";
        target.setHp(target.getHp() - 3);
        MessageHolder.addMessage(message);
        return message;
    }

    @Override
    public String showStatus() { // 戻り値をStringに変更
        String status = getName() + "：HP " + getHp() + " MP " + this.mp;
        MessageHolder.addMessage(status);
        return status;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }
}
