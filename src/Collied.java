import bagel.Image;
import java.util.Properties;

public class Collied extends Entity{
    private final Image SMOKE;
    private final Image FIRE;
    public Collied(Properties gameProps) {
        super(gameProps);
        this.SMOKE = new Image(gameProps.getProperty("gameObjects.smoke.image"));
        this.FIRE = new Image(gameProps.getProperty("gameObjects.fire.image"));


    }
}
