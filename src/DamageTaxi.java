import bagel.Image;

import java.util.Properties;

public class DamageTaxi extends Entity{
    private final Image DAMAGE_TAXI;

    public DamageTaxi(Properties gameProps) {
        super(gameProps);
        this.DAMAGE_TAXI = new Image(gameProps.getProperty("gameObjects.taxi.damagedImage"));
    }

    @Override
    public void render() {
        DAMAGE_TAXI.draw(this.getX(),this.getY());
    }
}
