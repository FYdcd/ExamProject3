package com.example.examproject3.weapon;

public class Bow extends Weapon {
    public Bow() {
        super("弓", 7); // ダメージは低いが、特殊攻撃用など
    }

    @Override
    public String attackMessage() {
        return "で射抜いた！";
    }
}
