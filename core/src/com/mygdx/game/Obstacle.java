package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Obstacle extends Actor {
    private float x;
    private float y;
    private float width;
    private float height;

    public Obstacle(float x, float y) {
        this.x = x;
        this.y = y;
        this.setWidth(GameConfig.OBSTACLE_WIDTH);
        this.setHeight(GameConfig.OBSTACLE_HEIGHT);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

}
