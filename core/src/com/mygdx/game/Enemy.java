package com.mygdx.game;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    private float x;
    private int damage;

    private float y;
    private float speed;
    private Rectangle boundingBox;

    public Enemy(float x, float y, float speed, int damage) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;

        this.setWidth(GameConfig.ENEMY_WIDTH * GameConfig.TEXTURE_SCALE); // Set the width of the enemy texture
        this.setHeight(GameConfig.ENEMY_HEIGHT * GameConfig.TEXTURE_SCALE); // Set the height of the enemy texture
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }



    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getDamage() {
        return damage;
    }

    public void update() {
        // Implement the logic to update the enemy's position based on its behavior
        // For example, you can move it towards the player, perform specific actions, etc.
    }
}
