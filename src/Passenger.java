import bagel.Font;
import bagel.Image;

public class Passenger extends Entity implements TakeDamage, Damageable{
    private int priority;
    private boolean pickedUp;
    private boolean droppedOff;
    private double expectedValue;
    private boolean isExpectedValue = false;
    private boolean hasUmbrella;
    private int health;
    private int collisionTimeOut;
    private double IN_CAR_RADIUS;
    private double radius;
    private double DETECT_RADIUS;
    private TripEndFlag tripEndFlag;
    //private final Image BLOOD;

    public Passenger(String string, double x, double y, double radius,
                     int priority,TripEndFlag tripEndFlag) {
        super(string, x, y, radius);
        this.tripEndFlag = tripEndFlag;
        this.priority = priority;
        this.pickedUp = false;
        this.droppedOff = false;

    }



   /* Getters and setters */
    public void setHasUmbrella(boolean hasUmbrella) {
        this.hasUmbrella = hasUmbrella;
    }

    public void setDETECT_RADIUS(double DETECT_RADIUS) {
        this.DETECT_RADIUS = DETECT_RADIUS;
    }

    public double getDETECT_RADIUS() {
        return DETECT_RADIUS;
    }

    public double getIN_CAR_RADIUS() {
        return IN_CAR_RADIUS;
    }


    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setTripEndFlag(TripEndFlag tripEndFlag) {
        this.tripEndFlag = tripEndFlag;
        this.setVisible(true);
    }

    public TripEndFlag getTripEndFlag() {
        return tripEndFlag;
    }

    public boolean hasTripEndFlag() {
        return tripEndFlag != null;
    }

    public void setIN_CAR_RADIUS(double IN_CAR_RADIUS) {
        this.IN_CAR_RADIUS = IN_CAR_RADIUS;
    }

    public void setDroppedOff(boolean droppedOff) {
        this.droppedOff = droppedOff;
        setVisible(true);
    }

    public boolean isDroppedOff() {
        return this.droppedOff;
    }
    /**
     * methods
     */


    /* Pickup and Dropped off */
    public boolean isPickedUp() {
        return pickedUp;
    }
    /**
     * Set the stage of passenger, along with the visibility of flag
     * Flag only visible when we have a passenger on it
     * @param pickedUp
     */
    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
        setVisible(!pickedUp);
        if (tripEndFlag != null) {
            tripEndFlag.setVisible(pickedUp);
        }
    }

    /**
     * Move the passenger to the flag when the trip ended
     * The passenger move at speed 1 pixel
     */

    public void moveToFlag(int speedX, int speedY) {
        if (hasTripEndFlag() && isDroppedOff()) {

            if (Utilities.getEuclideanDistance(tripEndFlag.getX(), tripEndFlag.getY(), this.getX(), this.getY()) > 1) {
                //move the passenger if they see flag
                this.setX(this.getX() + Utilities.clamp(tripEndFlag.getX() - this.getX(), -speedX, speedX));
                this.setY(this.getY() + Utilities.clamp(tripEndFlag.getY() - this.getY(), -speedY, speedY));
                if (Utilities.getEuclideanDistance(tripEndFlag.getX(), tripEndFlag.getY(), this.getX(), this.getY()) <= 1) {
                    tripEndFlag.setVisible(false);

                }

            }
        }

    }

    /**
     * Check the priority of the passenger and get the rate
     * @param passengerPriority passenger priority
     * @param priorityRate the rate for priority 1 to 3
     * @return return the priority rate
     */
    public double checkPriority(int passengerPriority, double[] priorityRate) {
        return switch (passengerPriority) {
            case 1 -> priorityRate[0];
            case 2 -> priorityRate[1];
            default -> priorityRate[2];
        };
    }

    /**
     * render Priority next to passenger
     * @param font font of text
     * @param rate rate of the priority
     * @param ratePerY rate per pixel distance of the passenger and
     */

    public void renderPriority (Font font, double rate, double ratePerY){
        //render priority
        font.drawString(String.valueOf(this.getPriority()), this.getX() - 30, this.getY());
        String formattedExpectedValue = String.format("%.1f", this.expectedValue(rate, ratePerY));
        font.drawString(formattedExpectedValue, this.getX()- 100, this.getY());
    }
    /**
     * flag the expected value, so it won't change when the passenger move
     * @param rate rate of priority
     * @param ratePerY rate per y-distance 0.1
     * @return return value of expected value
     */
    public double expectedValue(double rate, double ratePerY) {
        return this.expectedValue;
    }

    @Override
    public void takeDamage(Damageable Damage) {

    }

    @Override
    public boolean hasCollied() {
        return false;
    }

    @Override
    public int getDamage() {
        return 0;
    }
}
