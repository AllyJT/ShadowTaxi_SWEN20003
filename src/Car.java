import bagel.Image;

import java.util.Properties;

public abstract class Car extends Entity implements Attacker, Damageable {
    private double damage;
    private double health;
    private final Image FIRE;
    private int fireTimer;
    private final Image SMOKE;
    private int smokeRenderTime;
    private final int smokeTime = 10;
    private final int moveAwaySpeed = 1;
    private boolean isCollied;
    private Properties GAME_PROPS;

    public Car(Properties gameProps) {
        super(gameProps);
        this.GAME_PROPS = gameProps;// Initialize countdown
        this.setMoving(false);

        this.FIRE = new Image(gameProps.getProperty("gameObjects.fire.image"));
        this.fireTimer = Integer.parseInt(gameProps.getProperty("gameObjects.fire.ttl"));
        this.SMOKE = new Image(gameProps.getProperty("gameObjects.smoke.image"));
        this.smokeRenderTime = smokeTime;
    }

    /**
     * check timer of the car when it is dead
     */
    public void fireTimer(){
        if(fireTimer > 0){fireTimer--;}
    }

    /**
     * render car for a certain number of frame, 20 frame
     */
    public void renderFire(){
        if(fireTimer > 0){
            FIRE.draw(this.getX(), this.getY());
        }
    }

    /**
     * render smoke for the collsion between car and car
     */
    public void renderSmoke(){
        if(this.isMoving()){
            SMOKE.draw(this.getX(), this.getY());
        }
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    /**
     * move away method
     */
    @Override
    public void moveDownAway(){
        setY(this.getY() + moveAwaySpeed);
    }
    @Override
    public void moveUpAway(){
        setY(this.getY() - moveAwaySpeed);
    }


    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * car attack damagable entity
     * @param target the target
     */

    @Override
    public void attack(Damageable target) {
        if(target != null && target.getHealth() > 0 ) {
            target.setHealth(target.getHealth() - this.getDamage());
        }
    }

}

