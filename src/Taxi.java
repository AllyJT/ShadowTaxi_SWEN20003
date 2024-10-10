import bagel.Font;
import bagel.Image;

import java.util.Properties;

public class Taxi extends Car implements CoinActivate, Invincible, Movable, Damageable{
    private final Image TAXI;
    private final Image DAMAGE_TAXI;
    private Passenger passenger = null;
    private Coin coin;
    private boolean isStop;
    private boolean isDropped;
    private boolean isInvincible;

    //private final double RADIUS;
    private boolean stop;
    private int speed;
    private double damage;
    private double health;
    private Font healthValueFont;

   /* text */
    private String TAXI_TEXT;

   /* X and Y */
    private int healthValueX;
    private int healthValueY;

    public Taxi(Properties gameProps, Properties messProps) {
        super(gameProps);
        this.TAXI = new Image(gameProps.getProperty("gameObjects.taxi.image"));
        this.DAMAGE_TAXI = new Image(gameProps.getProperty("gameObjects.taxi.damagedImage"));
        /* TAXI HEALTH */
        this.setHealth(Double.parseDouble(gameProps.getProperty("gameObjects.taxi.health")));
        this.TAXI_TEXT = messProps.getProperty("gamePlay.taxiHealth");
        //this.setDamge(Double.parseDouble(gameProps.getProperty("gameObjects.taxi.damage")));

        /* TAXI DAMAGE */

        this.stop = true;
        setInvincible(false);
        setVisible(true);
    }


    /*SETTERS AND GETTER */
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


    /* render */

    public void render(){
        if(getVisible()){
            if(this.getHealth() >  0){
                TAXI.draw(this.getX(),this.getY());
            }
            else{
                DAMAGE_TAXI.draw(this.getX(),this.getY());
            }
        }
    }

    public void renderHealth(){
        healthValueFont.drawString(TAXI_TEXT + this.getHealth()*100,
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
        if( isStopped() && hasPassenger()) {
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
    //check if the taxi have passenger in it
    public boolean hasPassenger(){
        return passenger != null ;
    }


    public boolean isDropped() {
        return isDropped;
    }

    @Override
    public void setInvincible(boolean b) {
        this.isInvincible = b;
    }


    @Override
    public void setDamge(double damage) {
        this.damage = damage;
    }
}


