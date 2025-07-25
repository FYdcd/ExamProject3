package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.weapon.Wand; // Wandをインポート
import com.example.examproject3.until.MessageHolder;
import com.example.examproject3.weapon.Weapon;

public class Wizard extends Character {
    private int mp;

    public Wizard(String name, int hp, int mp) {
        super(name, hp, new Wand());
        this.mp = mp;
    }

    @Override
    public String attack(Creature target) { // 石を投げる
        String message = getName() + "は石を投げた！" + target.getName() + "に3のダメージを与えた！";
        target.setHp(target.getHp() - 3);
        MessageHolder.addMessage(message);
        return message;
    }

    public String fireBall(Creature target) { // 火の玉
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

    public String iceBlast(Creature target) { // 氷の魔法
        int cost = getWeapon().getCost() + 2; // 消費MP増加
        int damage = getWeapon().getDamage() + 5; // ダメージ増加
        if (this.mp < cost) {
            String message = "MPが足りない！";
            MessageHolder.addMessage(message);
            return message;
        }
        String message = getName() + "は冷気の魔法を放ち、" + target.getName() + "に" + damage + "のダメージを与えた！";
        target.setHp(target.getHp() - damage);
        setMp(getMp() - cost);
        MessageHolder.addMessage(message);
        return message;
    }

    @Override
    public String showStatus() {
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

    // 武器変更メソッドを追加
    public String changeWeapon(Weapon newWeapon) {
        String message = getName() + "は" + getWeapon().getName() + "から" + newWeapon.getName() + "に持ち替えた！";
        setWeapon(newWeapon);
        MessageHolder.addMessage(message);
        return message;
    }
}