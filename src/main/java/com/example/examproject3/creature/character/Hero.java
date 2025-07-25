package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.weapon.Sword; // Swordをインポート
import com.example.examproject3.until.MessageHolder;
import com.example.examproject3.weapon.Weapon;

public class Hero extends Character {

    public Hero(String name, int hp) {
        super(name, hp, new Sword());
    }

    @Override
    public String attack(Creature target) { // 基本攻撃
        return basicAttack(target);
    }

    public String basicAttack(Creature target) {
        String message = getName() + "は" + getWeapon().getName() + getWeapon().attackMessage() + target.getName() + "に" + getWeapon().getDamage() + "のダメージを与えた！";
        target.setHp(target.getHp() - getWeapon().getDamage());
        MessageHolder.addMessage(message);
        return message;
    }

    public String heavyAttack(Creature target) { // 強攻撃
        int damage = getWeapon().getDamage() + 5; // 基本ダメージ+5
        String message = getName() + "は渾身の" + getWeapon().getName() + getWeapon().attackMessage() + target.getName() + "に" + damage + "のダメージを与えた！";
        target.setHp(target.getHp() - damage);
        MessageHolder.addMessage(message);
        return message;
    }

    public String slashAttack(Creature target) { // 範囲攻撃 (ここでは単体だが演出として)
        int damage = (int)(getWeapon().getDamage() * 0.8); // 少しダメージ減
        String message = getName() + "は" + getWeapon().getName() + "で薙ぎ払い、" + target.getName() + "に" + damage + "のダメージを与えた！";
        target.setHp(target.getHp() - damage);
        MessageHolder.addMessage(message);
        return message;
    }

    // 武器変更メソッドを追加
    public String changeWeapon(Weapon newWeapon) {
        String message = getName() + "は" + getWeapon().getName() + "から" + newWeapon.getName() + "に持ち替えた！";
        setWeapon(newWeapon);
        MessageHolder.addMessage(message);
        return message;
    }
}