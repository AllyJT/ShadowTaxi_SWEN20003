import bagel.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EnemyCar extends Car {
    private final Image ENEMY_CAR;
    private final String FIREBALL;
    private final double FIREBALL_RADIUS;
    private final int FIREBALL_SPEED;
    private final double FIRE_BALL_DAMAGE;
    private List<Fireball> fireballList;
    private double damage;
    private int laneXposition[];

    public EnemyCar(Properties gameProps) {
        super(gameProps);
        this.ENEMY_CAR = new Image(gameProps.getProperty("gameObjects.enemyCar.image"));
        this.setRadius(Double.parseDouble(gameProps.getProperty("gameObjects.enemyCar.radius")));
        this.FIREBALL = new String(gameProps.getProperty("gameObjects.fireball.image"));
        this.FIREBALL_RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.radius"));
        this.FIREBALL_SPEED = Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.FIRE_BALL_DAMAGE = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.damage"));
        this.fireballList = new ArrayList<>();

        this.setSpeed(MiscUtils.getRandomInt(Integer.parseInt(gameProps.getProperty("gameObjects.enemyCar.minSpeedY")),
                Integer.parseInt(gameProps.getProperty("gameObjects.enemyCar.maxSpeedY"))));
        //enemyCar.setDamage(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemyCar.health")) * 100);
        this.setHealth(Double.parseDouble(gameProps.getProperty("gameObjects.enemyCar.health")) * 100);

        laneXposition = new int[3];
        laneXposition[0] = Integer.parseInt(gameProps.getProperty("roadLaneCenter1"));
        laneXposition[1] = Integer.parseInt(gameProps.getProperty("roadLaneCenter2"));
        laneXposition[2] = Integer.parseInt(gameProps.getProperty("roadLaneCenter3"));
        this.setX(selectLaneX());
        this.setY(selectLaneY());
        this.setDamage(Double.parseDouble(gameProps.getProperty("gameObjects.enemyCar.damage"))*100);
    }
    private double selectLaneY() {
        int[] laneYposition = {-50, 768};
        return laneYposition[MiscUtils.selectAValue(0, 1)];
    }

    private double selectLaneX() {
        return laneXposition[MiscUtils.getRandomInt(0, laneXposition.length-1)];
    }

    public void shootFire(){
        if(MiscUtils.canSpawn(300)){
            Fireball fireball = new Fireball(FIREBALL,this.getX(),this.getY(),
                    FIREBALL_RADIUS);
            fireball.setSpeed(FIREBALL_SPEED);
            fireball.setDamage(FIRE_BALL_DAMAGE);
            fireballList.add(fireball);
        }
    }

    public void setFireballList(List<Fireball> fireballList) {
        this.fireballList = fireballList;
    }

    public List<Fireball> getFireballList() {
        return fireballList;
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
