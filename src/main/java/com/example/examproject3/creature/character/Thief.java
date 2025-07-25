package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.weapon.Dagger; // Daggerをインポート
import com.example.examproject3.until.MessageHolder;
import com.example.examproject3.weapon.Weapon;

public class Thief extends Character {
    private boolean guard;

    public Thief(String name, int hp) {
        super(name, hp, new Dagger());
        this.guard = false;
    }

    @Override
    public String attack(Creature target) { // 基本攻撃（素早い2回攻撃）
        return doubleAttack(target);
    }

    public String doubleAttack(Creature target) {
        int damage = getWeapon().getDamage() * 2;
        String message = getName() + "は素早く2回攻撃した！" + target.getName() + "に" + damage + "のダメージを与えた！";
        target.setHp(target.getHp() - damage);
        MessageHolder.addMessage(message);
        return message;
    }

    public String sneakAttack(Creature target) { // 不意打ち (大ダメージ)
        int damage = getWeapon().getDamage() * 3;
        String message = getName() + "は影から不意打ちを仕掛け、" + target.getName() + "に" + damage + "のクリティカルダメージを与えた！";
        target.setHp(target.getHp() - damage);
        MessageHolder.addMessage(message);
        return message;
    }

    public String guard() {
        this.guard = true;
        String message = getName() + "は身構えた！次の攻撃を無効にする！";
        MessageHolder.addMessage(message);
        return message;
    }

    @Override
    public void setHp(int hp) {
        if (this.guard) {
            String message = "しかし、" + getName() + "は攻撃を回避し、ダメージが入らなかった！";
            MessageHolder.addMessage(message);
            this.guard = false;
        } else {
            super.setHp(hp);
        }
    }

    public boolean isGuard() {
        return guard;
    }

    public void setGuard(boolean guard) {
        this.guard = guard;
    }

    // 武器変更メソッドを追加
    public String changeWeapon(Weapon newWeapon) {
        String message = getName() + "は" + getWeapon().getName() + "から" + newWeapon.getName() + "に持ち替えた！";
        setWeapon(newWeapon);
        MessageHolder.addMessage(message);
        return message;
    }
}