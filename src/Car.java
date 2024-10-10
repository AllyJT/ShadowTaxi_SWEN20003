import java.util.Properties;

public abstract class Car extends Entity implements Attacker {
    private double damage;
    private double health;

    public Car(Properties gameProps) {
        super(gameProps);
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
    public void setDamage(double damage) {
        this.damage = damage;
    }
    @Override
    public void attack(Damageable target) {
        double targetHealth = target.getHealth();
        target.setHealth(targetHealth - this.damage);
    }

//    private boolean isCollising = false;
//    private boolean isActive;
//    private int counDown;
//    public void collisingWithCar(Entity dm1){
//        if(!isCollising){
//            if(Utilities.checkCollision(dm1, this)){
//                this.isActive = true;
//                this.counDown = 10;
//                this.isCollising = true;
//                Damageable dm2 = (Damageable) dm1;
//                if(dm2.getHealth())
//
//            }
//        }
//    }
//
//    public void countingDown(){
//        if(isActive){
//            counDown--;
//            if(counDown < 0){
//                isActive = false;
//                isCollising = false;
//            }
//        }
//    }
}

