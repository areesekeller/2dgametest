package com.mygdx.game.managers;

import com.mygdx.game.entities.Entity;
import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private static final CollisionManager instance = new CollisionManager();

    private CollisionManager() {
        // Private constructor to enforce singleton pattern
    }

    public static CollisionManager getInstance() {
        return instance;
    }

    // Method to check for collisions between a given entity and a list of entities
    public List<Entity> getCollidingEntities(Entity entity, List<Entity> entities) {
        List<Entity> collidingEnemies = new ArrayList<>();

        for (Entity currEntity : entities) {
            if (currEntity != entity && isColliding(entity, currEntity)) {
                collidingEnemies.add(currEntity);
            }
        }

        return collidingEnemies;
    }

    // Method to check if two entities are colliding based on their bounding boxes
    private boolean isColliding(Entity entity1, Entity entity2) {
        return entity1.boundingBox().overlaps(entity2.boundingBox());
    }
}
