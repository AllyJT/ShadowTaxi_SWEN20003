
import bagel.Image;
public class Fireball extends Entity implements Damageable, Attacker{
    private double damage;
    private final Entity ownEnemyCar;
    public Fireball(Entity enemyCar,String string, double x, double y, double radius) {
        super(string, x, y, radius);
        this.ownEnemyCar = enemyCar;
    }

    public Entity getOwnEnemyCar() {
        return ownEnemyCar;
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
    public void moveDownAway() {

    }

    @Override
    public void moveUpAway() {

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
