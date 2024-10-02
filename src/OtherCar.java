import bagel.Image;

import java.util.Properties;

public class OtherCar extends Entity{
    private final Image CAR_1;
    private final Image CAR_2;
    public OtherCar(Properties gameProps) {
        super(gameProps);
        this.CAR_1 = new Image(gameProps.getProperty("gameObjects.otherCar.image"));
        this.CAR_2 = new Image(gameProps.getProperty("gameObjects.otherCar.image"));


    }
}
