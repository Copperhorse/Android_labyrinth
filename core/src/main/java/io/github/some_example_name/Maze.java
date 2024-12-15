package io.github.some_example_name;

import java.util.Random;
import java.util.Stack;
// MAZE GENERATION ALGORITHM BASED UPON RECURSIVE BACKTRACKING,
// TRIED FLOOD FILL ALGORITHM, FUCKER DIDN'T WORK. ENDED UP WITH THIS.
// THERE ARE CHANCES THAT THERE WILL NOT BE A CLEAR PATH BETWEEN THE PLAYER AND THE GOAL,
// PLAY WITH THE CARDS YOU ARE DEALT WITH. ALSO PLEASE IMPROVE IT IF YOU CAN.
public class Maze {

    protected int horiz;              // Horizontal dimension
    protected int vert;               // Vertical dimension
    protected boolean[][] hWalls;     // Horizontal walls
    protected boolean[][] vWalls;     // Vertical walls
    private boolean[][] visited;      // Visited cells
    private static Random random = new Random();

    public Maze(int horiz, int vert) {
        this.horiz = horiz;
        this.vert = vert;

        hWalls = new boolean[horiz][vert - 1]; // Horizontal walls
        vWalls = new boolean[horiz - 1][vert]; // Vertical walls
        visited = new boolean[horiz][vert];

        // Initialize all walls
        for (int x = 0; x < horiz; x++) {
            for (int y = 0; y < vert - 1; y++) {
                hWalls[x][y] = true;
            }
        }
        for (int x = 0; x < horiz - 1; x++) {
            for (int y = 0; y < vert; y++) {
                vWalls[x][y] = true;
            }
        }

        // Generate the maze
        generateMaze(0, 0);
    }

    private void generateMaze(int x, int y) {
        visited[x][y] = true;

        // Randomized directions: 0 = up, 1 = right, 2 = down, 3 = left
        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions);

        for (int dir : directions) {
            int nx = x, ny = y;

            switch (dir) {
                case 0: ny -= 1; break; // Up
                case 1: nx += 1; break; // Right
                case 2: ny += 1; break; // Down
                case 3: nx -= 1; break; // Left
            }

            if (isInBounds(nx, ny) && !visited[nx][ny]) {
                // Remove wall between (x, y) and (nx, ny)
                if (dir == 0) hWalls[x][y - 1] = false;  // Remove upper wall
                if (dir == 1) vWalls[x][y] = false;      // Remove right wall
                if (dir == 2) hWalls[x][y] = false;      // Remove lower wall
                if (dir == 3) vWalls[x - 1][y] = false;  // Remove left wall

                generateMaze(nx, ny); // Recursive call
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < horiz && y >= 0 && y < vert;
    }

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
