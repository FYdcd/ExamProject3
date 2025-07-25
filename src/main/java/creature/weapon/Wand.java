package creature.weapon;

public class Wand extends Weapon {
    public Wand() {
        super("魔法の杖", 15);
        setCost(5); // costを5に設定
    }

    @Override
    public String attackMessage() {
        return "から魔法を放った！";
    }
}
