package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Wall extends GameObject {

    private boolean isBreakable;
    private int hitPoints;

    public Wall(float xPosition, float yPosition, float rotation, BufferedImage sprite, boolean isBreakable) {
        this.construct(xPosition, yPosition, rotation, sprite);
        this.isBreakable = isBreakable;

        this.init();
    }

    private void init() {
        this.hitPoints = 1;
    }

    public void takeDamage(int damageDealt) {
        this.hitPoints -= damageDealt;

            this.destroy();
    }

    public boolean isBreakable() {
        return this.isBreakable;
    }

    @Override
    public void update() {
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {

    }

    @Override
    public void handleCollision(Wall collidingWall) {

    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {

    }


    @Override
    public void drawGizmos(Graphics g) {

    }


    @Override
    public void drawVariables(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("hitPoints: " + this.hitPoints, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
        g2d.drawString("isBreakable: " + this.isBreakable, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
    }

}
