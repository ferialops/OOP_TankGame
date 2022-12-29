package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Random;


public class Tank extends Player {

    private final float ROTATION_SPEED = 2.5f;

    private BufferedImage originalSprite;
    private BufferedImage bulletSprite;
    private BufferedImage weaponSprite;
    private Weapon projectile;
    private Weapon.Type currentWeapon;

    private int moveSpeed;
    private int fireRate;
    private int damage;
    private int armor;
    private int ammo;

    private float fireCooldown;
    private float fireDelay;

    public Tank(float xPosition, float yPosition, float rotation, BufferedImage sprite, BufferedImage weaponSprite) {
        // Set properties
        this.construct(xPosition, yPosition, rotation, sprite);
        this.originalSprite = this.sprite;
        this.weaponSprite = weaponSprite;
        this.bulletSprite = weaponSprite;

        this.init();
    }

    private void init() {
        this.currentWeapon = Weapon.Type.Bullet;
        this.currentHP = this.MAX_HP;
        this.currentLives = this.MAX_LIVES;

        this.statsCollection = new LinkedHashMap<>();
        this.moveSpeed = 4;
        this.fireRate = 1;
        this.damage = 1;
        this.armor = 0;
        this.ammo = 50;

        this.fireDelay = 60f;
        this.fireCooldown = this.fireDelay;
        this.loser = false;
    }

    private void respawn() {
        this.currentHP = this.MAX_HP;
        this.currentWeapon = Weapon.Type.Bullet;
        this.sprite = this.originalSprite;
        this.weaponSprite = this.bulletSprite;

        this.moveSpeed = Math.max(4, this.moveSpeed - 2);
        this.fireRate = Math.max(1, this.fireRate - 2);
        this.damage = Math.max(1, this.damage - 2);
        this.armor = Math.max(0, this.armor - 2);
        this.ammo = 50;

        // Respawn at new random spawn location
        Random random = new Random();
        this.transform.setPosition(GameObjectCollection.spawnPoints.get(random.nextInt(GameObjectCollection.spawnPoints.size())).getTransform().getPosition());
    }


    // --- MOVEMENT ---
    private void rotateRight() {
        this.transform.rotate(this.ROTATION_SPEED);
    }
    private void rotateLeft() {
        this.transform.rotate(-this.ROTATION_SPEED);
    }
    private void moveForwards() {
        this.transform.move(this.moveSpeed);
    }
    private void moveBackwards() {
        this.transform.move(-this.moveSpeed);
    }
    // --- MOVEMENT ---


    private void fire() {
        if (this.fireCooldown >= this.fireDelay) {
            this.projectile = this.currentWeapon.createInstance(this.weaponSprite, this.damage, this);
            this.instantiate(this.projectile, this.transform.getPosition().add(this.originOffset), this.transform.getRotation());
            this.ammo--;
            this.fireCooldown = 0;
        }
    }


    public void takeDamage(int damageDealt) {
        this.currentHP -= Math.max(1, damageDealt - this.armor);
        if (this.currentHP <= 0) {
            this.currentLives--;
            if (this.currentLives <= 0) {
                this.loser = true;
                this.destroy();
            } else {
                this.respawn();
            }
        }
    }

    @Override
    public Weapon.Type getWeapon() {
        return this.currentWeapon;
    }

    @Override
    public float getCooldownRatio() {
        return this.fireCooldown / this.fireDelay;
    }

    @Override
    public LinkedHashMap<String, Integer> getStats() {
        this.statsCollection.put("Health", this.currentHP);
        this.statsCollection.put("Lives", this.currentLives);
        this.statsCollection.put("Speed", this.moveSpeed);
        this.statsCollection.put("Fire Rate", this.fireRate);
        this.statsCollection.put("Damage", this.damage);
        this.statsCollection.put("Armor", this.armor);
        this.statsCollection.put("Ammo", this.ammo);

        return this.statsCollection;
    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        // Cooldown
        if (this.fireCooldown < this.fireDelay) {
            this.fireCooldown += this.fireRate;
        }

        // Movement
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        // Rotation
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        // Weapon
        if (this.ActionPressed && this.ammo > 0) {
            this.fire();
        }
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        collidingTank.solidCollision(this);
    }

    @Override
    public void handleCollision(Wall collidingWall) {
        this.solidCollision(collidingWall);
    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {
    }

    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor((this.getHPRatio() > 0.5) ? Color.GREEN : (this.getHPRatio() > 0.25) ? Color.YELLOW : Color.RED);

        // Draw aim line
        float fromX = this.transform.getPositionX() + this.originOffset.getX();
        float fromY = this.transform.getPositionY() + this.originOffset.getY();
        float toX = (float) (this.getHPRatio() * 500 * Math.cos(Math.toRadians(this.transform.getRotation())));
        float toY = (float) (this.getHPRatio() * 500 * Math.sin(Math.toRadians(this.transform.getRotation())));
        g2d.drawLine((int) fromX, (int) fromY, (int) (fromX + toX), (int) (fromY + toY));
    }

    @Override
    public void drawVariables(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString("currentHP: " + this.currentHP, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
        g2d.drawString("moveSpeed: " + this.moveSpeed, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
        g2d.drawString("fireRate: " + this.fireRate, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 84);
        g2d.drawString("damage: " + this.damage, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 96);
        g2d.drawString("armor: " + this.armor, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 108);
        g2d.drawString("ammo: " + this.ammo, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 120);
    }

}
