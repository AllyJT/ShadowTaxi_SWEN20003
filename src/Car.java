import java.util.Properties;

public abstract class Car extends Entity implements Attacker {
    private double damage;
    private double health;
    private boolean isRebounding; // New property
    private int reboundCountdown;

    public Car(Properties gameProps) {
        super(gameProps);
        this.isRebounding = false; // Initialize to not rebounding
        this.reboundCountdown = 0; // Initialize countdown
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    @Override
    public void attack(Damageable target) {
        if(target != null) {
            target.setHealth(target.getHealth() - this.getDamage());
        }
    }

//    @Override
//    public void setVisible(boolean visible) {
//        super.setVisible(visible);
//        if(!visible){
//            health = 0;
//        }
//    }


}

