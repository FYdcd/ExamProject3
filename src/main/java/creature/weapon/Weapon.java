package creature.weapon;

public abstract class Weapon {
    private String name;
    private int damage;
    private int cost;

    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
        this.cost = 0; // 初期値0
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public abstract String attackMessage();
}
