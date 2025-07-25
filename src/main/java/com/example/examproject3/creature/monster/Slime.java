package com.example.examproject3.creature.monster;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Monster;
import until.MessageHolder;

public class Slime extends Monster {
    public Slime(char suffix, int hp) {
        super("スライム", hp, suffix);
    }

    @Override
    public String attack(Creature target) {
        String message = getName() + getSuffix() + "は体当たり攻撃！" + target.getName() + "に5のダメージを与えた！";
        target.setHp(target.getHp() - 5);
        MessageHolder.addMessage(message);
        return message;
    }
}
