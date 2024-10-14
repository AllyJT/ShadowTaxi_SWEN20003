import bagel.Image;

import java.util.Properties;

public abstract class Car extends Entity implements Attacker, Damageable {
    private double damage;
    private double health;
    private int fireTimer;
    private Properties GAME_PROPS;

    public Car(Properties gameProps) {
        super(gameProps);
        this.GAME_PROPS = gameProps;// Initialize countdown
        this.setMoving(false);
    }

    public int getFireTimer() {
        return fireTimer;
    }

    public void setFireTimer(int fireTimer) {
        this.fireTimer = fireTimer;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }


    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public void attack(Damageable target) {
        if(target != null && target.getHealth() > 0 ) {
            target.setHealth(target.getHealth() - this.getDamage());
        }
    }

}

