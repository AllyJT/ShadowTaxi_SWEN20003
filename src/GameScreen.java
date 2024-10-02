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
    private int currentFrame;
    private Font font;
    private double x;
    private double y;

    public GameScreen(Properties gameProps, Properties messageProps ){
        this.speed = Integer.parseInt(gameProps.getProperty("gameObjects.taxi.speedY"));
        road = new Road(gameProps,speed);
        this.currentFrame = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        font = new Font(gameProps.getProperty("font"), Integer.parseInt(gameProps.getProperty("gamePlay.info.fontSize")));
        this.x = Double.parseDouble(gameProps.getProperty("gamePlay.maxFrames.x"));
        this.y = Double.parseDouble(gameProps.getProperty("gamePlay.maxFrames.y"));
    }

    public void renderGameScreen(Input input){
        if(currentFrame >=0) {
            currentFrame--;
            String weather = road.checkWeather(currentFrame);
            road.setWeather(weather);
            moveObject(input);
        }
        road.render();
        font.drawString(String.valueOf(currentFrame), x, y);

    }
    private void moveObject(Input input){
        if(input.wasPressed(Keys.UP)|| input.isDown(Keys.UP)) {
            road.moveBG();

        }
    }
}
