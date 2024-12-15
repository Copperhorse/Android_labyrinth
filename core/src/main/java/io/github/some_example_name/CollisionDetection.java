package io.github.some_example_name;

public class CollisionDetection {
// COLLISION DETECTION IS FINICKY, AND HARD TO WORK WITH. THE BEST SOURCE I GOT FOR IT WAS A
    //https://youtu.be/AL92LrbDNY0?si=sNTAIttMwMvnwhR9
    // THE REST WERE GOOD TO BUT THEY WERE MOST MATHEMATICAL PROOFS OF DIFFERENT TYPES OF COLLISION DETECTION
    // WHICH WAS NOT WHAT I WANTED
    private Maze maze;
    private int cellSize;

    public CollisionDetection(Maze maze, int cellSize) {
        this.maze = maze;
        this.cellSize = cellSize;
    }

    public boolean isWallAtPosition(float x, float y) {
        int cellX = (int) (x / cellSize);
        int cellY = (int) (y / cellSize);

        if (cellX < 0 || cellX >= maze.horiz || cellY < 0 || cellY >= maze.vert) {
            return true; // Out of bounds
        }

        if (maze.hWalls[cellX][cellY] || maze.vWalls[cellX][cellY]) {
            return true; // There is a wall at this position
        }

        return false;
    }

    public boolean canMoveTo(float nextX, float nextY) {
        return !isWallAtPosition(nextX, nextY);
    }
}
