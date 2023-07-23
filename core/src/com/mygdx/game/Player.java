package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private Texture playerUpTexture;
    private Texture playerDownTexture;
    private Texture playerLeftTexture;
    private Texture playerRightTexture;
    private Texture dodgeRollTexture;

    private float health;
    private float maxHealth;


    // Dodge roll cooldown in milliseconds
    private long dodgeRollCooldown;

    // Flag to track whether the player is currently performing a dodge roll
    private boolean isDodging;
    private Direction currentDirection;
    private Rectangle boundingBox;

    private OrthographicCamera camera;


    // ... Your other methods and constructor ...

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        DEFAULT
    }
    private float defaultSpeed; // Add a variable to store the default speed
    private float speed;
    private List<Enemy> enemies;
    private List<Obstacle> obstacles;

    public Player(float x, float y, List<Enemy> enemies, List<Obstacle> obstacles, OrthographicCamera camera) {
        playerUpTexture = new Texture("player/up.png");
        playerDownTexture = new Texture("player/down.png");
        playerLeftTexture = new Texture("player/left.png");
        playerRightTexture = new Texture("player/right.png");
        dodgeRollTexture = new Texture("dodgeroll.png");

        this.setX(x);
        this.setY(y);
        this.setWidth(GameConfig.PLAYER_WIDTH);
        this.setHeight(GameConfig.PLAYER_HEIGHT);
        this.health = 100.0f;
        this.maxHealth = 100.0f;
        this.defaultSpeed = 200.0f; // Adjust the speed as needed
        this.speed = 200.0f;
        this.isDodging = false;
        this.dodgeRollCooldown = 0; // The initial value means the dodge roll is available at the start
        this.boundingBox = new Rectangle(x, y, getWidth(), getHeight());
        this.enemies = enemies; // Assign the enemies list
        this.obstacles = obstacles; // Assign the obstacles list
        this.camera = camera;

    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    private void updateBoundingBox() {
        boundingBox.setPosition(getX(), getY());
        boundingBox.setSize(getWidth(), getHeight());
    }
    public Direction getCurrentDirection() {
        if (currentDirection == null) {
            return Direction.DEFAULT;
        }
        return currentDirection;
    }

    public void moveUp() {
        float newY = getY() + speed * Gdx.graphics.getDeltaTime();
        Rectangle newBoundingBox = new Rectangle(getX(), newY, getWidth(), getHeight());

        if (!checkCollisionWithEnemies(newBoundingBox) && !checkCollisionWithObstacles(newBoundingBox)) {
            setY(newY);
            currentDirection = Direction.UP;
        } else {
            // Collision detected, handle it here if needed
            handleCollisions(enemies, obstacles);
        }
    }

    public void moveDown() {
        float newY = getY() - speed * Gdx.graphics.getDeltaTime();
        Rectangle newBoundingBox = new Rectangle(getX(), newY, getWidth(), getHeight());

        if (!checkCollisionWithEnemies(newBoundingBox) && !checkCollisionWithObstacles(newBoundingBox)) {
            setY(newY);
            currentDirection = Direction.DOWN;
        } else {
            // Collision detected, handle it here if needed
            handleCollisions(enemies, obstacles);
        }
    }

    public void moveLeft() {
        float newX = getX() - speed * Gdx.graphics.getDeltaTime();
        Rectangle newBoundingBox = new Rectangle(newX, getY(), getWidth(), getHeight());

        if (!checkCollisionWithEnemies(newBoundingBox) && !checkCollisionWithObstacles(newBoundingBox)) {
            setX(newX);
            currentDirection = Direction.LEFT;
        } else {
            // Collision detected, handle it here if needed
            handleCollisions(enemies, obstacles);
        }
    }

    public void moveRight() {
        float newX = getX() + speed * Gdx.graphics.getDeltaTime();
        Rectangle newBoundingBox = new Rectangle(newX, getY(), getWidth(), getHeight());

        if (!checkCollisionWithEnemies(newBoundingBox) && !checkCollisionWithObstacles(newBoundingBox)) {
            setX(newX);
            currentDirection = Direction.RIGHT;
        } else {
            // Collision detected, handle it here if needed
            handleCollisions(enemies, obstacles);
        }
    }

    // Helper method to check collisions with enemies
    private boolean checkCollisionWithEnemies(Rectangle newBoundingBox) {
        for (Enemy enemy : enemies) {
            if (newBoundingBox.overlaps(enemy.getBoundingRectangle())) {
                return true; // Collision with an enemy detected
            }
        }
        return false; // No collision with enemies
    }

    // Helper method to check collisions with obstacles
    private boolean checkCollisionWithObstacles(Rectangle newBoundingBox) {
        for (Obstacle obstacle : obstacles) {
            if (newBoundingBox.overlaps(obstacle.getBoundingRectangle())) {
                return true; // Collision with an obstacle detected
            }
        }
        return false; // No collision with obstacles
    }


    // Method for the player to perform a dodge roll
    public void dodgeRoll() {
        if (!isDodging && dodgeRollCooldown <= 0) {
            isDodging = true;
            // Double the player's speed during the dodge roll
            this.speed = defaultSpeed * 1.30f;

            // Simulate the dodge roll movement (Example: move the player twice as fast for a short duration)
            this.setPosition(this.getX() + speed * 2 * Gdx.graphics.getDeltaTime(), this.getY());

            // Set the dodge roll cooldown (e.g., 1000 milliseconds for a 1-second cooldown)
            dodgeRollCooldown = 500;
        }
    }


    public float getHealth() {
        return health;
    }


    public float getMaxHealth() {
        return maxHealth;
    }
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health <= 0) {
            handleGameOver();
        }
    }

    // In the Player class, add the following method to check for collisions with enemies and obstacles.
    public void checkCollisions(List<Enemy> enemies, List<Obstacle> obstacles) {
        for (Enemy enemy : enemies) {
            if (Intersector.overlaps(this.getBoundingRectangle(), enemy.getBoundingRectangle())) {
                // Collision with enemy, decrease player's health
                decreaseHealth(10); // You can adjust the amount of health to decrease
            }
        }

        for (Obstacle obstacle : obstacles) {
            if (Intersector.overlaps(this.getBoundingRectangle(), obstacle.getBoundingRectangle())) {
                // Collision with obstacle, handle it here if needed
            }
        }
    }

    public void handleGameOver() {
        // Perform actions when the game is over
        // For example, display game over screen, reset the game, etc.
    }

    public void handleCollisions(List<Enemy> enemies, List<Obstacle> obstacles) {
        // Check for collisions with enemies
        // Update the bounding box before checking for collisions
        updateBoundingBox();

        // Check for collisions with enemies
        for (Enemy enemy : enemies) {
            if (boundingBox.overlaps(enemy.getBoundingRectangle())) {
                decreaseHealth(enemy.getDamage()); // Decrease health when colliding with an enemy
                // You can add additional logic here, like knocking back the player or destroying the enemy
            }
        }

        // Check for collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if (boundingBox.overlaps(obstacle.getBoundingRectangle())) {
                // Handle collision with the obstacle (e.g., prevent player movement in that direction)
                // You can add additional logic here, like bouncing back the player or destroying the obstacle
            }
        }
    }

    @Override
    public void act(float delta) {
        // Reduce the dodge roll cooldown if it's greater than 0
        if (dodgeRollCooldown > 0) {
            dodgeRollCooldown -= delta * 1000; // Convert delta (in seconds) to milliseconds
        }

        // If the dodge roll cooldown is finished, allow the player to roll again
        if (dodgeRollCooldown <= 0) {
            isDodging = false;
            this.speed = defaultSpeed;

        }


        // Add other logic that needs to be performed on each frame update

        super.act(delta);
    }
    public void draw(Batch batch, OrthographicCamera camera, float parentAlpha) {
        // Draw the player texture based on the player's current direction or the dodge roll texture if the player is dodging
        Texture playerTexture;
        if (isDodging) {
            playerTexture = dodgeRollTexture;
        } else {
            switch (getCurrentDirection()) {
                case UP:
                    playerTexture = playerUpTexture;
                    break;
                case DOWN:
                    playerTexture = playerDownTexture;
                    break;
                case LEFT:
                    playerTexture = playerLeftTexture;
                    break;
                case RIGHT:
                    playerTexture = playerRightTexture;
                    break;
                default:
                    playerTexture = playerUpTexture; // Default to up texture if direction is not recognized
                    break;
            }
        }

        // Use the provided camera to draw the player
        batch.setProjectionMatrix(camera.combined);
        batch.draw(playerTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    // Other methods, properties, and the getCurrentDirection() method can stay as is
    // ...
}
