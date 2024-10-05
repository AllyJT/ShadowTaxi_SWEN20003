import bagel.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EnemyCar extends OtherCar{
    private final Image ENEMY_CAR;
    private final String FIREBALL;
    private final double FIREBALL_RADIUS;
    private final int FIREBALL_SPEED;
    private List<Fireball> fireballList;

    public EnemyCar(Properties gameProps) {
        super(gameProps);
        this.ENEMY_CAR = new Image(gameProps.getProperty("gameObjects.enemyCar.image"));
        this.FIREBALL = new String(gameProps.getProperty("gameObjects.fireball.image"));
        this.FIREBALL_RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.radius"));
        this.FIREBALL_SPEED = Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.fireballList = new ArrayList<>();
    }

    public void shootFire(){
        if(MiscUtils.canSpawn(300)){
            Fireball fireball = new Fireball(FIREBALL,this.getX(),this.getY(),
                    FIREBALL_RADIUS);
            fireball.setSpeed(FIREBALL_SPEED);
            fireballList.add(fireball);
        }
    }

    @Override
    public void render() {
        if(ENEMY_CAR != null){
            ENEMY_CAR.draw(getX(),getY());
        }
        for(Fireball fireball : fireballList){
            fireball.moveUp();
            fireball.render();
        }
    }
}
