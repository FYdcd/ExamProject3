package com.example.examproject3.creature.character;

import com.example.examproject3.creature.Creature;
import com.example.examproject3.creature.Character;
import com.example.examproject3.weapon.Dagger; // Daggerをインポート
import com.example.examproject3.until.MessageHolder;

public class Thief extends Character {
    private boolean guard;

    public Thief(String name, int hp) {
        super(name, hp, new Dagger());
        this.guard = false;
    }

    @Override
    public String attack(Creature target) { // 戻り値をStringに変更
        int damage = getWeapon().getDamage() * 2;
        String message = getName() + "は素早く2回攻撃した！" + target.getName() + "に" + damage + "のダメージを与えた！";
        target.setHp(target.getHp() - damage);
        MessageHolder.addMessage(message);
        return message;
    }

    public String guard() { // 戻り値をStringに変更
        this.guard = true;
        String message = getName() + "は身構えた！次の攻撃を無効にする！";
        MessageHolder.addMessage(message);
        return message;
    }

    @Override
    public void setHp(int hp) { // 戻り値はvoidのまま、メッセージ追加
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
}