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
    private int fireballRate;
    private int yCoord1;
    private int yCoord2;

    public EnemyCar(Properties gameProps) {
        super(gameProps);
        this.ENEMY_CAR = new Image(gameProps.getProperty("gameObjects.enemyCar.image"));
        this.setRadius(Double.parseDouble(gameProps.getProperty("gameObjects.enemyCar.radius")));
        this.FIREBALL = new String(gameProps.getProperty("gameObjects.fireball.image"));
        this.FIREBALL_RADIUS = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.radius"));
        this.FIREBALL_SPEED = Integer.parseInt(gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
        this.FIRE_BALL_DAMAGE = Double.parseDouble(gameProps.getProperty("gameObjects.fireball.damage"));
        this.fireballList = new ArrayList<>();
        this.fireballRate = 300;
        this.yCoord1 = -50;
        this.yCoord2 = 768;

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
    /* METHOD */

    /**
     * randomly select between two y coord for the enemy car
     * @return y coord for enemy car
     */
    private double selectLaneY() {
        int[] laneYposition = {yCoord1, yCoord2};
        return laneYposition[MiscUtils.selectAValue(0, 1)];
    }

    /**
     * randomly select between 3 lane for enemy car
     * @return lane position for enemy car
     */

    private double selectLaneX() {
        return laneXposition[MiscUtils.getRandomInt(0, laneXposition.length-1)];
    }

    /**
     * spawn fireball
     */

    public void shootFire(){
        if(MiscUtils.canSpawn(fireballRate)){
            Fireball fireball = new Fireball(this,FIREBALL,this.getX(),this.getY(),
                    FIREBALL_RADIUS);
            fireball.setSpeed(FIREBALL_SPEED);
            fireball.setDamage(FIRE_BALL_DAMAGE*100);
            fireballList.add(fireball);
        }
    }

    public void setFireballList(List<Fireball> fireballList) {
        this.fireballList = fireballList;
    }

    public List<Fireball> getFireballList() {
        return fireballList;
    }

    /**
     * render enemy car
     */
    @Override
    public void render() {
        if (ENEMY_CAR != null) {
            ENEMY_CAR.draw(getX(), getY());
        }
    }

    /**
     * render fireball and handle the collision when fireball hit any of the damagable entity
     * @param entities
     */
    public void renderFireball(List<Entity> entities){
        List<Fireball> fireballsToRemove = new ArrayList<>();
        for (Fireball fireball : fireballList) {
            fireball.moveUp();
            fireball.render();
            fireballCollision(entities,fireball);
            // Check if fireball should be removed
            if (!fireball.getVisible()) {
                fireballsToRemove.add(fireball);
            }
        }
        fireballList.removeAll(fireballsToRemove);
    }

    /**
     * handle fireball attack
     * @param entities list of entity that can get damage from fireball
     * @param fireball fireball that is attacking
     */
    public void fireballCollision(List<Entity> entities, Fireball fireball) {
        for (Entity entity : entities) {
            // check if the entity is not fireball's enemy car
            if (Utilities.checkCollision(entity, fireball) &&
                    !entity.equals(fireball.getOwnEnemyCar()) &&
                    entity.getVisible()) {
                if(entity instanceof Invincible){
                    Invincible inv = (Invincible) entity;
                    if(!inv.isInvincible()){
                        fireball.attack((Damageable) entity);
                    }
                }else{fireball.attack((Damageable) entity);}
                fireball.setVisible(false);
                break;
            }
        }
    }

}
