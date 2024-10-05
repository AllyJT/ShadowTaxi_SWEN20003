import java.util.Properties;

public class Car extends Entity {
    private double damage;
    private double health;

    public Car(Properties gameProps) {
        super(gameProps);
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }
}
