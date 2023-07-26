package com.mygdx.game;

import com.mygdx.game.states.GameState;

public interface GameStateListener {
    void onStateChange(GameState newState);
}
