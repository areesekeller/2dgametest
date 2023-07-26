package com.mygdx.game.states;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface GameState {
    // Method to handle initialization and setup when the state is entered.
    void enter();

    // Method to handle cleanup and resources release when the state is exited.
    void exit();

    // Method to handle user input and update the state.
    void handleInput();

    // Method to update the state (e.g., update positions, handle collisions, etc.).
    void update(float deltaTime);

    // Method to render the state (e.g., draw entities, UI, etc.).
    void render(SpriteBatch spriteBatch);

    // Method to dispose of any resources used by the state.
    void dispose();

    void resize(int width, int height);
}
