import bagel.Image;

import java.util.Properties;

public class OtherCar extends Car implements Movable, Damageable {
    private Image currentImage;
    private int laneXposition[];
    private double damage;

    public OtherCar(Properties gameProps) {
        super(gameProps);
        //super(selectLaneX(),MiscUtils.getRandomInt());
        this.currentImage = new Image(String.format(gameProps.getProperty
                ("gameObjects.otherCar.image"), MiscUtils.selectAValue(1, 2)));
        this.setRadius(Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.radius")));
        laneXposition = new int[3];
        laneXposition[0] = Integer.parseInt(gameProps.getProperty("roadLaneCenter1"));
        laneXposition[1] = Integer.parseInt(gameProps.getProperty("roadLaneCenter2"));
        laneXposition[2] = Integer.parseInt(gameProps.getProperty("roadLaneCenter3"));
        this.setHealth(Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.health"))*100);
        this.setX(selectLaneX());
        this.setY(selectLaneY());
        this.setDamge(Double.parseDouble(gameProps.getProperty("gameObjects.otherCar.damage")));
    }

    private double selectLaneY() {
        int[] laneYposition = {-50, 768};
        return laneYposition[MiscUtils.selectAValue(0, 1)];
    }

    private double selectLaneX() {
        return laneXposition[MiscUtils.getRandomInt(0, laneXposition.length-1)];
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    @Override
    public void render() {
        if (currentImage != null & getVisible()) {
            currentImage.draw(getX(), getY());
        }
    }

    @Override
    public void setDamge(double damage) {
        this.damage = damage;
    }

}
