import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Driver extends Entity implements Invincible,Damageable{
    private Image driver;
    private boolean isInvincible;
    private int speedX;
    private int speedY;

    public double health;

    public Driver(Properties gameProps) {
        super(gameProps);
        this.driver =  new Image(gameProps.getProperty("gameObjects.driver.image"));
        this.setRadius(Double.parseDouble(gameProps.getProperty("gameObjects.driver.radius")));
        this.setSpeedX(Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedX")));
        this.setSpeedY(Integer.parseInt(gameProps.getProperty("gameObjects.driver.walkSpeedX")));
        setVisible(false);
    }


    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

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

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    @Override
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }
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

    @Override
    public void render() {
        if(getVisible()){
            driver.draw(getX(),getY());
        }
    }
}
