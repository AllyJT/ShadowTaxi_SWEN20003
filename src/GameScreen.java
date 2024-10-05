import bagel.Font;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

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
    private InviciblePower inviciblePower;

    private Taxi taxi;
    private Driver driver;
    private Font font;

    private int currentFrame;

    private Properties GAME_PROPS;
    private Properties MESSAGE_PROPS;

    private final double[] gameplayValues;
    private final String[][] GAME_OBJECT;

    private int powerX;
    private int powerY;

    public GameScreen(Properties gameProps, Properties messageProps) {
        this.speed = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        road = new Road(gameProps, speed);
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        this.currentFrame = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        font = new Font(gameProps.getProperty("font"), Integer.parseInt(gameProps.getProperty("gamePlay.info.fontSize")));
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

        GAME_OBJECT = IOUtils.readCommaSeparatedFile("res/gameObjects.csv");
        gameLoops();

    }

    /**
     * Making function to loop the game object array
     */
    List<Coin> coinList = new ArrayList<>();
    List<Passenger> passengerList = new ArrayList<>();
    List<TripEndFlag> tripEndFlagList = new ArrayList<>();
    List<InviciblePower> inviciblePowerList = new ArrayList<>();
    public void gameLoops() {
        for (String[] row : GAME_OBJECT) {
            switch (row[0]) {
                case "TAXI":
                    taxi = new Taxi(GAME_PROPS, MESSAGE_PROPS);
                    taxi.setX(Double.parseDouble(row[1]));
                    taxi.setY(Double.parseDouble(row[2]));
                    taxi.setSpeed(Integer.parseInt(GAME_PROPS.getProperty("gameObjects.taxi.speedX")));
                    taxi.setRadius(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.taxi.radius")));
                    taxi.setHealthValueFont(font);
                    taxi.setHealthValueX(Integer.parseInt(GAME_PROPS.getProperty("gamePlay.taxiHealth.x")));
                    taxi.setHealthValueY(Integer.parseInt(GAME_PROPS.getProperty("gamePlay.taxiHealth.y")));
                    break;
                case "DRIVER":
                    driver = new Driver(GAME_PROPS.getProperty("gameObjects.driver.image"),
                            taxi.getX(),taxi.getY(),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.driver.radius")));
                    break;

                case "PASSENGER":
                    passenger = new Passenger(GAME_PROPS.getProperty("gameObjects.passenger.image"),
                            Double.parseDouble(row[1]),Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.passenger.radius")),
                            Integer.parseInt(row[3]), tripEndFlag);
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

                    break;
                case "COIN":
                    coin = new Coin(GAME_PROPS.getProperty("gameObjects.coin.image"),
                            Double.parseDouble(row[1]),Double.parseDouble(row[2]),
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
                    inviciblePower.setRadius(Double.parseDouble(
                            GAME_PROPS.getProperty("gameObjects.invinciblePower.radius")));
                    inviciblePower.setDURATION(Integer.parseInt(
                            GAME_PROPS.getProperty("gameObjects.invinciblePower.maxFrames")));
                    inviciblePowerList.add(inviciblePower);

            }

        }
    }


    public void renderGameScreen(Input input) {
        if (currentFrame >= 0) {
            currentFrame--;
            String weather = road.checkWeather(currentFrame);
            road.setWeather(weather);
            moveObject(input);
        }
        road.render();
        drivingCar(input);
        taxi.render();
        font.drawString(String.valueOf(currentFrame), gameplayValues[4], gameplayValues[5]);

        for(Coin coin : coinList){
            //coin.setVisible(false);
            coin.colliedWithCoin(taxi);
            coin.render();
            coin.updateCoinPower();
            if(coin.getPowerIsActive()) {
                coin.renderCoinPowerFrame(font, powerX, powerY);
            }
        }

        for(Passenger passenger : passengerList){
            taxi.pickUpPassenger(passenger);
            if(passenger.isPickedUp() && passenger.hasTripEndFlag()){
                passenger.getTripEndFlag().render();
            }
            int passengerSpeedX = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.walkSpeedX"));
            int passengerSpeedY = Integer.parseInt(GAME_PROPS.getProperty("gameObjects.passenger.walkSpeedY"));
            passenger.moveToFlag(passengerSpeedX,passengerSpeedY);
            passenger.render();
        }
        for (TripEndFlag tripEndFlag : tripEndFlagList){
            //car.renderPay(FONT_GAME,gameplayStrings,gameplayValues);
            taxi.dropOffPassenger();
        }
        for(InviciblePower inviciblePower : inviciblePowerList) {
            inviciblePower.colliedWithInvincible(taxi);
            inviciblePower.colliedWithInvincible(driver);
            inviciblePower.render();
        }
        renderOtherCar();

    }
    /**
     * Driving the taxi using the left or right arrow key
     */
    private void drivingCar(Input input){
        if(input.wasPressed(Keys.LEFT)|| input.isDown(Keys.LEFT)){
            taxi.moveLeft();
        } else if (input.wasPressed(Keys.RIGHT)||input.isDown(Keys.RIGHT)) {
            taxi.moveRight();
        }
        //car.setStopped(!isMoving(input));
    }
    private void moveObject(Input input) {
        if (input.wasPressed(Keys.UP) || input.isDown(Keys.UP)) {
            road.moveBG();
            for(Coin coin : coinList){
                coin.moveDown();
            }
            for(Passenger passenger : passengerList){
                passenger.moveDown();
            }
            for(TripEndFlag tripEndFlag : tripEndFlagList){
                tripEndFlag.moveDown();
            }
            for(InviciblePower inviciblePower : inviciblePowerList) {
                inviciblePower.moveDown();
            }
        }
    }
    List<OtherCar> otherCarList = new ArrayList<>();
    List<EnemyCar> enemyCarList = new ArrayList<>();
    public void renderOtherCar(){
        if(MiscUtils.canSpawn(200)){
            otherCar = new OtherCar(GAME_PROPS);
            otherCar.setSpeed(MiscUtils.getRandomInt(2,5));
            otherCar.setDamage(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.otherCar.damage"))*100);
            otherCar.setHealth(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.otherCar.health"))*100);
            otherCarList.add(otherCar);;
        }
        if(MiscUtils.canSpawn(400)){
            enemyCar = new EnemyCar(GAME_PROPS);
            enemyCar.setSpeed(MiscUtils.getRandomInt(Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyCar.minSpeedY")),
                    Integer.parseInt(GAME_PROPS.getProperty("gameObjects.enemyCar.maxSpeedY"))));
            enemyCar.setDamage(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemyCar.health"))*100);
            enemyCar.setHealth(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.enemyCar.damage"))*100);
            enemyCarList.add(enemyCar);
        }
        if(!otherCarList.isEmpty()) {
            for (OtherCar otherCar : otherCarList) {
                otherCar.moveUp();
                otherCar.render();
            }
        }
        if(!enemyCarList.isEmpty()) {
            for (EnemyCar enemyCar : enemyCarList) {
                enemyCar.shootFire();
                enemyCar.moveUp();
                enemyCar.render();
            }
        }
    }


}

