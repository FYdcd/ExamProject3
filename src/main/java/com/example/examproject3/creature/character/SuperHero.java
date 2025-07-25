package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import until.MessageHolder;

public class SuperHero extends Hero {
    public SuperHero(Hero hero) {
        super(hero.getName(), hero.getHp());
        setWeapon(hero.getWeapon());
        setHp(getHp() - 30);
        MessageHolder.addMessage(hero.getName() + "はスーパーヒーローになった！"); // メッセージ追加
    }

    @Override
    public String attack(Creature target) { // 戻り値をStringに変更
        int damage = (int) (getWeapon().getDamage() * 2.5);
        String message = getName() + "は" + getWeapon().getName() + getWeapon().attackMessage() + target.getName() + "に" + damage + "のダメージを与えた！";
        target.setHp(target.getHp() - damage);
        MessageHolder.addMessage(message);
        return message;
    }
}
