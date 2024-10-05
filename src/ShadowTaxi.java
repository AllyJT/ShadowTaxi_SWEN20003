import bagel.*;
import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2024
 * Please enter your name below
 * @author Phuong Trang Tran
 */
public class ShadowTaxi extends AbstractGame {

    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    private HomeScreen homeScreen;
    private PlayerInfoScreen playerInfoScreen;
    private GameScreen gamePlayScreen;

    public ShadowTaxi(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));

        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        homeScreen = new HomeScreen(GAME_PROPS, MESSAGE_PROPS);
        playerInfoScreen = new PlayerInfoScreen(GAME_PROPS, MESSAGE_PROPS);
        gamePlayScreen = new GameScreen(GAME_PROPS,MESSAGE_PROPS);
    }
    /**
     * Making a flag to store which screen we are at
     */
    int flag = 0;
    int isPress(Input input) {
        if (input.wasPressed(Keys.ENTER)) {
            flag++;
        }
        if (input.wasPressed(Keys.SPACE)) {
            flag = 0;
        }
        return flag;
    }

        /**
         * Render the relevant screens and game objects based on the keyboard input
         * given by the user and the status of the game play.
         * @param input The current mouse/keyboard input.
         */
    @Override
    protected void update(Input input) {
        int currentFlag = isPress(input);
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if(currentFlag == 0 ){
            homeScreen.render();
        }
        else if (currentFlag == 1) {
            playerInfoScreen.render(input);
        }
        else if ( currentFlag == 2){
            gamePlayScreen.renderGameScreen(input);
        }
    }

    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowTaxi game = new ShadowTaxi(game_props, message_props);
        game.run();
    }
}
