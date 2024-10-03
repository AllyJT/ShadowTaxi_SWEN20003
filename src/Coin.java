import bagel.Font;

public class Coin extends Entity{
    private boolean isCollied = false;
    private int duration;
    private int coinPower = 0;
    private boolean powerIsActive;

    public Coin(String string, double x, double y, double radius) {
        super(string, x, y, radius);
    }

    public void colliedWithCoin(CoinActivate obj){
        if(!isCollied) {
            //this.setVisible(false);
            if (Utilities.getEuclideanDistance(obj.getX(), obj.getY(), this.getX(), this.getY()) <
                    Utilities.getRadiusDisatance(obj.getRadius(), this.getRadius())) {
                    this.setVisible(false);
                    this.powerIsActive = true;
                    this.coinPower = 0;
                    setCollied();

            }
        }
    }
    public void setCollied(){
        this.isCollied = true;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCoinPower() {
        return coinPower;
    }

    public void setCoinPower(int coinPower) {
        this.coinPower = coinPower;
    }
    public boolean getPowerIsActive(){
        return powerIsActive;
    }
    public void setPowerIsActive(boolean powerIsActive) {
        this.powerIsActive = powerIsActive;
    }

    /* METHOD */

    public void updateCoinPower(){
        if(powerIsActive){
            coinPower++;
            if(coinPower > duration){
                powerIsActive = false;
                isCollied = false;
            }
        }
    }
    public void renderCoinPowerFrame(Font font, double x, double y){
        if(powerIsActive){
            font.drawString(String.valueOf(coinPower), x, y);
        }
    }
}
