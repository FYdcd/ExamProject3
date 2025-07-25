package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.weapon.Sword; // Swordをインポート

public class Hero extends Character {

    public Hero(String name, int hp) { // weaponフィールドを削除し、引数からweaponを削除
        super(name, hp, new Sword()); // デフォルトで剣を装備
    }

    @Override
    public void attack(Creature target) {
        System.out.println(getName() + "は" + getWeapon().getName() + getWeapon().attackMessage() + target.getName() + "に" + getWeapon().getDamage() + "のダメージを与えた！");
        target.setHp(target.getHp() - getWeapon().getDamage());
    }
}