import java.util.ArrayList;
import java.util.Random;

public class Treasure {

    private boolean obtained;
    private String icon;
    private String description;
    private String name;

    public Treasure() {
        obtained = false;
        icon = "🍀";
        description = "Increases chance to win fights by 5%!";
        name = "CLOVER";
    }

    public void obtain() {
        obtained = true;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void applyEffect(Player player) {
        player.setPower(player.getPower() + 0.05);
    }

    @Override
    public String toString() {
        return (obtained) ? icon : "\uD83C\uDF81";
    }

    public void printEffect() {
        System.out.println("Used " + getName());
    }

    public static Treasure generateRandomTreasure() {
        Random random = new Random();
        ArrayList<Treasure> choices = new ArrayList<>();
        choices.add(new Treasure());
        choices.add(new Nuke());
        choices.add(new Rocket());
        choices.add(new ExtraLife());

        return choices.get(random.nextInt(choices.size()));
    }
}