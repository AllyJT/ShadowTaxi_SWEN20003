import bagel.Image;
import bagel.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Road extends Entity {
    private Image BG_SUNNY;
    private Image BG_RAINING;
    private String currentWeather;
    private int speed;
    private List<String> weather = new ArrayList<>();
    private final String[][] WEATHER;
    private double BG_Y1 = Window.getHeight()/2.0;
    private double BG_Y2= -Window.getHeight()/2.0;


    public Road(Properties gameProps,int speed) {
        super(gameProps);
        this.BG_SUNNY = new Image(gameProps.getProperty("backgroundImage.sunny"));
        this.BG_RAINING = new Image(gameProps.getProperty("backgroundImage.raining"));
        this.speed = speed;
        WEATHER = IOUtils.readCommaSeparatedFile("res/gameWeather.csv");


    }
    public void moveBG(){
        BG_Y1 += speed;
        BG_Y2 += speed;
        if (BG_Y1 >= 1152) {
            BG_Y1 = BG_Y2 - Window.getHeight();
        } else if (BG_Y2 >= 1152) {
            BG_Y2 = BG_Y1  - Window.getHeight();
        }
    }
    public void setWeather(String weather){
        this.currentWeather = weather;
    }
    public String checkWeather(int frame){
        for(String[] row : WEATHER){
            if(frame >= Integer.parseInt(row[1]) && frame<= Integer.parseInt(row[2])){
                return row[0];
            }
        }
        return "SUNNY";
    }

    public void render(){
        Image BG;
        if(currentWeather.equals("SUNNY")){
            BG = BG_SUNNY;
        }else{
            BG = BG_RAINING;
        }

        BG.draw(Window.getWidth() / 2.0,BG_Y1);
        BG.draw(Window.getWidth() / 2.0,BG_Y2);
    }

}
