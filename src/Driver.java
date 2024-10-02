import java.util.Properties;

public class Driver extends Entity{
    public Driver(Properties gameProps) {
        super(gameProps);
        setHealth(Double.parseDouble(gameProps.getProperty("gameObjects.driver.health")));

    }
}
