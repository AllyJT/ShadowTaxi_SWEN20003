import bagel.Font;
import bagel.Input;
import bagel.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GameScreen {
    private int speed;
    private Road road;
    private OtherCar otherCar;
    private EnemyCar enemyCar;
    private Passenger passenger;
    private TripEndFlag tripEndFlag;
    private Coin coin;
    private Coin newestCoin = null;
    private InviciblePower inviciblePower;
    private Collision collisionHandler;
    private final double penaltyRate;

    private final String PLAYER_NAME;
    private boolean savedData;

    private Taxi taxi;
    private Driver driver;
    private Font font;
    private Font passengerFont;

    private int currentFrame;
    private int MaxFrame;
    private double target;

    private final double ratePerY;
    private double[] priority;

    private Properties GAME_PROPS;
    private Properties MESSAGE_PROPS;

    private final double[] gameplayValues;
    private final String[][] GAME_OBJECT;
    private String[] passengerStrings;
    private final double[] passengerValues;
    private final String[] gameplayStrings;
    private List<Car> carList = new ArrayList<>();
    private List<Entity> entitiesList = new ArrayList<>();

    private int powerX;
    private int powerY;
    int passengerSpeedX;
    int passengerSpeedY;
    private final int tripStatusX;
    private final int tripStatusY;
    private int ejectDriver;
    private int ejectPassenger;
    private int enemyRate;
    private int otherCarRate;
    private String rain;

    public GameScreen(Properties gameProps, Properties messageProps, String playerName) {
        this.speed = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        road = new Road(gameProps, speed);
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        this.currentFrame = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        this.MaxFrame = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        this.target = Double.parseDouble(gameProps.getProperty("gamePlay.target"));
        font = new Font(gameProps.getProperty("font"), Integer.parseInt(gameProps.getProperty("gamePlay.info.fontSize")));
        passengerFont = new Font(gameProps.getProperty("font"), Integer.parseInt(gameProps.getProperty("gameObjects.passenger.fontSize")));
        // number that wasn't provided in game property
        this.PLAYER_NAME = playerName;
        this.ejectDriver = 50;
        this.ejectPassenger = 100;
        this.enemyRate = 400;
        this.otherCarRate = 200;
        this.rain = "RAIN";
        /* coin frame */

        powerX = Integer.parseInt(gameProps.getProperty("gameplay.coin.x"));
        powerY = Integer.parseInt(gameProps.getProperty("gameplay.coin.y"));

        gameplayValues = new double[6];
        gameplayValues[0] = Double.parseDouble(gameProps.getProperty("gamePlay.earnings.x"));
        gameplayValues[1] = Double.parseDouble(gameProps.getProperty("gamePlay.earnings.y"));
        gameplayValues[2] = Double.parseDouble(gameProps.getProperty("gamePlay.target.x"));
        gameplayValues[3] = Double.parseDouble(gameProps.getProperty("gamePlay.target.y"));
        gameplayValues[4] = Double.parseDouble(gameProps.getProperty("gamePlay.maxFrames.x"));
        gameplayValues[5] = Double.parseDouble(gameProps.getProperty("gamePlay.maxFrames.y"));
        penaltyRate = Double.parseDouble(gameProps.getProperty("trip.penalty.perY"));

        //priority array;
        priority = new double[3];
        priority[0] = Double.parseDouble(gameProps.getProperty("trip.rate.priority1"));
        priority[1] = Double.parseDouble(gameProps.getProperty("trip.rate.priority2"));
        priority[2] = Double.parseDouble(gameProps.getProperty("trip.rate.priority3"));

        ratePerY = Double.parseDouble(gameProps.getProperty("trip.rate.perY"));

        passengerSpeedX = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.walkSpeedX"));
        passengerSpeedY = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.walkSpeedY"));

        passengerValues = new double[6];
        passengerValues[0] = Double.parseDouble(gameProps.getProperty("gamePlay.tripInfo.x"));
        passengerValues[1] = Double.parseDouble(gameProps.getProperty("gamePlay.tripInfo.y"));

        tripStatusX = Integer.parseInt(gameProps.getProperty("gamePlay.tripInfo.x"));
        tripStatusY = Integer.parseInt(gameProps.getProperty("gamePlay.tripInfo.y"));

        gameplayStrings = new String[3];
        gameplayStrings[0] = messageProps.getProperty("gamePlay.earnings");
        gameplayStrings[1] = messageProps.getProperty("gamePlay.remFrames");
        gameplayStrings[2] = messageProps.getProperty("gamePlay.target");

        passengerStrings = new String [5];
        passengerStrings[0]  = messageProps.getProperty("gamePlay.onGoingTrip.title");
        passengerStrings[1]  = messageProps.getProperty("gamePlay.completedTrip.title");
        passengerStrings[2]  = messageProps.getProperty("gamePlay.trip.expectedEarning");
        passengerStrings[3]  = messageProps.getProperty("gamePlay.trip.priority");
        passengerStrings[4]  = messageProps.getProperty("gamePlay.trip.penalty");

        GAME_OBJECT = IOUtils.readCommaSeparatedFile("res/gameObjects.csv");
        gameLoops();
        collisionHandler = new Collision(taxi, carList,gameProps,messageProps);

    }

    /**
     * Making function to loop the game object array
     */

    List<Coin> coinList = new ArrayList<>();
    List<Passenger> passengerList = new ArrayList<>();
    List<TripEndFlag> tripEndFlagList = new ArrayList<>();
    List<InviciblePower> inviciblePowerList = new ArrayList<>();

    /**
     * loop through the array to make an array of passenger, coin and invincible
     */
    public void gameLoops() {
        for (String[] row : GAME_OBJECT) {
            switch (row[0]) {
                case "TAXI":
                    taxi = new Taxi(GAME_PROPS, MESSAGE_PROPS);
                    taxi.setX(Double.parseDouble(row[1]));
                    taxi.setY(Double.parseDouble(row[2]));
                    taxi.setHealthValueFont(font);
                    entitiesList.add(taxi);
                    break;
                case "DRIVER":
                    driver = new Driver(GAME_PROPS);
                    driver.setX( Double.parseDouble(row[1]));
                    driver.setY(Double.parseDouble(row[2]));
                    entitiesList.add(driver);
                    break;

                case "PASSENGER":
                    passenger = new Passenger(GAME_PROPS.getProperty("gameObjects.passenger.image"),
                            Double.parseDouble(row[1]), Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.passenger.radius")),
                            Integer.parseInt(row[3]), tripEndFlag,GAME_PROPS);
                    passenger.setStartY(Double.parseDouble(row[5]));
                    tripEndFlag = new TripEndFlag(GAME_PROPS.getProperty("gameObjects.tripEndFlag.image"),
                            Double.parseDouble(row[4]),
                            Double.parseDouble(row[2]) - Double.parseDouble(row[5]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.tripEndFlag.radius")));
                    passenger.setTripEndFlag(tripEndFlag);
                    passenger.setRadius(Double.parseDouble(
                            GAME_PROPS.getProperty("gameObjects.passenger.radius")));
                    passenger.setDETECT_RADIUS(Double.parseDouble(
                            GAME_PROPS.getProperty("gameObjects.passenger.taxiDetectRadius")));
                    passenger.setIN_CAR_RADIUS(Double.parseDouble(
                            GAME_PROPS.getProperty("gameObjects.passenger.taxiGetInRadius")));
                    passenger.setSpeed(speed);
                    passenger.setHasUmbrella(Integer.parseInt(row[6]) != 0);
                    tripEndFlag.setSpeed(speed);
                    passengerList.add(passenger);
                    tripEndFlagList.add(tripEndFlag);
                    entitiesList.add(passenger);
                    break;
                case "COIN":
                    coin = new Coin(GAME_PROPS.getProperty("gameObjects.coin.image"),
                            Double.parseDouble(row[1]), Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.coin.radius")));
                    coin.setSpeed(speed);
                    coin.setDuration(Integer.parseInt(GAME_PROPS.getProperty("gameObjects.coin.maxFrames")));
                    coinList.add(coin);
                    break;
                case "INVINCIBLE_POWER":
                    inviciblePower = new InviciblePower(GAME_PROPS.getProperty("gameObjects.invinciblePower.image"),
                            Double.parseDouble(row[1]), Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.invinciblePower.radius")));
                    inviciblePower.setSpeed(speed);
//
                    inviciblePower.setDuration(Integer.parseInt(
                            GAME_PROPS.getProperty("gameObjects.invinciblePower.maxFrames")));
                    inviciblePowerList.add(inviciblePower);

            }

        }
    }

    /**
     * running the game
     * @param input key
     */
    public boolean renderGameScreen(Input input) {
        if (currentFrame > 0) {
            currentFrame--;
            String weather = road.checkWeather(currentFrame);
            road.setWeather(weather);
            moveObject(input);
        }
        road.render();
        renderCoin();
        driverEjection();
        drivingCar(input);
        getInTaxi();
        taxi.renderHealth();

        if(taxi.getVisible()){
            taxi.render();
            taxi.renderSmoke();}
        driver.render();
        renderFrame();
        for (TripEndFlag tripEndFlag : tripEndFlagList) {
            taxi.renderPay(font,gameplayStrings,gameplayValues);
            taxi.dropOffPassenger(font,passengerStrings,tripStatusX,tripStatusY);
        }
        // handle invincible power
        for (InviciblePower inviciblePower : inviciblePowerList) {
            inviciblePower.colliedWithInvincible(taxi);
            inviciblePower.colliedWithInvincible(driver);
            inviciblePower.effecting(taxi);
            inviciblePower.effecting(driver);
            inviciblePower.render();
        }
        renderCar();
        // handle collision
        collisionHandler.checkCollisions();
        collisionHandler.checkCarCollisions();
        if(driver.getVisible()) {
            collisionHandler.killHuman(driver);
            renderBloodDriver(driver);
        }
        return isLevelCompleted() || isGameOver();
    }

    /**
     * render current Frame and target score
     */
    private void renderFrame(){
        font.drawString(gameplayStrings[1] + currentFrame,
                gameplayValues[4], gameplayValues[5]);
        font.drawString(gameplayStrings[2] + target, gameplayValues[2],gameplayValues[3]);
    }

    /**
     * render the coin when collect and update the priority when collect coin or in rain
     */

    public void renderCoin(){
        //render coin and handle activation
        for (Coin coin : coinList) {
            coin.colliedWithCoin(taxi);
            coin.colliedWithCoin(driver);
            coin.render();
            coin.updateCoinPower();
            if (coin.getPowerIsActive()) {
                newestCoin = coin;
            }
        }
        if (newestCoin != null && newestCoin.getPowerIsActive()) {
            newestCoin.renderCoinPowerFrame(font, powerX, powerY);
        }

        //Passenger render
        for (Passenger passenger : passengerList) {
            // update priority if needed
            if(newestCoin != null && newestCoin.getPowerIsActive() ){
                if(passenger.getPriority() > 1 && !passenger.priorityAdjust()){
                    passenger.setPriority(passenger.getPriority() - 1);
                    passenger.setPriorityAdjust(true);
                }
            }
            else if (road.getCurrentWeather().equals(rain) && !passenger.isHasUmbrella()) {
                passenger.setPriority(1);
            }else{
                passenger.setPriorityAdjust(false);
                passenger.resetPriority();
            }
            // pickup passenger
            taxi.pickUpPassenger(passenger);
            if (passenger.isPickedUp() && passenger.hasTripEndFlag()) {
                passenger.getTripEndFlag().render();
                taxi.checkPenalty(passenger,penaltyRate);
                taxi.updatePay(passenger);
                taxi.renderLastTrip(font,passengerStrings,tripStatusX,tripStatusY);
            }
            // passenger move to flage
            if(!taxi.getAccident()) {
                passenger.moveToFlag(passengerSpeedX, passengerSpeedY);
            }
            // handle accident
            collisionHandler.killHuman(passenger);
            renderBloodPassenger(passenger);
            passenger.render(passengerFont,ratePerY,priority);
        }
        taxi.renderTrip(font,passengerStrings,tripStatusX,tripStatusY);
    }

    /**
     * Driving the taxi using the left or right arrow key
     * if taxi is dead then we control driver
     */
    private void drivingCar(Input input) {
        if(taxi.getHealth() > 0 && taxi.isHasDriver()) {
            if (input.wasPressed(Keys.LEFT) || input.isDown(Keys.LEFT)) {
                taxi.moveLeft();

            } else if (input.wasPressed(Keys.RIGHT) || input.isDown(Keys.RIGHT)) {
                taxi.moveRight();
            }
        }else {
            taxi.setHasDriver(false);
            taxi.setAccident(true);
            driver.setVisible(true);
            driver.move(input);
            if (taxi.hasPassenger()) {
                Passenger passenger = taxi.getPassenger();
                passenger.setVisible(true);
                if(!taxi.isHasDriver() && passenger.isInCollision()){
                    passenger.setX(taxi.getX() - ejectPassenger);
                    passenger.render();
                    passenger.setPickedUp(false);
                    passenger.setInCollision(false);
                }
                passenger.followDriver(driver, passengerSpeedX, passengerSpeedY);
                passenger.render();
            }

        }
    }

    /**
     * eject driver and set collision to true
     */
    private void driverEjection(){
        if(!taxi.isHasDriver() && driver.isInCollision()){
            if(taxi.hasPassenger()) {
                taxi.getPassenger().setInCollision(true);
            }
            driver.setInCollision(true);
            driver.setX(taxi.getX() - ejectDriver);
        }
    }

    /**
     * when the driver is in get in radius to taxi, driver get in taxi along with the passenger
     * if have passenger
     */

    private void getInTaxi(){
        if(Utilities.checkCollision(driver,taxi)){
            driver.setInCollision(false);
            driver.setVisible(false);
            taxi.setAccident(false);
            if(taxi.hasPassenger()) {
                taxi.getPassenger().setInCollision(false);
                taxi.getPassenger().setVisible(false);
            }
            taxi.setHasDriver(true);
        }

    }

    /**
     * move the object when we are driving the car upward
     * @param input key
     */

    private void moveObject(Input input) {
        if (input.wasPressed(Keys.UP) || input.isDown(Keys.UP)) {
            road.moveBG();
            collisionHandler.moveDamageTaxiList();
            for (Coin coin : coinList) {
                coin.moveDown();
            }
            for (Passenger passenger : passengerList) {
                passenger.moveDown();
            }
            for (TripEndFlag tripEndFlag : tripEndFlagList) {
                tripEndFlag.moveDown();
            }
            for (InviciblePower inviciblePower : inviciblePowerList) {
                inviciblePower.moveDown();
            }
            if(!taxi.isHasDriver()){
                taxi.moveDown();
            }

        }
    }

    List<OtherCar> otherCarList = new ArrayList<>();
    List<EnemyCar> enemyCarList = new ArrayList<>();
    /**
     * render the random spawn car such as enemy cars, handle dead and fireballs
     */
    public void renderCar() {
        if (MiscUtils.canSpawn(enemyRate)) {
            enemyCar = new EnemyCar(GAME_PROPS);
            enemyCarList.add(enemyCar);
            carList.add(enemyCar);
            entitiesList.add(enemyCar);
        }
        if (!enemyCarList.isEmpty()) {
            for (EnemyCar enemyCar : enemyCarList) {
                if(enemyCar.getVisible() ) {
                    enemyCar.moveUp();
                    enemyCar.render();
                    enemyCar.renderSmoke();
                    renderFlame(enemyCar);
                    enemyCar.shootFire();
                    enemyCar.renderFireball(entitiesList);
                }
            }
        }
        renderOtherCar();
        collisionHandler.renderDamageTaxiList();
    }

    /**
     * spawn and render other cars
     */
    public void renderOtherCar(){
        if (MiscUtils.canSpawn(otherCarRate)) {
            otherCar = new OtherCar(GAME_PROPS);
            otherCarList.add(otherCar);
            carList.add(otherCar);
            entitiesList.add(otherCar);
        }
        if (!otherCarList.isEmpty()) {
            for (OtherCar otherCar : otherCarList) {
                if (otherCar.getVisible()) {
                    otherCar.moveUp();
                    otherCar.render();
                    otherCar.renderSmoke();
                    renderFlame(otherCar);
                }
            }
        }
    }

    /**
     * render fire when car is dead
     * @param car car
     */
    public void renderFlame(Car car){
        if(car.getHealth() <= 0) {
            car.fireTimer();
            car.renderFire();
        }
    }

    /**
     * render blood when driver is killed
     * @param human driver
     */
    public void renderBloodDriver(Driver human){
        if(human.getHealth() <= 0) {
            human.bloodTimer();
            human.renderBlood();
        }
    }
    /**
     * render blood when passenger is killed
     * @param human driver
     */
    public void renderBloodPassenger(Passenger human){
        if(human.getHealth() <= 0) {
            human.bloodTimer();
            human.renderBlood();

        }
    }

    /**
     * Check if the game is over. If the game is over and not saved the score, save the score.
     * @return true if the game is over, false otherwise.
     */
    boolean isGameOver;
    public boolean isGameOver() {
        // Check if game is over based on frame or driver's health
        isGameOver = currentFrame <= 0 || driver.getHealth() <= 0;

        // Check passenger health status
        for (Passenger passenger1 : passengerList) {
            if (passenger1.getHealth() <= 0) {
                isGameOver = true; // If any passenger is down, game is over
                break; // No need to check further passengers
            }
        }

        // Save score if game is over and not saved yet
        if (isGameOver && !savedData) {
            savedData = true;
            saveScore();
        }

        return isGameOver;
    }

    /**
     * save the player name and their score in the wanted format
     */

    private void saveScore() {
        String scoreLine = PLAYER_NAME + "," + taxi.getPay();
        IOUtils.writeScoreToFile(GAME_PROPS.getProperty("gameEnd.scoresFile"), scoreLine);
    }
    /**
     * Check if the level is completed. If the level is completed and not saved the score, save the score.
     * @return true if the level is completed, false otherwise.
     */
    public boolean isLevelCompleted() {
        // Level is completed if the total earnings is greater than or equal to the target earnings
        boolean isLevelCompleted = taxi.getPay() >= target;
        if(isLevelCompleted && !savedData) {
            savedData = true;
            IOUtils.writeScoreToFile(GAME_PROPS.getProperty("gameEnd.scoresFile"),
                    PLAYER_NAME + "," + taxi.getPay());
        }
        return isLevelCompleted;
    }


}


