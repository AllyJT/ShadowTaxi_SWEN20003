import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Collision {
    private Taxi taxi;
    private DamageTaxi damageTaxi1;
    private List<Car> carList;
    private List<DamageTaxi> damageTaxiList = new ArrayList<>();
    private int countDown = 0;
    private int moveAwayCountDown = 0;
    private boolean isCollided = false;
    private Car currentCollidedEntity = null;
    private List<Entity> fireList = new ArrayList<>();
    private Taxi currentTaxi = null;
    private Fireball currentCollidedFireball = null;
    private Properties GAME_PROPS;
    private Properties MESSAGE_PROPS;

    private Entity blood;
    private Entity smoke;
    private int speed;
    public List<Entity> smokeList = new ArrayList<>();

    private final int noDamageTimeOut = 200;
    private final int movingTimeOut = 10;
    private final int renderBloodTimer = 20;
    private int bloodTimer;


    public Collision(Taxi taxi, List<Car> carList, Properties gameProp,
                     Properties messageProp) {
        this.GAME_PROPS = gameProp;
        this.MESSAGE_PROPS = messageProp;
        this.taxi = taxi;
        this.speed = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.taxi.speedY"));
        this.carList = carList;
    }

    public void checkCollisions() {
        if (!isCollided) {
            for (Car car : carList) {
                if (Utilities.checkCollision(car, taxi)) {
                    isCollided = true;
                    countDown = noDamageTimeOut; // Set the countdown
                    moveAwayCountDown = movingTimeOut;
                    currentTaxi = taxi;
                    currentCollidedEntity = car;
                    removingCurrentColliedEntity(car);
                    break; // Exit after the first collision
                }

            }
        }

        handleCollision();
    }

    private boolean movingAway = false;

    private void handleCollision() {
        if (isCollided && countDown > 0) {
            if (currentCollidedEntity != null && currentTaxi != null) {
                // Display the countdown or other logic as needed
                if (!movingAway && currentTaxi.getHealth()>0
                        && currentCollidedEntity.getHealth() > 0) {
                    if(!currentTaxi.isInvincible()) {
                        currentCollidedEntity.attack(currentTaxi);
                    }
                    currentTaxi.attack(currentCollidedEntity);
                }
                if (moveAwayCountDown > 0) {
                    //renderSmokeList();
                    currentTaxi.setMoving(true);
                    currentCollidedEntity.setMoving(true);
                    movingAway = true;
                    moveAwayCountDown--;
                    mover(currentTaxi, currentCollidedEntity);

                }
                if (moveAwayCountDown <= 0) {
                    currentTaxi.setMoving(false);
                    currentCollidedEntity.setMoving(false);
                    movingAway = false;
                    currentCollidedEntity.setSpeed(setNewSpeed());
                }
            }
            countDown--;
            if (countDown <= 0) {
                currentCollidedEntity = null;
                isCollided = false;
                currentTaxi = null;
            }

        }
        if (currentCollidedEntity != null && currentCollidedEntity.getHealth() <= 0) {
            if (moveAwayCountDown <= 0) {
                currentCollidedEntity.setVisible(false);
                currentCollidedEntity = null;
            }

        }

        resetTaxiPosition();
    }

    private void removingCurrentColliedEntity(Car car) {
        if (car.getHealth() <= 0) {
            //car.setVisible(false);
            carList.remove(car);
        }
    }

    public void checkCarCollisions() {
        for (int i = 0; i < carList.size(); i++) {
            Car car1 = carList.get(i);
            for (int j = i + 1; j < carList.size(); j++) {
                Car car2 = carList.get(j);
                if (Utilities.checkCollision(car1, car2) &&
                        car1.getVisible() && car2.getVisible()) {
                    car1.setInCollision(true);
                    car2.setInCollision(true);
                    car1.setCollisionTimer(noDamageTimeOut);
                    car1.setMovingAwayTimer(moveAwayCountDown);
                    renderSFX(car1, car2);
                    handleCarCollision(car1, car2);
                }
            }
        }
    }

    public void renderSFX(Car car1, Car car2) {
        clearSmoke();
        accidentRenderSmoke(car1);
        accidentRenderSmoke(car2);
    }

    public void handleCarCollision(Car car1, Car car2) {
        if (car1.isInCollision() && car2.isInCollision()) {
            if (!car1.isMoving() && !car2.isMoving()
            && car1.getHealth() > 0 && car2.getHealth() > 0) {
                car1.attack(car2);
                car2.attack(car1);
            }
            if (car1.getCollisionTimer() > 0) {
                renderSmokeList();
                car1.setMoving(true);
                car2.setMoving(true);
                mover(car1, car2);
                car1.setMovingAwayTimer(car1.getMovingAwayTimer() - 1);
            }
            if (car1.getMovingAwayTimer() <= 0) {
                car1.setMoving(false);
                car2.setMoving(false);
                car1.setSpeed(setNewSpeed());
                car2.setSpeed(setNewSpeed());
            }
            car1.setCollisionTimer(car1.getCollisionTimer() - 1);
        }
        if (car1.getCollisionTimer() <= 0) {
            car1.setInCollision(false);
            car2.setInCollision(false);
        }
        removeCar(car1);
        removeCar(car2);
    }

    private void removeCar(Car car) {
        if (car.getHealth() <= 0) {
            car.fireTimer();
            car.renderFire();
            if (!car.isMoving()) {
                car.setVisible(false);
                carList.remove(car);
            }
        }
    }

    public void mover(Car car1, Car car2) {
        if (car1.getY() > car2.getY()) {
            car1.moveDownAway();
            car2.moveUpAway();
        } else {
            car1.moveUpAway();
            car2.moveDownAway();
        }
    }

    public int setNewSpeed() {
        return MiscUtils.getRandomInt(Integer.parseInt(
                        GAME_PROPS.getProperty("gameObjects.enemyCar.minSpeedY")),
                Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyCar.maxSpeedY")));
    }

    private void resetTaxiPosition() {
        if (currentTaxi != null && currentTaxi.getHealth() <= 0) {
            currentTaxi.setVisible(false);
            if (moveAwayCountDown == 0) {
                renderDamageTaxi();
                taxi.setX(MiscUtils.selectAValue(360, 620));
                taxi.setY(MiscUtils.selectAValue(200, 400));
                taxi.setHealth(100);
                taxi.setVisible(true);
                taxi.setHasDriver(false);
            }

        }

    }

    private void renderDamageTaxi() {
        damageTaxi1 = new DamageTaxi(GAME_PROPS);
        damageTaxi1.setX(currentTaxi.getX());
        damageTaxi1.setY(currentTaxi.getY());
        damageTaxi1.setSpeed(speed);
        damageTaxiList.add(damageTaxi1);
        currentTaxi = null;

    }

    public void renderDamageTaxiList() {
        if (!damageTaxiList.isEmpty()) {
            for (DamageTaxi damageTaxi : damageTaxiList) {
                damageTaxi.fireTimer();
                damageTaxi.render();
            }
        }
    }

    public void moveDamageTaxiList() {
        moveSmoke();
        if (!damageTaxiList.isEmpty()) {
            for (DamageTaxi damageTaxi : damageTaxiList) {
                damageTaxi.moveDown();
            }
        }
    }

    public void renderSmokeList() {
        if (!smokeList.isEmpty()) {
            for (Entity smoke : smokeList) {
                smoke.render();
            }
        }

    }

    public void moveSmoke() {
        if (!smokeList.isEmpty()) {
            for (Entity smoke : smokeList) {
                smoke.moveDown();
            }
        }

    }

    public void clearSmoke() {
        smokeList.clear();
    }

    public void accidentRenderSmoke(Car car) {
        smoke = new Entity(GAME_PROPS.getProperty("gameObjects.smoke.image"));
        smoke.setX(car.getX());
        smoke.setY(car.getY());
        smoke.setSpeed(speed);
        smokeList.add(smoke);
    }

    public void killHuman(Entity human){
        for(Car car: carList){
            if(Utilities.checkCollision(car,human) && !isCollided){
                //human.setCollisionTimer(noDamageTimeOut);
                human.setMovingAwayTimer(movingTimeOut);
                human.setMoving(false);
                human.setInCollision(true);
                colliedWithHuman(car,human);
                break;
            }

        }
    }
    public void colliedWithHuman(Car car, Entity human) {
        Damageable vulnerable = (Damageable) human;
        // If the human is in collision
        if (human.isInCollision()) {
            // Apply damage if not moving
            if (!human.isMoving() && ((Damageable) human).getHealth() > 0
            && car.getHealth() > 0) {
                if (human instanceof Invincible && !((Invincible) human).isInvincible()) {
                    car.attack(vulnerable);
                } else {
                    car.attack(vulnerable);
                }
            }

            // Handle moving away logic
            if (human.getMovingAwayTimer() > 0) {
                human.setMoving(true);
                // Move away from the car
                if (human.getY() > car.getY()) {
                    vulnerable.moveUpAway();
                } else {
                    vulnerable.moveDownAway();
                }
                human.setMovingAwayTimer(human.getMovingAwayTimer() - 1);
            }

            if (human.getMovingAwayTimer() <= 0) {
                human.setMoving(false);
                //human.setInCollision(false);
            }

            human.setCollisionTimer(human.getCollisionTimer() - 1);
        }

        if (human.getCollisionTimer() < 0) {
            human.setInCollision(false);
        }
    }

}