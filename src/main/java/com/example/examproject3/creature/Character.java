package com.example.examproject3.creature;

import com.example.examproject3.weapon.Weapon;
import com.example.examproject3.until.MessageHolder; // MessageHolderをインポート

public abstract class Character implements Creature {
    private String name;
    private int hp;
    private Weapon weapon;

    public Character(String name, int hp, Weapon weapon) {
        if (hp < 0) {
            throw new IllegalArgumentException("初期設定に誤りがあるため、キャラクターを作成できませんでした");
        }
        this.name = name;
        this.hp = hp;
        this.weapon = weapon;
    }

    @Override
    public final boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public String showStatus() { // 戻り値をStringに変更
        String status = this.name + "：HP " + this.hp;
        MessageHolder.addMessage(status);
        return status;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String die() { // 戻り値をStringに変更
        String message = this.name + "は死んでしまった！";
        MessageHolder.addMessage(message);
        return message;
    }

    // attackメソッドはサブクラスで実装されるためここでは変更なし
    // ただし、サブクラスのattackもStringを返すように変更する
}