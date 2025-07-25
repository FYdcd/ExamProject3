package com.example.examproject3.creature;

import com.example.examproject3.creature.weapon.Weapon;

public abstract class Character implements Creature {
    private String name;
    private int hp;
    private Weapon weapon; // Weapon型のフィールドを追加

    public Character(String name, int hp, Weapon weapon) { // weaponを引数に追加
        if (hp < 0) {
            throw new IllegalArgumentException("初期設定に誤りがあるため、キャラクターを作成できませんでした");
        }
        this.name = name;
        this.hp = hp;
        this.weapon = weapon; // weaponを初期化
    }

    @Override
    public final boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public void showStatus() {
        System.out.println(this.name + "：HP " + this.hp);
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

    public Weapon getWeapon() { // weaponのgetterを追加
        return weapon;
    }

    public void setWeapon(Weapon weapon) { // weaponのsetterを追加
        this.weapon = weapon;
    }

    public void die() { // dieメソッドを追加
        System.out.println(this.name + "は死んでしまった！");
    }
}