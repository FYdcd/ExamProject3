package com.example.examproject3.creature.monster;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Monster;

public class Goblin extends Monster {
    public Goblin(char suffix, int hp) {
        super("ゴブリン", hp, suffix);
    }

    @Override
    public void attack(Creature target) {
        System.out.println(getName() + getSuffix() + "はナイフで切りつけた！" + target.getName() + "に8のダメージを与えた！");
        target.setHp(target.getHp() - 8);
    }
}
