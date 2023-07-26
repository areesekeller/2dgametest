package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameStateListener;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.GameAssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class StartState implements GameState {
    private Texture startTexture;
    private int screenWidth;
    private int screenHeight;
    private GameStateManager gameStateManager;
    private GameStateListener gameStateListener;

    public StartState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameStateListener = new StartStateListener();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Load start texture
        startTexture = GameAssetManager.getInstance().getTexture("start");
    }

    @Override
    public void enter() {
        // Register this state as a listener
        gameStateManager.addListener(gameStateListener);
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    public void handleInput() {
        // Check for a key press to transition to the play state
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            gameStateManager.removeListener(gameStateListener);
            gameStateManager.setState(new PlayState(gameStateManager));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // Begin the sprite batch
        spriteBatch.begin();
        System.out.println("Rendering StartState");


        // Draw the start texture at the center of the screen
        spriteBatch.draw(startTexture, 0, 0, screenWidth, screenHeight);

        // End the sprite batch
        spriteBatch.end();
    }

    @Override
    public void exit() {
        dispose();
        // Unload start texture (optional, can be left empty in this case)
    }

    public void dispose() {
        // Dispose of any resources when the state is no longer needed
        System.out.println("Disposing StartState");

        startTexture.dispose();
    }

    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;

    }
    // Listener to transition to the play state
    private class StartStateListener implements GameStateListener {
        @Override
        public void onStateChange(GameState newState) {
            if (newState instanceof StartState) {
                // Register as a listener again when transitioning back to this state (optional, can be left empty in this case)
                gameStateManager.addListener(gameStateListener);
            }
        }
    }
}
