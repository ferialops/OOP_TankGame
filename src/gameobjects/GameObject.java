package gameobjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public abstract class GameObject implements CollisionHandling {

    protected BufferedImage sprite;
    protected Transform transform;
    protected float width;
    protected float height;
    protected Vector2D originOffset;
    protected Rectangle2D.Double collider;

    private boolean destroyed = false;


    protected void construct(float xPosition, float yPosition, float rotation, BufferedImage sprite) {
        this.transform = new Transform(xPosition, yPosition, rotation);
        this.construct(sprite);
    }


    protected void construct(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
    }


    protected void instantiate(GameObject spawnObj, Vector2D location, float rotation) {
        float x = location.getX() - spawnObj.originOffset.getX();
        float y = location.getY() - spawnObj.originOffset.getY();
        Vector2D spawnPoint = new Vector2D(x, y);
        spawnObj.transform.setPosition(spawnPoint);
        spawnObj.transform.setRotation(rotation);
        spawnObj.collider.setRect(x, y, spawnObj.width, spawnObj.height);
        GameObjectCollection.spawn(spawnObj);
    }

    protected void destroy() {
        this.destroyed = true;
    }

    protected void solidCollision(GameObject obj) {
        Rectangle2D intersection = this.collider.createIntersection(obj.collider);
        // Vertical collision
        if (intersection.getWidth() >= intersection.getHeight()) {
            // From the top
            if (intersection.getMaxY() >= this.collider.getMaxY()) {
                this.transform.move(0, -(float) intersection.getHeight());
            }
            // From the bottom
            if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                this.transform.move(0, (float) intersection.getHeight());
            }
        }
        // Horizontal collision
        if (intersection.getHeight() >= intersection.getWidth()) {
            // From the left
            if (intersection.getMaxX() >= this.collider.getMaxX()) {
                this.transform.move(-(float) intersection.getWidth(), 0);
            }
            // From the right
            if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                this.transform.move((float) intersection.getWidth(), 0);
            }
        }
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public Vector2D getOriginOffset() {
        return this.originOffset;
    }

    public Rectangle2D.Double getCollider() {
        return this.collider;
    }


    public boolean isDestroyed() {
        return destroyed;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    public void drawCollider(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(this.collider);
    }


    public void drawTransform(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("[" + this.getClass().getSimpleName() + "]", this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 12);
        g2d.drawString("x: " + this.transform.getPositionX(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 24);
        g2d.drawString("y: " + this.transform.getPositionY(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 36);
        g2d.drawString("angle: " + this.transform.getRotation(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 48);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] " + "Position: " + this.transform.getPosition() + ", Angle: " + this.transform.getRotation();
    }


    public abstract void update();


    public abstract void drawGizmos(Graphics g);


    public abstract void drawVariables(Graphics g);

}

interface CollisionHandling {

    void collides(GameObject collidingObj);
    void handleCollision(Tank collidingTank);
    void handleCollision(Wall collidingWall);
    void handleCollision(Weapon collidingWeapon);

}
