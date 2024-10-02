import bagel.Image;

import java.util.Properties;

public class Car extends Entity{
    private final Image TAXI;
    private final Image DAMAGE_TAXI;
    private int speed;


    public Car(Properties gameProps) {
        super(gameProps);
        this.TAXI = new Image(gameProps.getProperty("gameObjects.taxi.image"));
        this.DAMAGE_TAXI = new Image(gameProps.getProperty("gameObjects.taxi.damagedImage"));

        setVisible(true);
    }
    public void render(){
        if(getVisible()){
            TAXI.draw(this.getX(),this.getY());
        }
    }
}


