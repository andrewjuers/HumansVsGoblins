public class Nuke extends Treasure {

    public Nuke() {
        super();
        setIcon("☢️");
        setName("NUKE");
        setDescription("Kills all of the goblins on the board!");
    }

    public void applyEffect(Player player, Board board) {
        for (int i=0; i<board.getGoblins().size(); i++) player.addKill();
        board.clearGoblins();
    }
}
