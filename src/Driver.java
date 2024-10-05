import java.util.Properties;

public class Driver extends Entity implements  Invincible, Damageable, TakeDamage{
    public boolean isInvincible;
    public int damage;
    public double health;
    public Driver(String string, double x, double y, double radius) {
        super(string, x, y, radius);
        setVisible(false);
    }

    @Override
    public void takeDamage(Damageable doDamage) {
        if(!isInvincible){
            int damageAmount = doDamage.getDamage();
            health = health - damageAmount;

        }
    }
    public int getDamage(){
        return damage;
    }
    @Override
    public boolean hasCollied() {
        return false;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }
}
