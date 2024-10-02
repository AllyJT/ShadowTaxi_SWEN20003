import bagel.*;

import java.util.Properties;

public class Entity {
    private Image image;
    private double x;
    private double y;
    private double radius;
    private boolean visible = true;
    private int speed;
    private int health;
    private Properties GAME_PROPS;

    public Entity(Properties gameProps) {
        this.GAME_PROPS = gameProps;
    }

    public Entity(String string, double x, double y){
        this.image = new Image(string);
        this.x = x;
        this.y = y;
    }
    public Entity(String string, double x, double y , double radius,int speed,
                  int health) {
        this.image = new Image(string);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.health = health;
    }



    /**
     * getters and setters
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getRadius() {
        return radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * movement
     */
    public void moveDown(){
        y+= speed;
    }
    public void moveLeft(){
        x-= speed;
    }
    public void moveRight(){
        x+=speed;
    }

    /**
     * rendering the object
     */
    public void render(){
        if(visible){
            image.draw(x,y);
        }

    }
}
