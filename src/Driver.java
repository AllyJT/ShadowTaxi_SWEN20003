import java.util.Properties;

public class Driver extends Entity implements Invincible,Damageable{
    public boolean isInvincible;

    public double health;
    public Driver(String string, double x, double y, double radius) {
        super(string, x, y, radius);
        setVisible(false);
    }


    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {

    }



    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }
}
