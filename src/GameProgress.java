import java.io.Serializable;

public class GameProgress implements Serializable {
    private String name;
    private int health;
    private int weapon;
    private int lvl;
    private double distance;

    public GameProgress(String name, int health, int weapon, int lvl, double distance) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress:\n" +
                "name = " + name +
                "\nhealth = " + health +
                " ♥\nweapon = " + weapon +
                " ⌖\nlvl = " + lvl +
                " ⇧\ndistance = " + distance +
                " ⟷";
    }
}
