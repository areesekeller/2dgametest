package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.managers.GameAssetManager;

import com.badlogic.gdx.math.Rectangle;
import java.util.List;

public abstract class Entity {
    protected Vector2 position;
    protected float width;
    protected float height;
    protected Rectangle boundingBox;
    protected String textureName;
    protected String storedTextureName;

    protected float maxHealth;

    protected float health;

    protected float damageValue;

    public Entity(float x, float y, float width, float height, String textureName) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.boundingBox = new Rectangle(x, y, width, height);
        this.textureName = textureName;
        this.storedTextureName = textureName;
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        System.out.println("Rendering texture: " + textureName);
        System.out.println("Position: (" +  position.x + ", " + position.y + ")");
        System.out.println("Width: " + width + ", Height: " + height);
        batch.draw(GameAssetManager.getInstance().getTexture(textureName), position.x, position.y, width, height);
    }
    public Rectangle boundingBox() {
        return new Rectangle(position.x, position.y, width, height);
    }

    public String getTextureName() {
        return textureName;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    // Abstract method to handle collision response with other entities
    public abstract void collisionResponse(List<Entity> entities);

    public void dispose() {
    }
}
