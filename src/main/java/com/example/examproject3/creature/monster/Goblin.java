package com.example.examproject3.creature.monster;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Monster;
import until.MessageHolder;

public class Goblin extends Monster {
    public Goblin(char suffix, int hp) {
        super("ゴブリン", hp, suffix);
    }

    @Override
    public String attack(Creature target) {
        String message = getName() + getSuffix() + "はナイフで切りつけた！" + target.getName() + "に8のダメージを与えた！";
        target.setHp(target.getHp() - 8);
        MessageHolder.addMessage(message);
        return message;
    }
}
