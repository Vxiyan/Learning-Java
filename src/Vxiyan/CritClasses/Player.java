package Vxiyan.CritClasses;

import Vxiyan.Utils.Randy;
import Vxiyan.Utils.Loggy;

/**
 * Represents a player character in the Vxiyan game system.
 * <p>
 * Extends {@link BaseEntity} and initializes the player with a username,
 * default health of 100, and a randomly generated base statistic.
 * Special usernames trigger modified starting stats (for testing / easter eggs).
 * </p>
 *
 * <h2>Special Username Behavior</h2>
 * <ul>
 *   <li>{@code null} or blank → falls back to {@code "User"}</li>
 *   <li>{@code "vxiyan"} (case-insensitive) → maxHealth = 1000, baseStat = 1000</li>
 *   <li>{@code "quinn"} (case-insensitive) → maxHealth = 1, baseStat = -1</li>
 * </ul>
 *
 * @author Vxiyan
 * @see BaseEntity
 * @see Event
 */
public class Player extends BaseEntity {

    /** The player's username / display name. */
    String Uname;

    /**
     * Constructs a new Player with the given username.
     * <p>
     * Calls the superclass constructor with:
     * <ul>
     *   <li>maxHealth = 100.0</li>
     *   <li>baseStat = a random integer in the range [1, 100]</li>
     * </ul>
     * After construction, special username checks may override these values
     * and also update the four core stats and current health.
     * </p>
     *
     * @param Uname the desired username; may be {@code null}
     */
    public Player(String Uname) {
        super(100.0, Randy.randRange(1, 100));

        // Handle null / blank and special usernames
        if (Uname == null || Uname.isBlank()) {
            this.Uname = "User";
        } else if (Uname.strip().equalsIgnoreCase("vxiyan")) {
            this.Uname = Uname.strip();
            this.maxHealth = 1000;
            this.baseStat = 1000;
            // Keep the four core stats and current health in sync
            this.mana = 1000;
            this.strength = 1000;
            this.charisma = 1000;
            this.defense = 1000;
            this.healthPoints = 1000;
        } else if (Uname.strip().equalsIgnoreCase("quinn")) {
            this.Uname = Uname.strip();
            this.maxHealth = 1;
            this.baseStat = -1;
            this.mana = -1;
            this.strength = -1;
            this.charisma = -1;
            this.defense = -1;
            this.healthPoints = 1;
        } else {
            this.Uname = Uname.strip();
        }

        Loggy.println("Vxiyan.CritClasses.Player " + this.Uname + " created.");
    }
}
