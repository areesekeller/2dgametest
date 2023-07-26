package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.states.GameOverState;

import java.util.List;

public class Enemy extends Entity {

    // Add any enemy-specific fields here, for example:
    private float movementSpeed;


    public Enemy(float x, float y) {
        super(x, y, 32, 32, "enemy");
        // Initialize enemy-specific fields

        this.maxHealth = 100.0f;
        this.health = this.maxHealth;
        movementSpeed = 0; // Set a default movement speed, you can adjust as needed
        this.damageValue = 10.0f;
    }

    public float getDamageValue() {
        return damageValue;
    }

    @Override
    public void update(float deltaTime) {
        // Add enemy-specific logic here
        // For example, move the enemy based on its movement speed
        // You can also add behavior like AI, pathfinding, etc.

        // Move the enemy to the right in this simple example
        setPosition(position.x + movementSpeed * deltaTime, position.y);
    }
    public void takeDamage(float damage) {
        System.out.println("Ouch! Health: " + health);
        if (health <= 0) {
            dispose();
            // Player is defeated, you can add game over logic here
        } else {
            health -= damage;
        }
    }

    public float getHealthPercentage() {
        return health / maxHealth;
    }

    public void setPosition (float x, float y) {
        this.position = new Vector2(x, y);
    }
    @Override
    public void render(SpriteBatch batch) {
        // Draw the enemy's texture at its position
        super.render(batch);
    }


    public void collisionResponse(List<Entity> entities) {}
    // Add any other methods specific to the enemy behavior as needed
}
