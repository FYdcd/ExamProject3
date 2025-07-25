package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.weapon.Sword; // Swordをインポート
import until.MessageHolder;

public class Hero extends Character {

    public Hero(String name, int hp) {
        super(name, hp, new Sword());
    }

    @Override
    public String attack(Creature target) { // 戻り値をStringに変更
        String message = getName() + "は" + getWeapon().getName() + getWeapon().attackMessage() + target.getName() + "に" + getWeapon().getDamage() + "のダメージを与えた！";
        target.setHp(target.getHp() - getWeapon().getDamage());
        MessageHolder.addMessage(message);
        return message;
    }
}