import java.util.ArrayList;

public class Player {
    private int x,y;
    private double power;
    private int kills;
    private int life;
    private final ArrayList<Treasure> inventory;

    public Player() {
        x = 4;
        y = 9;
        power = 0.5;
        kills = 0;
        life = 3;
        inventory = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\uD83E\uDDD1";
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveUp() {
        if (y > 0) y--;
    }
    public void moveDown() {
        if (y < 9) y++;
    }
    public void moveLeft() {
        if (x > 0) x--;
    }
    public void moveRight() {
        if (x < 9) x++;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        // Cap the power
        this.power = Math.min(power, 0.8);
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        kills++;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public ArrayList<Treasure> getInventory() {
        return inventory;
    }

    public void addTreasure(Treasure t) {
        inventory.add(t);
    }

    public void removeTreasure(Treasure t) {
        inventory.remove(t);
    }
}
