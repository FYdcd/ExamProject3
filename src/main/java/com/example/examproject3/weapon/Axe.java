package com.example.examproject3.weapon;

public class Axe extends Weapon {
    public Axe() {
        super("斧", 18); // 剣より高火力
    }

    @Override
    public String attackMessage() {
        return "で叩き斬った！";
    }
}