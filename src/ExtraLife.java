public class ExtraLife extends Treasure{

    public ExtraLife() {
        super();
        setIcon("💚");
        setName("1UP");
        setDescription("Gives you 1 extra life, use it wisely!");
    }

    @Override
    public void applyEffect(Player player) {
        player.setLife(player.getLife() + 1);
    }
}
