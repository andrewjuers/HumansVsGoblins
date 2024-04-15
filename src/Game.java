import java.util.*;

public class Game {
    private Board board;
    private final Scanner scanner;
    private boolean gameOver;

    public Game() {
        scanner = new Scanner(System.in);
        newGame();
    }

    public void newGame() {
        board = new Board();
        gameOver = false;
        System.out.println("Humans VS Goblins");
    }

    public void play() {
        String input = " ";
        while (!input.isEmpty() && input.toLowerCase().charAt(0) != 'q') {
            if (gameOver) {
                board.displayGameOver();
                input = getInput("P to play again, Q to quit: ");
                processGameOver(input);
            }
            else if (board.getCombat()) {
                board.displayCombatScene();
                input = getInput("F to fight, Q to quit: ");
                processFight(input);
            } else if (board.getNewItem() != null) {
                board.displayTreasure();
                input = getInput("C to collect, U to use, Q to quit: ");
                processNewItem(input);
            }
            else if (board.getSelectedInventory() != null) {
                board.displayInventory();
                input = getInput("W/S to select, U to use, B to go back, Q to quit: ");
                processInventory(input);
            }
            else {
                board.display();
                input = getInput("WASD to move, I for inventory, Q to quit: ");
                processTurn(input);
            }
        }
    }

    private String getInput(String message) {
        System.out.print(message);
        return scanner.next();
    }

    private void processTurn(String input) {
        String dir = input.toLowerCase();
        switch (dir.charAt(0)) {
            case 'w' -> board.getPlayer().moveUp();
            case 's' -> board.getPlayer().moveDown();
            case 'a' -> board.getPlayer().moveLeft();
            case 'd' -> board.getPlayer().moveRight();
            case 'i' -> {
                if (board.getPlayer().getInventory().isEmpty()) System.out.println("Nothing in inventory!");
                else board.updateInventoryDisplay("");
                return;
            }
        }
        board.moveGoblins(); // Move goblins
    }

    private void processFight(String input) {
        String f = "" + input.toLowerCase().charAt(0);
        if (f.equals("f")) {
            gameOver = !board.fight();
            System.out.println((!gameOver) ? "Goblin defeated!" : "You lost the fight!");
            if (gameOver && board.getPlayer().getLife() > 0) {
                System.out.println("The goblin disappeared!");
                gameOver = false;
            }
        }
    }

    private void processGameOver(String input) {
        String p = "" + input.toLowerCase().charAt(0);
        if (p.equals("p")) {
            newGame();
        }
    }

    private void processNewItem(String input) {
        String c = "" + input.toLowerCase().charAt(0);
        if (c.equals("u")) {
            board.useTreasure(board.getNewItem());
        } else if (c.equals("c")) {
            if (board.getPlayer().getInventory().size() >= 4) {
                System.out.println("Inventory full!");
            } else board.addItemToInventory();
        }
    }

    private void processInventory(String input) {
        String dir = "" + input.toLowerCase().charAt(0);
        switch (dir) {
            case "w", "s" -> board.updateInventoryDisplay(dir);
            case "b" -> board.setSelectedInventory(null);
            case "u" -> board.useTreasure(board.getSelectedInventory());
        }
    }
}
