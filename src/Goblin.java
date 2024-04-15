import java.util.*;

public class Goblin extends Player{

    public Goblin(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public String toString() {
        return "\uD83D\uDC7A";
    }

    // Method to make the goblin move closer to the player
    public void makeMove(Player player, Tile[][] tiles) {
        // Slow them down randomly
        Random random = new Random();
        if (random.nextDouble() > .5) return;
        // Get player's coordinates
        int playerX = player.getX();
        int playerY = player.getY();

        // Calculate the difference in x and y coordinates between the goblin and the player
        int dx = playerX - getX();
        int dy = playerY - getY();

        // Check which direction the goblin should move to get closer to the player
        if (Math.abs(dx) > Math.abs(dy)) {
            // Move horizontally
            if (dx > 0 && canMoveTo(getX() + 1, getY(), tiles)) {
                // Move right if the destination tile is empty
                moveRight();
            } else if (dx < 0 && canMoveTo(getX() - 1, getY(), tiles)) {
                // Move left if the destination tile is empty
                moveLeft();
            }
        } else {
            // Move vertically
            if (dy > 0 && canMoveTo(getX(), getY() + 1, tiles)) {
                // Move down if the destination tile is empty
                moveDown();
            } else if (dy < 0 && canMoveTo(getX(), getY() - 1, tiles)) {
                // Move up if the destination tile is empty
                moveUp();
            }
        }
    }

    // Method to check if the goblin can move to the specified coordinates
    private boolean canMoveTo(int x, int y, Tile[][] tiles) {
        // Check if the coordinates are within the bounds of the board
        if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[0].length) {
            return false; // Coordinates are out of bounds
        }
        // Check if the destination tile is empty (does not have a goblin or treasure)
        return tiles[x][y].getGoblin() == null && tiles[x][y].getTreasure() == null;
    }
}
