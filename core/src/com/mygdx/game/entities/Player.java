package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.managers.GameAssetManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.states.GameOverState;

import java.util.List;

public class Player extends Entity {

    private static float SPEED = 200f;
    private Vector2 direction;

    private GameStateManager gameStateManager;
    private boolean isRolling;
    private float rollCooldown;
    private float rollDuration;
    private String tempName;

    float elapsedTime;
    private float rollTimer;
    private boolean isImmune;
    private float immunityTimer;
    public Player(float x, float y) {
        super(x, y, 32, 32, "up");
        direction = new Vector2(0, 0);
        isRolling = false;
        rollCooldown = 2.5f; // Set the cooldown time for the dodge roll (in seconds)
        rollDuration = 0.8f; // Set the duration of the roll (in seconds)
        rollTimer = 0.0f;
        this.maxHealth = 100.0f;
        this.health = this.maxHealth;
        // Initialize immunity fields
        isImmune = true;
        immunityTimer = 1.0f;
        elapsedTime = 0.0f;
        this.gameStateManager = GameStateManager.getInstance();
    }

    @Override
    public void update(float delta) {
        System.out.println("Updating Player");

        boundingBox.setPosition(position);
        if (isImmune) {
            immunityTimer -= delta;
            if (immunityTimer <= 0f) {
                isImmune = false; // Reset immunity status when the timer runs out
                elapsedTime= 0.0f;
            } else {
                    // Blinking effect - switch texture every half second (adjust the duration as needed)
                    float blinkDuration = 0.2f;
                    elapsedTime += delta;
                    System.out.println("delta: " + delta + " elapsed time: " + elapsedTime + " modulo : " + (elapsedTime % blinkDuration));
                    boolean showImmuneTexture = (elapsedTime % blinkDuration) < blinkDuration/2;
                    tempName = storedTextureName;
                    System.out.println("storedTexture: " + storedTextureName);
                    if (isRolling) {
                        tempName = "dodgeroll";
                    }
                    System.out.println("tempname: " + tempName + " showImmune: " + showImmuneTexture);
                    textureName = showImmuneTexture ? "immune" : tempName;
                  }

        } else {
            rollTimer += delta;
        }
        if (rollTimer >= rollDuration) {
            if (isRolling) {
                textureName = storedTextureName;
            }
            isRolling = false;
            SPEED = 200.0f;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        // Add any additional rendering for the player here
    }

    @Override
    public void dispose() {
        System.out.println("Disposing Player");

        // Dispose any resources used by the player here
    }


    public void moveUp() {
        position.y += SPEED * Gdx.graphics.getDeltaTime();
        if (!isRolling) {
            textureName = "up";
        }
        direction.set(0, 1);
    }

    public void moveDown() {
        position.y -= SPEED * Gdx.graphics.getDeltaTime();
        if (!isRolling) {
            textureName = "down";
        }
        direction.set(0, -1);
    }


    public void moveLeft() {
        position.x -= SPEED * Gdx.graphics.getDeltaTime();
        if (!isRolling) {
            textureName = "left";
        }
        direction.set(-1, 0);
    }

    public void moveRight() {
        position.x += SPEED * Gdx.graphics.getDeltaTime();
        if (!isRolling) {
            textureName = "right";
        }
        direction.set(1, 0);
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void dodgeroll() {
        // Apply dodge roll logic here...
        if (!isImmune) {
            // Set immunity and timer after dodgeroll
            isImmune = true;
            immunityTimer = 0.5f; // Set the immunity duration here (e.g., 1 second)
        }

        System.out.println("Dodge, isrolling " + isRolling + " rollTimer: " + rollTimer);
        if (!isRolling && rollTimer >= rollCooldown /* Input condition for dodge roll */) {

            System.out.println("Roll");
            storedTextureName = textureName;
            textureName = "dodgeroll";
            // Example: Set player invincibility frames during the roll
            isRolling = true;
            rollTimer = 0.0f;

            // Example: Move the player in the desired direction during the roll
            SPEED = 400.0f;
        }
    }

    public void takeDamage(float damage) {
        System.out.println("Ouch! Health: " + health);
        if (health <= 0) {

            gameStateManager.setState(new GameOverState(gameStateManager));
            // Player is defeated, you can add game over logic here
        } else  if (!isImmune) {
            health -= damage;
            isImmune = true;
            immunityTimer = 1.0f; // Set the immunity duration here (e.g., 1 second)
        }
    }

    public float getHealthPercentage() {
        return health / maxHealth;
    }


    public void collisionResponse(List<Entity> entities) {
        for (Entity collidingEntity : entities) {
            if (collidingEntity instanceof Enemy) {
                float damage = ((Enemy) collidingEntity).getDamageValue();
                takeDamage(damage);
                // Move the player away from the enemy
                float pushBackX = position.x - collidingEntity.position.x;
                float pushBackY = position.y - collidingEntity.position.y;
                position.add(200*pushBackX/GameAssetManager.getInstance().getTexture(collidingEntity.textureName).getWidth(), 200*pushBackY/GameAssetManager.getInstance().getTexture(collidingEntity.textureName).getHeight());

            } else if (collidingEntity instanceof Obstacle) {
                // If the colliding entity is an obstacle, prevent the player from moving into it
                float pushBackX = position.x - collidingEntity.position.x;
                float pushBackY = position.y - collidingEntity.position.y;
                position.add(200*pushBackX/GameAssetManager.getInstance().getTexture(collidingEntity.textureName).getWidth(), 200*pushBackY/GameAssetManager.getInstance().getTexture(collidingEntity.textureName).getHeight());
            }
        }
    }
}
