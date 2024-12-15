package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
// DON'T BELEIVE I HAVE TO EXPLAIN THIS. SHIT IS EASY
public class Player {
    private float x, y; // Center of the player rectangle
    private int size;   // Size of the player rectangle

    public Player(float startX, float startY, int size) {
        this.x = startX;
        this.y = startY;
        this.size = size;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x - size / 2, y - size / 2, size, size);
    }

    public void move(float dx, float dy, float minX, float minY, float maxX, float maxY) {
        // Update position with bounds checking
        x = Math.max(minX, Math.min(x + dx, maxX));
        y = Math.max(minY, Math.min(y + dy, maxY));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

