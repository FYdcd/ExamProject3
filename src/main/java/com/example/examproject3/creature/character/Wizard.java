package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.weapon.Wand; // Wandをインポート

public class Wizard extends Character {
    private int mp;

    public Wizard(String name, int hp, int mp) {
        super(name, hp, new Wand()); // デフォルトで魔法の杖を装備
        this.mp = mp;
    }

    // magicメソッドに名称変更
    public void magic(Creature target) {
        if (this.mp < getWeapon().getCost()) {
            System.out.println("MPが足りない！");
            return;
        }
        System.out.println(getName() + getWeapon().attackMessage() + target.getName() + "に" + getWeapon().getDamage() + "のダメージを与えた！");
        target.setHp(target.getHp() - getWeapon().getDamage());
        setMp(getMp() - getWeapon().getCost());
    }

    @Override
    public void attack(Creature target) {
        System.out.println(getName() + "は石を投げた！" + target.getName() + "に3のダメージを与えた！");
        target.setHp(target.getHp() - 3);
    }

    @Override
    public void showStatus() {
        System.out.println(getName() + "：HP " + getHp() + " MP " + this.mp);
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }
}
