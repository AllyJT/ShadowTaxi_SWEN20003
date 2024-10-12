import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Collision {
    private Taxi taxi;
    private DamageTaxi damageTaxi1;
    private List<OtherCar> otherCarList;
    private List<DamageTaxi> damageTaxiList = new ArrayList<>();
    private List<EnemyCar> enemyCarList = new ArrayList<>();
    private int countDown = 0;
    private int moveAwayCountDown = 0;
    private boolean isCollided = false;
    private OtherCar currentCollidedCar = null;
    private EnemyCar currentColliedEnemy = null;
    private Taxi currentTaxi = null;
    private Properties GAME_PROPS;
    private Properties MESSAGE_PROPS;

    private final int noDamageTimeOut = 200;
    private final int movingTimeOut = 10;

    public Collision(Taxi taxi, List<OtherCar> otherCarList, Properties gameProp,
                     Properties messageProp) {
        this.GAME_PROPS = gameProp;
        this.MESSAGE_PROPS = messageProp;
        this.taxi = taxi;
        this.otherCarList = otherCarList;
    }

    public void checkCollisions() {
        if (!isCollided) {
            for (OtherCar otherCar : otherCarList) {
                if (Utilities.checkCollision(otherCar, taxi)) {
                    isCollided = true;
                    countDown = noDamageTimeOut; // Set the countdown
                    moveAwayCountDown = movingTimeOut;
                    currentCollidedCar = otherCar;
                    currentTaxi = taxi;
                    break; // Exit after the first collision
                }
            }

        }
//        for (int i = 0; i < otherCarList.size(); i++) {
//            OtherCar car1 = otherCarList.get(i);
//            for (int j = i + 1; j < otherCarList.size(); j++) {
//                OtherCar car2 = otherCarList.get(j);
//                if (Utilities.checkCollision(car1, car2)) {
//                    // Handle collision between two other cars
//                    handleOtherCarCollision(car1, car2);
//                }
//            }
//        }

        handleCollision();
    }

    private void handleCollision() {
        if (isCollided) {
            // Logic for handling the collision and moving the cars
            if (currentCollidedCar != null && currentTaxi!=null) {
                // Display the countdown or other logic as needed
                currentCollidedCar.attack(currentTaxi);
                currentTaxi.attack(currentCollidedCar);

                // Move the cars based on their positions
                // Example movement logic (customize as needed)
                if(moveAwayCountDown >= 0 ) {
                    moveAwayCountDown--;
                    if (currentTaxi.getY() < currentCollidedCar.getY() && countDown > 0) {
                        currentTaxi.moveUp();
                        currentCollidedCar.moveDown();
                    } else if (currentTaxi.getY() >= currentCollidedCar.getY() && countDown > 0) {
                        currentTaxi.moveDown();
                        currentCollidedCar.moveUp();
                    }
                }
            }
            countDown--;
            if (countDown <= 0) {
                isCollided = false;
                currentCollidedCar = null;
            }
        }
        if (currentCollidedCar != null && currentCollidedCar.getHealth() <= 0) {
            currentCollidedCar.setVisible(false); // Hide the car if health is zero
            currentCollidedCar = null;
        }
        if (currentTaxi != null && currentTaxi.getHealth() <= 0) {
            currentTaxi.setVisible(false);
            renderDamageTaxi();
            taxi.setX(MiscUtils.selectAValue(360,620));
            taxi.setY(MiscUtils.selectAValue(200,400));
            taxi.setHealth(100);
            taxi.setVisible(true);
            taxi.setHasDriver(false);
        }

    }
    private void renderDamageTaxi(){
        damageTaxi1 = new DamageTaxi(GAME_PROPS);
        damageTaxi1.setX(currentTaxi.getX());
        damageTaxi1.setY(currentTaxi.getY());
        damageTaxi1.setSpeed(Integer.parseInt(GAME_PROPS.getProperty("gameObjects.taxi.speedY")));
        damageTaxiList.add(damageTaxi1);
        currentTaxi = null;

    }
    public void renderDamageTaxiList(){
        if(!damageTaxiList.isEmpty()){
            for(DamageTaxi damageTaxi: damageTaxiList){
                damageTaxi.render();
            }
        }
    }
    public void moveDamageTaxiList(){
        if(!damageTaxiList.isEmpty()){
            for(DamageTaxi damageTaxi: damageTaxiList){
                damageTaxi.moveDown();
            }
        }
    }
    public void handleOtherCarCollision(OtherCar car1, OtherCar car2){
        car1.attack(car2);
        car2.attack(car1);
        if(moveAwayCountDown >= 0 ) {
            moveAwayCountDown--;
            if (car1.getY() < car2.getY() && countDown > 0) {
                car1.moveUp();
                car2.moveDown();
            } else if (car1.getY() >= car2.getY() && countDown > 0) {
                car1.moveDown();
                car2.moveUp();
            }
        }

    }
}