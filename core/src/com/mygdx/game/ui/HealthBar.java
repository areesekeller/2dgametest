package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.GameAssetManager;

public class HealthBar {
    private float x;
    private float y;
    private float width;
    private float height;
    private float healthPercentage;
    private Color backgroundColor;
    private Color foregroundColor;
    private Entity entity;

    public HealthBar(Entity entity) {

        this.entity = entity;
        // Determine the position and size of the health bar based on the entity type
        if (entity instanceof Player) {
            // Health bar for the player
            this.x = 20;
            this.y = Gdx.graphics.getHeight() - 20;
            this.width = 100;
            this.height = 15;
            this.foregroundColor = Color.GREEN;
        } else if (entity instanceof Enemy) {
            // Health bar for enemies
            // Set the position just above the enemy's texture by 20 pixels
            this.x = entity.getPosition().x;
            this.y = entity.getPosition().y + entity.getHeight() + 20;
            this.width = entity.getWidth();
            this.height = 5; // Smaller height for enemy health bars
            this.foregroundColor = Color.RED;
        }

        this.healthPercentage = 1.0f; // Full health (1.0) at the beginning
        this.backgroundColor = Color.DARK_GRAY;

    }

    // Method to get the width of the health bar
    public float getWidth() {
        return width;
    }

    // Method to get the height of the health bar
    public float getHeight() {
        return height;
    }

    // Method to set the x-coordinate of the health bar
    public void setX(float x) {
        this.x = x;
    }

    // Method to set the y-coordinate of the health bar
    public void setY(float y) {
        this.y = y;
    }

    public void setHealthPercentage(float healthPercentage) {
        this.healthPercentage = Math.max(0.0f, Math.min(1.0f, healthPercentage));
    }

    public void render(ShapeRenderer shapeRenderer) {

        // Draw the background of the health bar

        if (entity instanceof Enemy) {
            this.x = entity.getPosition().x;
            this.y = Gdx.graphics.getHeight() - entity.getPosition().y - entity.getHeight() - 10;
        }

        System.out.println("health bar x: " + x + " y: " + y + " width: " + width + " height: " + height + " entity: " + entity.getClass().getSimpleName());
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(x, y, width, height);

        // Draw the foreground of the health bar based on the health percentage
        shapeRenderer.setColor(foregroundColor);
        shapeRenderer.rect(x, y, width * healthPercentage, height);

    }
}
