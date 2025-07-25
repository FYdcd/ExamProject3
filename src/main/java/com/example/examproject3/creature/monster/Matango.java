package com.example.examproject3.creature.monster;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Monster;

public class Matango extends Monster {
    public Matango(char suffix, int hp) {
        super("お化けキノコ", hp, suffix);
    }

    @Override
    public void attack(Creature target) {
        System.out.println(getName() + getSuffix() + "は体当たり攻撃！" + target.getName() + "に6のダメージを与えた！");
        target.setHp(target.getHp() - 6);
    }
}
