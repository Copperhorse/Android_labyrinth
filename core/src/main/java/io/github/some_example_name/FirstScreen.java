package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//----------------------------------------------------------------
// THE MEAT OF THE GAME, ICLUDES PLAYER INPUT, GENERATION OF MAZE.
// PLAYER INPUT WITH KEYBOARD HAS COLLISION DETECTION BUT IT SOMEHOW DOES NOT SEEM TO WORK WITH
//THE TOUCH CONTROLS, I DON'T KNOW WHY THE FUCK IT NOT. IT JUST DOESN'T.
// NOT GONNA BOTHER WITH IT SINCE I AM GETTING LATE FOR SUBMISSION OF THIS PROJECT.
//HOPE TEACHER DOESN'T SEE THIS. IT FOCUSEES ON THE VIEWPORT FOR SMALLER MAZES, BUT USES THE CAMERA FOR THE LARGER MAZES.
// THE CAMERA MOVES AROUND WITH THE PLAYER. WHERE THE PLAYER GOES THE CAMERA FOLLOWS.
// I WANTED TO ADD THE GYROSCOPIC CONTROLS TO THE PLAYERS. BUT MY PHONE APPARENTLY DOESN'T SUPPORT IT? THE GYROSCOPE SIMPLY
// DOESN'T WORK.
//----------------------------------------------------------------

public class FirstScreen implements Screen {
    private  Main game;

    private Maze maze;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private int cellSize;

    private boolean useCamera;
    private Player player;
    private Goal goal;

    public FirstScreen(int horiz, int vert,Main game) {
        this.game = game;
        maze = new Maze(horiz, vert);
        shapeRenderer = new ShapeRenderer();

        if (horiz <= 20 && vert <= 20) {
            // Stretch the maze for small sizes
            cellSize = Math.min(Gdx.graphics.getWidth() / horiz, Gdx.graphics.getHeight() / vert);
            useCamera = false;
        } else {
            // Use camera for large mazes
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            cellSize = 32; // Fixed cell size for large mazes
            useCamera = true;
        }

        // Initialize player at the top-left corner
        player = new Player(cellSize / 2, cellSize / 2, cellSize / 2);

        // Find a reachable goal position with a limited number of attempts
        goal = findReachableGoal(horiz, vert, 100); // Adjust the number of attempts as needed
    }

    private Goal findReachableGoal(int horiz, int vert, int maxAttempts) {
        Goal potentialGoal = null;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int goalX = (int) (Math.random() * horiz) * cellSize + cellSize / 2;
            int goalY = (int) (Math.random() * vert) * cellSize + cellSize / 2;
            potentialGoal = new Goal(goalX, goalY, cellSize / 2);
            if (isGoalReachable(potentialGoal)) {
                break;
            }
        }
        if (potentialGoal == null) {
            // Fallback to a default position if no reachable goal is found after attempts
            int fallbackX = (horiz - 1) * cellSize + cellSize / 2;
            int fallbackY = (vert - 1) * cellSize + cellSize / 2;
            potentialGoal = new Goal(fallbackX, fallbackY, cellSize / 2);
        }
        return potentialGoal;
    }

    private boolean isGoalReachable(Goal goal) {
        // Perform a pathfinding check to see if the goal is reachable
        for (int x = 0; x < maze.horiz; x++) {
            for (int y = 0; y < maze.vert; y++) {
                if (maze.hWalls[x][y] || maze.vWalls[x][y]) {
                    // If there's any wall between the player and the goal, it's not reachable
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (useCamera) {
            camera.position.set(player.getX(), player.getY(), 0);
            camera.update();
            shapeRenderer.setProjectionMatrix(camera.combined);
        }

        // Draw the maze
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);

        for (int x = 0; x < maze.horiz; x++) {
            for (int y = 0; y < maze.vert; y++) {
                int startX = x * cellSize;
                int startY = y * cellSize;

                // Draw horizontal walls
                if (y < maze.vert - 1 && maze.hWalls[x][y]) {
                    shapeRenderer.line(startX, startY + cellSize, startX + cellSize, startY + cellSize);
                }

                // Draw vertical walls
                if (x < maze.horiz - 1 && maze.vWalls[x][y]) {
                    shapeRenderer.line(startX + cellSize, startY, startX + cellSize, startY + cellSize);
                }
            }
        }

        shapeRenderer.end();

        // Draw the player and goal
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.render(shapeRenderer);
        goal.render(shapeRenderer);
        shapeRenderer.end();

        handlePlayerMovement(delta);

        // Check if player reaches the goal
        checkWinCondition();
    }

    private void handlePlayerMovement(float delta) {
        float speed = 100 * delta; // Movement speed
        float nextX = player.getX();
        float nextY = player.getY();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Convert to screen coordinates

            // Move player towards the touch position
            float deltaX = touchX - player.getX();
            float deltaY = touchY - player.getY();
            float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance > 0) {
                float moveX = deltaX / distance * speed;
                float moveY = deltaY / distance * speed;
                nextX += moveX;
                nextY += moveY;
            }
        } else {
            // Existing accelerometer and gyroscope control logic
            int currentCellX = (int) (player.getX() / cellSize);
            int currentCellY = (int) (player.getY() / cellSize);

            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
                if (currentCellY < maze.vert - 1 && !maze.hWalls[currentCellX][currentCellY]) {
                    nextY += speed;
                }
            }
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
                if (currentCellY > 0 && !maze.hWalls[currentCellX][currentCellY - 1]) {
                    nextY -= speed;
                }
            }
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) {
                if (currentCellX < maze.horiz - 1 && !maze.vWalls[currentCellX][currentCellY]) {
                    nextX += speed;
                }
            }
            if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) {
                if (currentCellX > 0 && !maze.vWalls[currentCellX - 1][currentCellY]) {
                    nextX -= speed;
                }
            }
        }

        // Ensure player stays within the maze bounds
        nextX = Math.max(cellSize / 2, Math.min(nextX, maze.horiz * cellSize - cellSize / 2));
        nextY = Math.max(cellSize / 2, Math.min(nextY, maze.vert * cellSize - cellSize / 2));

        // Update player's position
        player.move(nextX - player.getX(), nextY - player.getY(), 0, 0, maze.horiz * cellSize, maze.vert * cellSize);
    }

    private void checkWinCondition() {
        float distanceToGoal = (float) Math.sqrt(Math.pow(player.getX() - goal.getX(), 2) + Math.pow(player.getY() - goal.getY(), 2));

        if (distanceToGoal < cellSize / 2) {
            game.setScreen(new WinScreen(game));
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
