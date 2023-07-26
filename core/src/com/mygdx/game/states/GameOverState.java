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
import com.mygdx.game.entities.Player;
import com.mygdx.game.GameStateListener;

import java.util.ArrayList;
import java.util.List;

public class GameOverState implements GameState {

    private Texture gameOverTexture;
    private boolean isDisposed;
    private int screenWidth;
    private int screenHeight;


    private GameStateListener gameStateListener;

    private GameStateManager gameStateManager;


    public GameOverState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameStateListener = new GameOverStateListener();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();


        // Load start texture
        isDisposed = false;
        gameOverTexture = GameAssetManager.getInstance().getTexture("gameOver");

        GameStateManager.getInstance().addListener(this.gameStateListener);
    }

    // Rest of the methods remain the same...


    @Override
    public void enter() {

    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        System.out.println("Rendering GameOverState");

        spriteBatch.begin();
        spriteBatch.draw(gameOverTexture, 0, 0, screenWidth, screenHeight );

        spriteBatch.end();
    }


    @Override
    public void handleInput() {
        System.out.println("Handling Input in GameOverState");

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            gameStateManager.setState(new StartState(gameStateManager));
        }
    }

    @Override
    public void exit() {
        isDisposed = true;
    }

    @Override
    public void dispose() {
        System.out.println("Disposing GameOverState");

        if (!isDisposed) {
            // Dispose of any resources when the state is no longer needed
            gameOverTexture.dispose();
        }
    }

    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }



    private class GameOverStateListener implements GameStateListener {
        @Override
        public void onStateChange(GameState newState) {
            if (newState instanceof GameOverState) {
                // Register as a listener again when transitioning back to this state (optional, can be left empty in this case)
                gameStateManager.addListener(gameStateListener);
            }
            // You can implement logic here to handle state changes, if needed
        }
    }
}
