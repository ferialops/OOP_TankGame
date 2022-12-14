import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class GameLauncher {

    public static void main(String[] args) {
        GamePanel game = new GamePanel();
        game.init();
        try {
            // $ java -jar csc413-tankgame-blai30.jar [args]
            game.loadMap(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e + ": Program args not given");
            game.loadMap(null);
        }
        GameWindow.gameWindow = new GameWindow(game);
        game.launch();
        System.gc();
    }

}


public class GameWindow extends JFrame {

    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = 960;

    static GameWindow gameWindow;

    GameWindow(JPanel game) {
        
        try {
            System.out.println(System.getProperty("user.dir"));
            Image icon = ImageIO.read(this.getClass().getResource("/resources/icon.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(game, BorderLayout.CENTER);
        this.setVisible(true);
    }

}
