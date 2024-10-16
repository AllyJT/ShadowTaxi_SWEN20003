/**
 * Interface for Entity that is damagable
 */
public interface Damageable {
    double getHealth();
    void setHealth(double health);
    void moveDownAway();
    void moveUpAway();
}
