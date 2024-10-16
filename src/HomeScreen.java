import bagel.*;
import java.util.Properties;
public class HomeScreen {
    private final String MESSAGE;
    private final Font MESSAGE_FONT;
    private final String ENTER_MESSAGE;
    private final Font ENTER_FONT;
    private final Image HOME;
    private final double MESSAGE_Y;
    private final double ENTER_Y;

    public HomeScreen(Properties gameProps, Properties messageProps) {
        MESSAGE = messageProps.getProperty("home.title");
        MESSAGE_FONT = new Font(gameProps.getProperty("font"),
                Integer.parseInt(gameProps.getProperty("home.title.fontSize")));
        HOME = new Image(gameProps.getProperty("backgroundImage.home"));
        ENTER_MESSAGE = messageProps.getProperty("home.instruction");
        ENTER_FONT = new Font(gameProps.getProperty("font"),
                Integer.parseInt(gameProps.getProperty("home.instruction.fontSize")));
        MESSAGE_Y = Double.parseDouble(gameProps.getProperty("home.title.y"));
        ENTER_Y = Double.parseDouble(gameProps.getProperty("home.instruction.y"));
    }

    /**
     * render home screen
     */

    public void render() {
        HOME.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        double stringWidth = MESSAGE_FONT.getWidth(MESSAGE);
        double enterWidth = ENTER_FONT.getWidth(ENTER_MESSAGE);
        MESSAGE_FONT.drawString(MESSAGE, (Window.getWidth() - stringWidth) / 2.0,
                MESSAGE_Y);
        ENTER_FONT.drawString(ENTER_MESSAGE, (Window.getWidth() - enterWidth) / 2.0,
                ENTER_Y);
    }
}