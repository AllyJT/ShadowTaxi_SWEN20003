
import bagel.Image;
import java.util.Properties;

public class EnemyCar extends Entity{
    private final Image ENEMY_CAR;
    private final Image FIREBALL;

    public EnemyCar(Properties gameProps) {
        super(gameProps);
        this.ENEMY_CAR = new Image(gameProps.getProperty("gameObjects.enemyCar.imag"));
        this.FIREBALL = new Image(gameProps.getProperty("gameObjects.fireball.image"));

    }
}
