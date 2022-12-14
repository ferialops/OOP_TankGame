package gameobjects;

import java.util.HashMap;
import java.util.LinkedHashMap;


public abstract class Player extends GameObject {

    protected boolean UpPressed = false;
    protected boolean DownPressed = false;
    protected boolean LeftPressed = false;
    protected boolean RightPressed = false;
    protected boolean ActionPressed = false;

    protected LinkedHashMap<String, Integer> statsCollection;

    protected final int MAX_HP = 20;
    protected final int MAX_LIVES = 5;

    protected int currentHP;
    protected int currentLives;

    protected boolean loser;

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleActionPressed() {
        this.ActionPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleActionPressed() {
        this.ActionPressed = false;
    }

    public float getHPRatio() {
        return Math.min(1, (float) this.currentHP / (float) this.MAX_HP);
    }

    public int getCurrentLives() {
        return this.currentLives;
    }

    public int getMaxLives() {
        return this.MAX_LIVES;
    }

    public boolean isLoser() {
        return this.loser;
    }

    public abstract Weapon.Type getWeapon();

    public abstract float getCooldownRatio();

    public abstract HashMap<String, Integer> getStats();

}
