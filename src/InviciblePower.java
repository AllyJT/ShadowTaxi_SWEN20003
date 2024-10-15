import bagel.Font;

public class InviciblePower extends Entity{
    private boolean isCollied = false;
    private int duration;
    private int invincibleTime = 0;
    private boolean isInvincible;
    public InviciblePower(String string, double x, double y, double radius) {
        super(string, x, y, radius);
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public void colliedWithInvincible(Invincible obj){
        if(!isCollied) {
            if (Utilities.getEuclideanDistance(obj.getX(), obj.getY(), this.getX(), this.getY()) <
                    Utilities.getRadiusDisatance(obj.getRadius(), this.getRadius())) {
                this.setVisible(false);
                this.isInvincible = true;
                this.invincibleTime = 0;
                obj.setInvincible(true);
                setCollied(true);

            }
        }
    }
    /* METHOD */
    public void setCollied(boolean collied){
        this.isCollied = collied;
    }


    public void effecting (Invincible obj) {
        if (isInvincible) {
            invincibleTime++;
            if (invincibleTime > duration) {
                obj.setInvincible(false);
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

    public boolean isInvincible() {
        return isInvincible;
    }
}



