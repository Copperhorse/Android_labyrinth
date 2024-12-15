package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Goal {
    private float x, y; // Center of the goal rectangle
    private int size;   // Size of the goal rectangle

    public Goal(float x, float y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        // IT IS THE CENTER OF THE RECTANGLE.
        shapeRenderer.rect(x - size / 2, y - size / 2, size, size);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
