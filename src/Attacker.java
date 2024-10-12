public interface Attacker {
    void attack(Damageable target);
    double getDamage();
    void setDamage(double damage);
}
