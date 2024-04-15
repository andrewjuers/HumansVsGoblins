public class Tile {
    private Player player;
    private Goblin goblin;
    private Treasure treasure;
    private final int x;
    private final int y;

    public Tile(int x, int y) {
        player = null;
        goblin = null;
        treasure = null;
        this.x = x;
        this.y = y;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Goblin getGoblin() {
        return goblin;
    }

    public void setGoblin(Goblin goblin) {
        this.goblin = goblin;
    }

    public boolean combat() {
        return (goblin != null && player != null);
    }

    // Check if the square is empty for rng purposes
    public boolean isEmpty() {
        return (goblin == null && player == null && treasure == null);
    }

    public boolean onItem() {
        return (player != null && treasure != null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Treasure getTreasure() {
        return treasure;
    }

    public void setTreasure(Treasure t) {
        treasure = t;
    }

    @Override
    public String toString() {
        if (player != null) return player.toString();
        else if (goblin != null) return goblin.toString();
        else if (treasure != null) return treasure.toString();
        return "⬛";
    }
}

