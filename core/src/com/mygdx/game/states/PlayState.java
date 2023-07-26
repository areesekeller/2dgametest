package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Obstacle;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.managers.CollisionManager;
import com.mygdx.game.managers.GameAssetManager;
import com.mygdx.game.managers.GameStateManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.entities.Player;
import com.mygdx.game.GameStateListener;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import com.mygdx.game.ui.HealthBar;

import java.util.ArrayList;
import java.util.List;

public class PlayState implements GameState {

    private Player player;

    private List<Enemy> enemies;
    private ShapeRenderer shapeRenderer;
    private List<Obstacle> obstacles;

    private Texture backgroundTexture;
    private boolean isDisposed;


    private int screenWidth;
    private int screenHeight;
    private GameStateListener gameStateListener;

    private GameStateManager gameStateManager;

    private CollisionManager collisionManager;
    private OrthographicCamera camera;
    private  ExtendViewport viewport;

    private HealthBar playerHealthBar;
    private List<HealthBar> enemyHealthBars;

    public PlayState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameStateListener = new PlayStateListener();
        collisionManager = CollisionManager.getInstance();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);


        // Load start texture
        isDisposed = false;
        backgroundTexture = GameAssetManager.getInstance().getTexture("background");

        player = new Player(100, 100);
        playerHealthBar = new HealthBar(player);
        enemies = new ArrayList<>();
        enemyHealthBars = new ArrayList<HealthBar>();
        enemies.add(new Enemy(200,200));

        enemyHealthBars.add(new HealthBar(enemies.get(0)));
        obstacles = new ArrayList<>();

        obstacles.add(new Obstacle(200,50));
        GameStateManager.getInstance().addListener(this.gameStateListener);
    }

    // Rest of the methods remain the same...


    @Override
    public void enter() {

    }

    @Override
    public void update(float delta) {
        handleInput();
        player.update(delta);
        for (Enemy enemy : enemies) {
            enemy.update(delta);
            enemyHealthBars.get(enemies.indexOf(enemy)).setHealthPercentage(enemy.getHealthPercentage());
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        // Compile all entities into one list for collision detection
        List<Entity> allEntities = new ArrayList<>();
        allEntities.add(player);
        allEntities.addAll(enemies);
        allEntities.addAll(obstacles);

        // Perform collision detection and response for each entity
        for (Entity entity : allEntities) {
            List<Entity> collidingEntities = CollisionManager.getInstance().getCollidingEntities(entity, allEntities);
            entity.collisionResponse(collidingEntities);
        }
        playerHealthBar.setHealthPercentage(player.getHealthPercentage());



    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        System.out.println("Rendering PlayState");

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0,screenWidth, screenHeight );
        player.render(spriteBatch);

        System.out.println("Rendering enemy");
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);


        for (Enemy enemy : enemies) {
            System.out.println("Rendering enemy");
            enemy.render(spriteBatch);
            enemyHealthBars.get(enemies.indexOf(enemy)).render(shapeRenderer);
        }

        // Render obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.render(spriteBatch);
        }
        spriteBatch.end();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
        playerHealthBar.render(shapeRenderer);
        shapeRenderer.end();
    }


    @Override
    public void handleInput() {
        System.out.println("Handling Input in PlayState");

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.dodgeroll();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            System.out.println("Left key pressed");
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.moveUp();
        }
    }

    @Override
    public void exit() {
        isDisposed = true;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing PlayState");

        if (!isDisposed) {
            // Dispose of any resources when the state is no longer needed
            backgroundTexture.dispose();
            player.dispose();
            shapeRenderer.dispose();
        }
    }

    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        playerHealthBar.setX(20);  // Set the x-coordinate for the health bar
        playerHealthBar.setY(Gdx.graphics.getHeight() - 20); // Set the y-coordinate for the health bar
        camera.update();
    }



    private class PlayStateListener implements GameStateListener {
        @Override
        public void onStateChange(GameState newState) {
            if (newState instanceof PlayState) {
                // Register as a listener again when transitioning back to this state (optional, can be left empty in this case)
                gameStateManager.addListener(gameStateListener);
            }
            // You can implement logic here to handle state changes, if needed
        }
    }
}
