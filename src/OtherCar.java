import bagel.Image;

import java.util.Properties;

public class OtherCar extends Car {
    private Image currentImage;
    private int laneXposition[];

    public OtherCar(Properties gameProps) {
        super(gameProps);
        //super(selectLaneX(),MiscUtils.getRandomInt());
        this.currentImage = new Image(String.format(gameProps.getProperty
                ("gameObjects.otherCar.image"), MiscUtils.selectAValue(1, 2)));

        laneXposition = new int[3];
        laneXposition[0] = Integer.parseInt(gameProps.getProperty("roadLaneCenter1"));
        laneXposition[1] = Integer.parseInt(gameProps.getProperty("roadLaneCenter2"));
        laneXposition[2] = Integer.parseInt(gameProps.getProperty("roadLaneCenter3"));
        this.setX(selectLaneX());
        this.setY(selectLaneY());
    }

    private double selectLaneY() {
        int[] laneYposition = {-50, 768};
        return laneYposition[MiscUtils.selectAValue(0, 1)];
    }

    private double selectLaneX() {
        return laneXposition[MiscUtils.selectAValue(0, laneXposition.length - 1)];
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    @Override
    public void render() {
        if (currentImage != null) {
            currentImage.draw(getX(), getY());
        }
    }

}
