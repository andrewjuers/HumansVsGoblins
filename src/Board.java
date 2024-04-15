import java.util.ArrayList;
import java.util.Random;

public class Board {
    private final int size = 10;
    private final Player player;
    private final ArrayList<Goblin> goblins;
    private final Tile[][] tiles;
    // Variables to control game state
    private boolean combat;
    private Treasure newItem;
    private Treasure selectedInventory;
    private final Random random;

    public Board() {
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[j][i] = new Tile(j, i); // Create a new Tile object for each element
            }
        }

        player = new Player();
        goblins = new ArrayList<>();
        goblins.add(new Goblin(0,0));
        goblins.add(new Goblin(4, 4));
        goblins.add(new Goblin(9,0));

        combat = false;
        newItem = null;
        selectedInventory = null;

        random = new Random();

        updateGrid();
    }

    // Updates each tile
    public void updateGrid() {
        combat = false; // Set combat to false before loop
        newItem = null;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[j][i].setPlayer(null);
                tiles[j][i].setGoblin(null);
                // Player is on this tile
                if (i == player.getY() && j == player.getX()) tiles[j][i].setPlayer(player);
                // Check if a goblin is on this tile
                for (Goblin goblin: goblins)
                    if (goblin.getY() == i && goblin.getX() == j) {
                        tiles[j][i].setGoblin(goblin);
                        break;
                    }
                // Initiate combat
                if (tiles[j][i].combat()) combat = true;
                // Player is on the same tile as treasure
                else if (tiles[j][i].onItem()) {
                    newItem = tiles[j][i].getTreasure();
                    newItem.obtain();
                }
            }
        }
    }

    // Method to display the board to the console
    public void display() {
        // Update first
        updateGrid();
        // Iterate over the grid and print each cell's value
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(tiles[j][i].toString() + "\t");
            }
            System.out.println(); // Move to the next line for the next row
        }
        displayInfoBar();
    }

    public void displayCombatScene() {
        String fight = "FIGHT!";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j > 1 && j < 8) System.out.print(fight.charAt(j-2) + "\t");
                else if (i == 5 && j == 3) System.out.print(goblins.getFirst().toString() + "\t");
                else if (i == 5 && j == 4) System.out.print("V" + "\t");
                else if (i == 5 && j == 5) System.out.print("S" + "\t");
                else if (i == 5 && j == 6) System.out.print(player.toString() + "\t");
                else System.out.print("⬛" + "\t");
            }
            System.out.println(); // Move to the next line for the next row
        }
        displayInfoBar(); // Added to see your info during combat
    }

    public void displayTreasure() {
        String n = "NEW";
        String item = "ITEM";
        // Calculate the number of spaces needed on each side of the string
        int nameSize = newItem.getName().length();
        int totalSpaces = 10 - nameSize;
        int spacesOnLeft = totalSpaces / 2;
        for (int y=0; y < size; y++) {
            for (int x=0; x < size; x++) {
                if (y == 0 && x > 0 && x < 4) System.out.print(n.charAt(x-1));
                else if (y == 0 && x > 4 && x < 9) System.out.print(item.charAt(x-5));
                else if (y == 5 && x == 4) System.out.print(newItem.toString());
                else if (y == 4 && x >= spacesOnLeft && x < spacesOnLeft + nameSize) System.out.print(newItem.getName().charAt(x - spacesOnLeft));
                else if (y == 6 && x == 0) System.out.print(newItem.getDescription());
                else if (y == 6) continue;
                else System.out.print("⬛");
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public void displayInventory() {
        String inventory = "INVENTORY";
        String info = "INFO";
        for (int y=0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y == 0 && x > 0) System.out.print(inventory.charAt(x-1));
                else if (y >= 2 && y < 2 + player.getInventory().size() && x == 1) {
                    Treasure t = player.getInventory().get(y-2);
                    if (selectedInventory.equals(t)) System.out.print("[");
                    System.out.print(t.toString() + "\t" + t.getName());
                    if (selectedInventory.equals(t)) System.out.print("]");
                    else if (t.getName().length() < 4) System.out.print("\t");
                }
                else if (y == 7 && x > 0 && x < 5) System.out.print(info.charAt(x-1));
                else if (y == 8 && x == 0) System.out.print(selectedInventory.getDescription());
                else if (y == 8) continue;
                else if (y >= 2 && y < 2 + player.getInventory().size() && x != 0 && x < 4) continue;


                else System.out.print("⬛");
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public void displayGameOver() {
        String game = "GAME";
        String over = "OVER";
        String score = "SCORE:";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 && j > 0 && j < 5) System.out.print(game.charAt(j-1) + "\t");
                else if (i == 0 && j > 5) System.out.print(over.charAt(j-6) + "\t");
                else if (i == 5 && j > 0 && j < 7) System.out.print(score.charAt((j-1)) + "\t");
                else if (i == 5 && j == 8) System.out.print(player.getKills() + "\t");
                else System.out.print("⬛" + "\t");
            }
            System.out.println(); // Move to the next line for the next row
        }
    }

    public void displayInfoBar() {
        System.out.println("Score: " + player.getKills() + "\t❤️: " + player.getLife() + "\tLuck: " + (int) (100 * player.getPower()) + "%");
    }

    public ArrayList<Goblin> getGoblins() {
        return goblins;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getCombat() {
        return combat;
    }

    public Treasure getNewItem() {
        return newItem;
    }

    public Treasure getSelectedInventory() {
        return selectedInventory;
    }

    public void setSelectedInventory(Treasure t) {
        selectedInventory = t;
    }

    public void clearGoblins() {
        goblins.clear();
    }

    // Goblins pursue player
    public void moveGoblins() {
        for (Goblin goblin: goblins) {
            goblin.makeMove(player, tiles);
            updateGrid();
        }
        goblinArmy();
        updateGrid();
    }

    // Add more goblins when goblin is defeated
    public void goblinArmy() {
        if (goblins.size() >= 3 + player.getKills()) return;
        Tile[] spawnTiles = {tiles[0][0], tiles[0][9], tiles[9][0], tiles[9][9]};
        Tile spawnTile = spawnTiles[random.nextInt(spawnTiles.length)];
        goblins.add(new Goblin(spawnTile.getX(), spawnTile.getY()));
    }

    // Calculate combat (randomly)
    public boolean fight() {
        boolean win = false;
        double d = random.nextDouble();
        // Randomly determine winner
        if (player.getKills() == 0 || player.getPower() >= d) win = true;
        // Remove the dead goblin (or make him disappear)
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (tiles[x][y].combat()) goblins.remove(tiles[x][y].getGoblin());
            }
        }
        if (win) {
            // Increase score, increase power, and create treasure chest
            player.addKill();
            player.setPower(player.getPower() + 0.01);
            createTreasure();
        }
        else {
            // Decrease life and luck
            player.setPower(player.getPower() - 0.01);
            player.setLife(player.getLife() - 1);
        }

        updateGrid(); // Update the grid with removed goblin
        return win;
    }

    // Create treasure chest when goblin is defeated
    public void createTreasure() {
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        tiles[x][y].setTreasure(Treasure.generateRandomTreasure());
    }

    public void useTreasure(Treasure t) {
        selectedInventory = null;
        if (t instanceof Nuke) {
            ((Nuke) t).applyEffect(player, this);
            moveGoblins(); // Move goblins so the board isn't empty
        }
        else t.applyEffect(player);
        t.printEffect();
        // Remove treasure from player inventory and the tile
        player.removeTreasure(t);
        removeTreasureFromTile();
        updateGrid();
    }

    public void removeTreasureFromTile() {
        // Remove the treasure from the board
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (tiles[x][y].onItem()) {
                    tiles[x][y].setTreasure(null);
                    return;
                }
            }
        }
    }

    public void updateInventoryDisplay(String dir) {
        if (player.getInventory().isEmpty()) return;
        else if (selectedInventory == null) selectedInventory = player.getInventory().getFirst();
        if (player.getInventory().size() == 1 || dir.isEmpty()) return;
        char d = dir.toLowerCase().charAt(0);
        int index = player.getInventory().indexOf(selectedInventory);
        switch (d) {
            case 's' -> {
                if (index != player.getInventory().size() - 1) {
                    selectedInventory = player.getInventory().get(index + 1);
                } else selectedInventory = player.getInventory().getFirst();
            } case 'w' -> {
                if (index != 0) {
                    selectedInventory = player.getInventory().get(index - 1);
                } else selectedInventory = player.getInventory().getLast();
            }
        }
    }

    public void addItemToInventory() {
        player.addTreasure(newItem);
        System.out.println(newItem.getName() + " added to inventory!");
        removeTreasureFromTile();
        updateGrid();
    }
}
