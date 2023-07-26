package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class GameAssetManager {

    private static final Map<String, String> texturePaths = new HashMap<>();
    private static final AssetManager assetManager = new AssetManager();

    private static final GameAssetManager instance = new GameAssetManager();

    // Add all texture paths and their common names here
    private GameAssetManager() {
        texturePaths.put("up", "player/up.png");
        texturePaths.put("down", "player/down.png");
        texturePaths.put("left", "player/left.png");
        texturePaths.put("immune", "player/immune.png");
        texturePaths.put("right", "player/right.png");
        texturePaths.put("background", "bg.jpg");
        texturePaths.put("start", "start.jpeg");
        texturePaths.put("dodgeroll", "player/dodgeroll.png");
        texturePaths.put("enemy", "enemy/enemy.png");

        texturePaths.put("obstacle", "obstacle/obstacle1.png");
        texturePaths.put("gameOver", "gameover.jpg");


        // Add other textures and their paths here...
    }

    // Method to load all textures
    public void load() {
        for (String commonName : texturePaths.keySet()) {
            String path = texturePaths.get(commonName);
            assetManager.load(path, Texture.class);
        }

        assetManager.finishLoading(); // Block and wait until all assets are loaded
    }

    public static GameAssetManager getInstance() {
        return instance;
    }

    // Method to get a texture using its common name
    public static Texture getTexture(String commonName) {
        String path = texturePaths.get(commonName);
        if (path == null) {
            Gdx.app.error("GameAssetManager", "Texture not found for common name: " + commonName);
            return null;
        }

        return assetManager.get(path, Texture.class);
    }

    // Method to dispose of the asset manager when it's no longer needed
    public static void dispose() {
        assetManager.dispose();
    }
}
