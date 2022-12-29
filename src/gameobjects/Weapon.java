package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Weapon extends GameObject {
    enum Type {
        Bullet {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Bullet(sprite, damage, shooter);
            }
        };


        public abstract Weapon createInstance(BufferedImage sprite, int damage, Tank shooter);

    }

    protected Tank shooter;

    protected int damage;
    protected float velocity;
    protected int hitPoints;

    protected abstract void init();


    public void takeDamage() {
        this.hitPoints--;
        if (this.hitPoints <= 0) {
            this.destroy();
        }
    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        this.transform.move(this.velocity);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        if (collidingTank != this.shooter) {
            collidingTank.takeDamage(this.damage);
            this.takeDamage();
        }
    }

    @Override
    public void handleCollision(Wall collidingWall) {
        if (collidingWall.isBreakable()) {
            collidingWall.takeDamage(this.damage);
        }
        this.takeDamage();
    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {
        if (collidingWeapon.shooter != this.shooter) {
            collidingWeapon.takeDamage();
        }
    }


    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public void drawVariables(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString("damage: " + this.damage, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
        g2d.drawString("velocity: " + this.velocity, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
        g2d.drawString("hitPoints: " + this.hitPoints, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 84);
    }

}
