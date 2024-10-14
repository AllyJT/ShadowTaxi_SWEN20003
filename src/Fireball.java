
import bagel.Image;

import java.util.Properties;

public class Fireball extends Entity implements Damageable, Attacker{
    private double damage;
    public Fireball(String string, double x, double y, double radius) {
        super(string, x, y, radius);

    }

    @Override
    public void render() {
        Image image = this.getImage();
        if(image != null){
            image.draw(this.getX(),this.getY());
        }
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public void setHealth(double health) {

    }


    @Override
    public double getDamage() {
        return 0;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }
    @Override
    public void attack(Damageable target) {
        double targetHealth = target.getHealth();
        target.setHealth(targetHealth - this.damage);
    }
}
