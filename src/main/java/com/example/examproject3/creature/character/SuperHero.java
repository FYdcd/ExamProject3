package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;

public class SuperHero extends Hero {
    public SuperHero(Hero hero) {
        super(hero.getName(), hero.getHp()); // Heroのコンストラクタを呼び出し
        setWeapon(hero.getWeapon()); // 武器を引き継ぐ
        setHp(getHp() - 30); // 代償として30のダメージを受ける
        System.out.println(hero.getName() + "はスーパーヒーローになった！");
    }

    @Override
    public void attack(Creature target) {
        int damage = (int) (getWeapon().getDamage() * 2.5);
        System.out.println(getName() + "は" + getWeapon().getName() + getWeapon().attackMessage() + target.getName() + "に" + damage + "のダメージを与えた！");
        target.setHp(target.getHp() - damage);
    }
}
