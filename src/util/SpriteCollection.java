package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum SpriteCollection {
    tank1,
    tank2,
    tankGold,
    softWall,

    // Weapons
    bullet1,
    bullet2;

    private BufferedImage image = null;

    public BufferedImage getImage() {
        return this.image;
    }

    public static void init() {
        try {
            SpriteCollection.tank1.image = ImageIO.read(SpriteCollection.class.getResource("/resources/tank1.png"));
            SpriteCollection.tank2.image = ImageIO.read(SpriteCollection.class.getResource("/resources/tank2.png"));
            SpriteCollection.tankGold.image = ImageIO.read(SpriteCollection.class.getResource("/resources/tank3.png"));
            SpriteCollection.softWall.image = ImageIO.read(SpriteCollection.class.getResource("/resources/wallS.png"));


            SpriteCollection.bullet1.image = ImageIO.read(SpriteCollection.class.getResource("/resources/bullet1.png"));
            SpriteCollection.bullet2.image = ImageIO.read(SpriteCollection.class.getResource("/resources/bullet2.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

}
