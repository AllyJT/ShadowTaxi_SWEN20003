import bagel.*;
import bagel.util.Colour;

import java.util.Properties;
public class PlayerInfoScreen {
    private final Image BG;
    private final Font INFO_FONT;
    private final String NAME_ENTER;
    private final String NAME_START;
    private final double INFO_WIDTH;
    private final double START_WIDTH;
    private final Properties GAME_PROPS;
    private String userName = "";


    public PlayerInfoScreen(Properties gameProps, Properties messageProps ){
        this.GAME_PROPS = gameProps;
        BG = new Image(gameProps.getProperty("backgroundImage.playerInfo"));
        INFO_FONT = new Font(gameProps.getProperty("font"),
                Integer.parseInt(gameProps.getProperty("playerInfo.fontSize")));
        NAME_ENTER = messageProps.getProperty("playerInfo.playerName");
        NAME_START = messageProps.getProperty("playerInfo.start");
        INFO_WIDTH = INFO_FONT.getWidth(NAME_ENTER);
        START_WIDTH = INFO_FONT.getWidth(NAME_START);

    }

    /**
     * name makere
     * @param input key
     */
    private void nameMaker(Input input) {

        if (input.wasPressed(Keys.DELETE) || input.wasPressed(Keys.BACKSPACE)) {
            // Check if username exist
            if (!userName.isEmpty()) {
                userName = userName.substring(0,(userName.length() - 1));
            }
        } else {
            // Get the key, check if the character is Alphabetical
            String nameChar = MiscUtils.getKeyPress(input);
            if (nameChar != null && !nameChar.isEmpty()) {
                userName += nameChar;
            }
        }

    }

    /**
     * render player info screen
     * @param input key
     */
    public void render(Input input){

        BG.draw(Window.getWidth()/2.0,Window.getHeight()/2.0);
        INFO_FONT.drawString(NAME_ENTER,(Window.getWidth()-INFO_WIDTH)/2.0,
                Double.parseDouble(GAME_PROPS.getProperty("playerInfo.playerName.y")));
        INFO_FONT.drawString(NAME_START,(Window.getWidth()-START_WIDTH)/2.0,
                Double.parseDouble(GAME_PROPS.getProperty("playerInfo.start.y")));
        DrawOptions usernameDrawOption = new DrawOptions();
        usernameDrawOption.setBlendColour(Colour.BLACK);
        INFO_FONT.drawString(userName,(Window.getWidth()- INFO_FONT.getWidth(userName))/2.0,
                Double.parseDouble(GAME_PROPS.getProperty("home.title.y")), usernameDrawOption);
        nameMaker(input);
    }

    public String getUserName() {
        return userName;
    }

}
