import bagel.Image;

import java.util.Properties;

public class DamageTaxi extends Entity{
    private final Image DAMAGE_TAXI;
    private final Image FIRE;
    private int fireTimer;

    public DamageTaxi(Properties gameProps) {
        super(gameProps);
        this.DAMAGE_TAXI = new Image(gameProps.getProperty("gameObjects.taxi.damagedImage"));
        this.FIRE = new Image(gameProps.getProperty("gameObjects.fire.image"));
        this.fireTimer = Integer.parseInt(gameProps.getProperty("gameObjects.fire.ttl"));
    }

    @Override
    public void render() {
        DAMAGE_TAXI.draw(this.getX(),this.getY());
        if(fireTimer > 0) {
            FIRE.draw(this.getX(), this.getY());
        }
    }
    public void fireTimer(){
        if(fireTimer > 0){fireTimer--;}
    }
}
