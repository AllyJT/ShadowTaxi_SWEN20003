import bagel.Font;
import bagel.Image;

public class Passenger extends Entity{
    private int priority;
    private boolean pickedUp;
    private boolean droppedOff;
    private double expectedValue;
    private boolean isExpectedValue = false;
    private boolean hasUmbrella;
    private int health;
    private int collisionTimeOut;
    private TripEndFlag tripEndFlag;
    //private final Image BLOOD;

    public Passenger(String string, double x, double y, double radius,
                     int speed, int health, Image blood, int priority,TripEndFlag tripEndFlag) {
        super(string, x, y, radius, speed, health);
        this.tripEndFlag = tripEndFlag;
        this.priority = priority;
        this.pickedUp = false;
        this.droppedOff = false;

    }

    /**
     * extra getters and setters
     */

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

    public boolean hasTripEndFlag() {
        return tripEndFlag != null;
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

}
