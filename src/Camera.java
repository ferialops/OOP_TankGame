import gameobjects.GameObject;

import java.awt.image.BufferedImage;

public class Camera {

    private static final int WIDTH = (GameWindow.SCREEN_WIDTH / 2) - 2;
    private static final int HEIGHT = (GameWindow.SCREEN_HEIGHT * 2 / 3) - 2;

    private GameObject trackObject;
    private BufferedImage view;

    public Camera(GameObject obj) {
        this.trackObject = obj;
    }

    public void redraw(BufferedImage world) {
        float x = this.trackObject.getTransform().getPositionX() + this.trackObject.getOriginOffset().getX() - ((float) WIDTH / 2);
        float y = this.trackObject.getTransform().getPositionY() + this.trackObject.getOriginOffset().getY() - ((float) HEIGHT / 2);

        if (x <= 0) {
            x = 0;
        } else if (x >= world.getWidth() - WIDTH) {
            x = world.getWidth() - WIDTH;
        }

        if (y <= 0) {
            y = 0;
        } else if (y >= world.getHeight() - HEIGHT) {
            y = world.getHeight() - HEIGHT;
        }

        this.view = world.getSubimage((int) x, (int) y, WIDTH, HEIGHT);
    }
    
    public BufferedImage getScreen() {
        return this.view;
    }

}
