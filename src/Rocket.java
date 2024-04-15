import java.util.Random;

public class Rocket extends Treasure {

    public Rocket() {
        super();
        setIcon("🚀");
        setName("ROCKET");
        setDescription("Ride a rocket to a random location!");
    }

    @Override
    public void applyEffect(Player player) {
        Random random = new Random();
        player.setX(random.nextInt(10));
        player.setY(random.nextInt(10));
    }
}
