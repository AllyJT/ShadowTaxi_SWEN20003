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
    private Passenger passenger;
    private TripEndFlag tripEndFlag;
    private Coin coin;
    private InviciblePower inviciblePower;
    private Font font;

    private int currentFrame;

    private Properties GAME_PROPS;

    private final double[] gameplayValues;
    private final String[][] GAME_OBJECT;

    private Car car;

    public GameScreen(Properties gameProps, Properties messageProps) {
        this.speed = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        road = new Road(gameProps, speed);
        this.GAME_PROPS = gameProps;
        this.currentFrame = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        font = new Font(gameProps.getProperty("font"), Integer.parseInt(gameProps.getProperty("gamePlay.info.fontSize")));

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
                    car = new Car(GAME_PROPS);
                    car.setX(Double.parseDouble(row[1]));
                    car.setY(Double.parseDouble(row[2]));
                    car.setSpeed(Integer.parseInt(GAME_PROPS.getProperty("gameObjects.taxi.speedX")));
                    car.setHealth(Double.parseDouble(GAME_PROPS.getProperty("gameObjects.taxi.health")));

                    break;

                case "PASSENGER":
                    passenger = new Passenger(GAME_PROPS.getProperty("gameObjects.passenger.image"),
                            Double.parseDouble(row[1]),Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.passenger.taxiDetectRadius")),
                            Integer.parseInt(row[3]), tripEndFlag);
                    tripEndFlag = new TripEndFlag(GAME_PROPS.getProperty("gameObjects.tripEndFlag.image"),
                            Double.parseDouble(row[4]),
                            Double.parseDouble(row[2]) - Double.parseDouble(row[5]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.tripEndFlag.radius")));
                    passenger.setTripEndFlag(tripEndFlag);
                    passenger.setIN_CAR_RADIUS(Double.parseDouble(
                            GAME_PROPS.getProperty("gameObjects.passenger.taxiGetInRadius")));
                    passenger.setSpeed(speed);
                    tripEndFlag.setSpeed(speed);
                    passengerList.add(passenger);
                    tripEndFlagList.add(tripEndFlag);

                    break;
                case "COIN":
                    coin = new Coin(GAME_PROPS.getProperty("gameObjects.coin.image"),
                            Double.parseDouble(row[1]),Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.coin.radius")));
                    coin.setSpeed(speed);
                    coinList.add(coin);
                    break;
                case "INVINCIBLE_POWER":
                    inviciblePower = new InviciblePower(GAME_PROPS.getProperty("gameObjects.invinciblePower.image"),
                            Double.parseDouble(row[1]), Double.parseDouble(row[2]),
                            Double.parseDouble(GAME_PROPS.getProperty("gameObjects.invinciblePower.radius")));
                    inviciblePower.setSpeed(speed);
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
        car.render();
        font.drawString(String.valueOf(currentFrame), gameplayValues[4], gameplayValues[5]);

        for(Coin coin : coinList){
            coin.render();
        }
        for(Passenger passenger : passengerList){
            passenger.render();
        }
        for(InviciblePower inviciblePower : inviciblePowerList) {
            inviciblePower.render();
        }

    }
    /**
     * Driving the taxi using the left or right arrow key
     */
    private void drivingCar(Input input){
        if(input.wasPressed(Keys.LEFT)|| input.isDown(Keys.LEFT)){
            car.moveLeft();
        } else if (input.wasPressed(Keys.RIGHT)||input.isDown(Keys.RIGHT)) {
            car.moveRight();
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


}

