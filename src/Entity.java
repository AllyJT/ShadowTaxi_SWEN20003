import bagel.*;

import java.util.Properties;

abstract class Entity {
    private Image image;
    private double x;
    private double y;
    private double radius;
    private boolean visible = true;
    private boolean inCollision;
    private int speed;
    private double health;
    private Properties GAME_PROPS;

    /**
     * constructor of the entity class
     * @param gameProps is the game property file provided
     */
    public Entity(Properties gameProps) {
        this.GAME_PROPS = gameProps;
    }

    /**
     * constructor
     * @param string name of the file
     * @param x coord x
     * @param y coord y
     * @param radius radius of the entity
     */
    public Entity(String string, double x, double y, double radius) {
        this.image = new Image(string);
        this.x = x;
        this.y = y;
    }

    /**
     * constructor
     * @param x coord x
     * @param y coord y
     */
    public Entity(double x, double y){
        this.x = x;
        this.y = y;
    }


    /**
     * getters and setters
     */

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean getVisible(){
        return visible;
    }

    public void setInCollision(boolean inCollision) {
        this.inCollision = inCollision;
    }
    public boolean isInCollision(){
        return inCollision;
    }
    /**
     * movement  of the entity
     */
    public void moveDown(){
        y+= speed;
    }
    public void moveUp(){
        y-= speed;
    }
    public void moveLeft(){
        x-= speed;
    }
    public void moveRight(){
        x+=speed;
    }

    /**
     * rendering the object when visible
     */
    public void render(){
        if(visible){
            image.draw(x,y);
        }

    }
}
