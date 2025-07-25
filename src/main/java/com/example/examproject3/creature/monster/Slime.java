package com.example.examproject3.creature.monster;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Monster;

public class Slime extends Monster {
    public Slime(char suffix, int hp) {
        super("スライム", hp, suffix);
    }

    @Override
    public void attack(Creature target) {
        System.out.println(getName() + getSuffix() + "は体当たり攻撃！" + target.getName() + "に5のダメージを与えた！");
        target.setHp(target.getHp() - 5);
    }
}
