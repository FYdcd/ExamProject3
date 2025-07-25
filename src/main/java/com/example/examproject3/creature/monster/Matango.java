package com.example.examproject3.creature.monster;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Monster;
import com.example.examproject3.until.MessageHolder;

public class Matango extends Monster {
    public Matango(char suffix, int hp) {
        super("お化けキノコ", hp, suffix);
    }

    @Override
    public String attack(Creature target) { // 戻り値をStringに変更
        String message = getName() + getSuffix() + "は体当たり攻撃！" + target.getName() + "に6のダメージを与えた！";
        target.setHp(target.getHp() - 6);
        MessageHolder.addMessage(message);
        return message;
    }
}
