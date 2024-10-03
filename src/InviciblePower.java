import bagel.Font;

public class InviciblePower extends Entity{
    private int DURATION;
    private boolean isCollied = false;
    private int duration;
    private int invincibleTime = 0;
    private boolean isInvincible;
    public InviciblePower(String string, double x, double y, double radius) {
        super(string, x, y, radius);
    }
    public void setDURATION(int duration){
        this.DURATION = duration;
    }
    public void colliedWithInvincible(Invincible obj){
        if(!isCollied) {
            //this.setVisible(false);
            if (Utilities.getEuclideanDistance(obj.getX(), obj.getY(), this.getX(), this.getY()) <
                    Utilities.getRadiusDisatance(obj.getRadius(), this.getRadius())) {
                this.setVisible(false);
                this.isInvincible = true;
                this.invincibleTime = 0;
                setCollied();

            }
        }
    }
    /* METHOD */
    public void setCollied(){
        this.isCollied = true;
    }


    public void effecting () {
        if (isInvincible) {
            invincibleTime++;
            if (invincibleTime > duration) {
                isInvincible = false;
                isCollied = false;
            }
        }
    }
    public void renderInvincible (Font font,double x, double y){
        if (isInvincible) {
            font.drawString(String.valueOf(invincibleTime), x, y);
        }
    }
}



