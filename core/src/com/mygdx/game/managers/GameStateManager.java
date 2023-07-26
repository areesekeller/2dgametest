package com.mygdx.game.managers;

import com.mygdx.game.GameStateListener;
import com.mygdx.game.states.GameState;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.states.StartState;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;



public class GameStateManager {
    private GameState currentState;
    private List<GameStateListener> listeners;
    private static final GameStateManager instance = new GameStateManager();



    private GameStateManager() {
        currentState = new StartState(getInstance());
        listeners = new ArrayList<>();
    }
    public void setState(GameState newState) {
            currentState = newState;
            currentState.enter(); // Call enter() method when changing states
            notifyListeners(newState);
    }
    public GameState getState() {
        return currentState;
    }

    public void render(SpriteBatch batch) {
        currentState.render(batch);
    }
    public void update(float delta) {
        currentState.update(delta);
    }

    public void resize(int width, int height) {
        currentState.resize(width,height);
    }

    public void dispose() {
        currentState.dispose();
    }

    public static GameStateManager getInstance() {
        return instance;
    }

    public void addListener(GameStateListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(GameState newState) {
        List<GameStateListener> listenersCopy = new ArrayList<>(listeners);
        for (GameStateListener listener : listenersCopy) {
            listener.onStateChange(newState);
        }
    }



}
