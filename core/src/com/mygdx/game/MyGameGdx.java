package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Enemy;
import com.mygdx.game.entities.Obstacle;
import com.mygdx.game.entities.Player;
import com.mygdx.game.managers.CollisionManager;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameAssetManager;
import com.mygdx.game.GameStateListener;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.StartState;

import java.util.ArrayList;
import java.util.List;

// ... (imports remain the same)

public class MyGameGdx extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    // Game States
    private GameStateManager gameStateManager;
    private GameAssetManager gameAssetManager;
    private GameStateListener gameStateListener;

    // Entities

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameAssetManager = GameAssetManager.getInstance();

        System.out.println("Loading assets...");
        gameAssetManager.load();
        // Initialize Game States
        gameStateManager = GameStateManager.getInstance();
        gameStateManager.setState(new StartState(gameStateManager));
        this.gameStateListener = new mainListener();

        // Load Assets

        // Initialize Entities
    }

    @Override
    public void render() {
        System.out.println("Current GameState: " + gameStateManager.getState());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update current game state
        gameStateManager.update(Gdx.graphics.getDeltaTime());

        // Render based on current game state
        batch.setProjectionMatrix(camera.combined);
        gameStateManager.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        GameAssetManager.getInstance().dispose();
        GameStateManager.getInstance().dispose();
    }

    public void resize(int width, int height) {
        GameStateManager.getInstance().resize(width, height);
    }
    private class mainListener implements GameStateListener {
        @Override
        public void onStateChange(GameState newState) {
            if (newState instanceof StartState) {
                // Register as a listener again when transitioning back to this state (optional, can be left empty in this case)
                gameStateManager.addListener(gameStateListener);
            }
            System.out.println("State changed to: " + newState);
        }
    }
}
