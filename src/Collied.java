import bagel.Image;
import java.util.Properties;

public abstract class Collied{
    private final Image SMOKE;
    private final Image FIRE;
    private int timeFrame = 10;
    private int collisionTimer = 0;
    private boolean inCollision;
    public Collied(Properties gameProps) {
        this.SMOKE = new Image(gameProps.getProperty("gameObjects.smoke.image"));
        this.FIRE = new Image(gameProps.getProperty("gameObjects.fire.image"));
        this.inCollision = false;

    }

}
