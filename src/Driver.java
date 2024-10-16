import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Driver extends Entity implements Damageable, Invincible,CoinActivate{
    private Image driver;
    private Passenger passenger = null;
    private boolean isInvincible;
    private final int moveAwaySpeed = 2;
    private int speedX;
    private int speedY;
    private final Image BLOOD;
    private int bloodTimer;

    public double health;

    public Driver(Properties gameProps) {
        super(gameProps);
        this.BLOOD = new Image(gameProps.getProperty("gameObjects.blood.image"));
        this.bloodTimer = Integer.parseInt(gameProps.getProperty("gameObjects.blood.ttl"));

        this.driver =  new Image(gameProps.getProperty("gameObjects.driver.image"));
        this.setRadius(Double.parseDouble(gameProps.getProperty("gameObjects.driver.radius")));
        this.setSpeedX(Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedX")));
        this.setSpeedY(Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedX")));
        this.setHealth(Double.parseDouble(gameProps.getProperty("gameObjects.driver.health"))*100);
        this.setInCollision(false);
        setVisible(false);
    }

    // GETTERS AND SETTERS

    public Passenger getPassenger() {
        return passenger;
    }
    public boolean hasPassenger(){
        return passenger!=null;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
    public double getHealth() {
        return health;
    }


    public void setHealth(double health) {
        this.health = health;
    }
    @Override
    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }


    /**
     * render blood with timer
     */
    public void bloodTimer(){
        if(bloodTimer > 0){bloodTimer--;}
    }

    public void renderBlood() {
        if(bloodTimer > 0){
            BLOOD.draw(this.getX(), this.getY());
        }
    }

    /**
     * moving the driver
     */
    @Override
    public void moveDown() {
        setY(this.getY() +  speedY);
    }

    @Override
    public void moveLeft() {
        setX(this.getX() -  speedX);
    }

    @Override
    public void moveRight() {
        setX(this.getX() +  speedX);
    }

    @Override
    public void moveUp() {
        setY(this.getY() -  speedY);
    }


    public void moveDownAway(){
        setY(this.getY() + moveAwaySpeed);
        setX(this.getX() + moveAwaySpeed);
    }

    public void moveUpAway(){
        setY(this.getY() - moveAwaySpeed);
        setX(this.getX() - moveAwaySpeed);
    }

    /**
     * control driver
     * @param input key
     */
    public void move(Input input){
        if(input.wasPressed(Keys.LEFT) || input.isDown(Keys.LEFT)){
            moveLeft();
        }
        else if(input.wasPressed(Keys.RIGHT) || input.isDown(Keys.RIGHT)){
            moveRight();
        }
        else if (input.wasPressed(Keys.DOWN) || input.isDown(Keys.DOWN)) {
            moveDown();
        }
        else if(input.wasPressed(Keys.UP) || input.isDown(Keys.UP)){
            moveUp();
        }
    }

    /**
     * render driver
     */
    @Override
    public void render() {
        if(getVisible()){
            driver.draw(getX(),getY());
        }
    }
}
