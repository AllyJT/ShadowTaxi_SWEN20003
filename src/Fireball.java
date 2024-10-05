
import bagel.Image;

import java.util.Properties;

public class Fireball extends Entity {
    private int speed;


    public Fireball(String string, double x, double y, double radius) {
        super(string, x, y, radius);

    }

    @Override
    public void render() {
        Image image = this.getImage();
        if(image != null){
            image.draw(this.getX(),this.getY());
        }
    }
}
