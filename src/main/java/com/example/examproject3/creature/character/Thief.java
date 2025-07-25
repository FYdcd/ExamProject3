package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.creature.weapon.Dagger; // Daggerをインポート

public class Thief extends Character {
    private boolean guard;

    public Thief(String name, int hp) {
        super(name, hp, new Dagger()); // デフォルトで短剣を装備
        this.guard = false;
    }

    @Override
    public void attack(Creature target) {
        int damage = getWeapon().getDamage() * 2;
        System.out.println(getName() + "は素早く2回攻撃した！" + target.getName() + "に" + damage + "のダメージを与えた！");
        target.setHp(target.getHp() - damage);
    }

    public void guard() {
        this.guard = true;
        System.out.println(getName() + "は身構えた！次の攻撃を無効にする！");
    }

    @Override
    public void setHp(int hp) {
        if (this.guard) {
            System.out.println("しかし、" + getName() + "は攻撃を回避し、ダメージが入らなかった！");
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
}
