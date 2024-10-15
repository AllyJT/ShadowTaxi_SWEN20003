import bagel.Font;
import bagel.Image;

import java.util.Properties;

public class Taxi extends Car implements CoinActivate, Invincible{
    private final Image TAXI;
    private Passenger passenger = null;
    private boolean hasDriver;
    private boolean isStop;
    private boolean isDropped;
    private boolean isInvincible;
    private double moveDownSpeed;
    //private final double RADIUS;
    private boolean stop;
    private boolean accident;
    private Font healthValueFont;

   /* text */
    private String TAXI_TEXT;

   /* X and Y */
    private int healthValueX;
    private int healthValueY;


    /**
     * constructor of the taxi
     * @param gameProps game property file
     * @param messProps message property file
     */
    public Taxi(Properties gameProps, Properties messProps) {
        super(gameProps);
        this.TAXI = new Image(gameProps.getProperty("gameObjects.taxi.image"));
        /* TAXI HEALTH */
        this.setHealth(Double.parseDouble(gameProps.getProperty("gameObjects.taxi.health"))*100);
        this.TAXI_TEXT = messProps.getProperty("gamePlay.taxiHealth");
        this.setDamage(Double.parseDouble(gameProps.getProperty("gameObjects.taxi.damage")) * 100);
        this.setSpeed(Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedX")));
        this.setRadius(Double.parseDouble(gameProps.getProperty("gameObjects.taxi.radius")));
        moveDownSpeed = Double.parseDouble(gameProps.getProperty("gameObjects.taxi.speedY")) ;
        this.setHealthValueX(Integer.parseInt(gameProps.getProperty("gamePlay.taxiHealth.x")));
        this.setHealthValueY(Integer.parseInt(gameProps.getProperty("gamePlay.taxiHealth.y")));

        /* TAXI DAMAGE */

        this.stop = true;
        setInvincible(false);
        setVisible(true);
        setHasDriver(true);
    }


    /*SETTERS AND GETTER */

    public void setAccident(boolean accident) {
        this.accident = accident;
    }
    public boolean getAccident(){
        return accident;
    }

    public int getHealthValueX() {
        return healthValueX;
    }

    public int getHealthValueY() {
        return healthValueY;
    }

    public void setHealthValueX(int healthValueX) {
        this.healthValueX = healthValueX;
    }

    public void setHealthValueY(int healthValueY) {
        this.healthValueY = healthValueY;
    }

    public void setHealthValueFont(Font healthValueFont) {
        this.healthValueFont = healthValueFont;
    }

    public Font getHealthValueFont() {
        return healthValueFont;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public boolean isStopped(){ return stop;}

    //check if the taxi have passenger in it
    public boolean hasPassenger(){
        return passenger != null ;
    }
    public boolean isHasDriver() {
        return hasDriver;
    }
    public void setHasDriver(boolean hasDriver) {
        this.hasDriver = hasDriver;
    }

    public boolean isDropped() {
        return isDropped;
    }

    @Override
    public void moveDown() {
        setY(getY() + moveDownSpeed);
    }

    @Override
    public void setInvincible(boolean b) {
        this.isInvincible = b;
    }
    @Override
    public boolean isInvincible() {
        return isInvincible;
    }
    /* render */

    public void render(){
        if(getVisible() ){
            TAXI.draw(this.getX(),this.getY());
        }
    }


    public void renderHealth(){
        healthValueFont.drawString(TAXI_TEXT + this.getHealth(),
                this.getHealthValueX(),this.getHealthValueY());
    }

    /* PICK AND DROP OFF PASSENGER */
    /**
     * Pick up the passenger when the condition are met
     * @param passenger check if the passenger condition is meet
     */
    public void pickUpPassenger(Passenger passenger){
        if( isStopped() && !hasPassenger() &&
                Utilities.getEuclideanDistance(this.getX(),this.getY(),passenger.getX(),
                        passenger.getY()) <= passenger.getDETECT_RADIUS()){
            if (!passenger.isPickedUp()) {
                if(Utilities.getEuclideanDistance(this.getX(),this.getY(),passenger.getX(),
                        passenger.getY()) <= passenger.getIN_CAR_RADIUS()){
                    passenger.setPickedUp(true);
                    passenger.render();
                    this.passenger = passenger;
//                    isFinish = false;
//                    priority = this.passenger.getPriority();
//                    expectedPay = this.passenger.getExpectedValue();
                }
                else {
                    //move the passenger if they not in car
                    passenger.setX(passenger.getX() +
                            Utilities.clamp(this.getX()- passenger.getX(),-1,1));
                    passenger.setY(passenger.getY() +
                            Utilities.clamp(this.getY() - passenger.getY() ,-1,1));

                }
            }

        }

    }

    /**
     * Drop off the passenger when the condition are met
     */
    public void dropOffPassenger(){
        if( isStopped() && hasPassenger() && !getAccident()) {
            this.getPassenger().setX(this.getX());
            this.getPassenger().setY(this.getY());
            if ((Utilities.getEuclideanDistance(this.getX(), this.getY(), passenger.getTripEndFlag().getX(),
                    passenger.getTripEndFlag().getY()) <= passenger.getTripEndFlag().getRadius() )||
                    (passenger.getY() <= passenger.getTripEndFlag().getY())
            ){  this.passenger.setDroppedOff(true);
                isDropped = true;
//                isFinish = true;
//                renderLastTrip(FONT_GAME,passengerStrings,tripStatusX,tripStatusY);
                this.passenger = null;

            }
        }
    }

    public void ejectPassenger(Driver driver,int speedX, int speedY){
        if(this.hasPassenger()){
            this.passenger.setVisible(true);
            this.passenger.setX(this.getX() - 100);
            this.passenger.setY(this.getY());
            this.passenger.followDriver(driver,speedX, speedY);
        }
    }

}


