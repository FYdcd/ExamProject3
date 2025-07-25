package creature;

public abstract class Monster implements Creature {
    private String name;
    private int hp;
    private char suffix;

    public Monster(String name, int hp, char suffix) {
        if (hp < 0) {
            throw new IllegalArgumentException("初期設定に誤りがあるため、キャラクターを作成できませんでした");
        }
        this.name = name;
        this.hp = hp;
        this.suffix = suffix;
    }

    @Override
    public final boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public void showStatus() {
        System.out.println(this.name + this.suffix + "：HP " + this.hp);
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

    public char getSuffix() {
        return suffix;
    }

    public void setSuffix(char suffix) {
        this.suffix = suffix;
    }

    public void run() { // runメソッドを追加
        System.out.println(this.name + this.suffix + "は逃げ出した");
    }

    public void die() { // dieメソッドを追加
        System.out.println(this.name + this.suffix + "を倒した！");
    }
}
