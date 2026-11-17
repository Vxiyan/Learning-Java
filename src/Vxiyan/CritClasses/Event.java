package Vxiyan.CritClasses;

import Vxiyan.Utils.Loggy;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class representing a game event in the Vxiyan system.
 * <p>
 * Events are categorized into several types and pre-populate a map of
 * event-specific data (correct answers, path descriptions, combat stats, etc.)
 * based on the type provided at construction time.
 * </p>
 *
 * <h2>Event Types</h2>
 * <ul>
 *   <li>{@link type#BIPATH} – Binary-choice events with a single correct answer</li>
 *   <li>{@link type#POLYPATH} – Multi-choice path/decision events</li>
 *   <li>{@link type#BASIC_BATTLE} – Standard combat encounters (HP, damage)</li>
 *   <li>{@link type#ADV_BATTLE} – Advanced combat encounters (HP, damage, difficulty)</li>
 *   <li>{@link type#SHOP} – Shop events (currently only logs a message)</li>
 * </ul>
 *
 * <p>
 * Subclasses must implement the abstract {@link #handle()} method to define
 * the concrete behavior when the event is triggered.
 * </p>
 *
 * @author Vxiyan
 * @see Player
 */
public abstract class Event {

    /** Human-readable name of this event instance. */
    protected String name;

    /**
     * Enumeration of supported event categories.
     */
    public enum type {
        /** Binary path / correct-answer style events. */
        BIPATH,

        /** Multi-option path / decision events. */
        POLYPATH,

        /** Basic combat encounters (health + attack). */
        BASIC_BATTLE,

        /** Advanced combat encounters (health + attack + difficulty). */
        ADV_BATTLE,

        /** Shop / merchant events. */
        SHOP
    }

    /** The category of this event. */
    protected type eventType;

    /**
     * Map holding event-specific data.
     * <ul>
     *   <li>BIPATH: {@code String} (correct answer)</li>
     *   <li>POLYPATH: {@code String[]} (path descriptions)</li>
     *   <li>BASIC_BATTLE: {@code String[]} of length 2 – {health, damage}</li>
     *   <li>ADV_BATTLE: {@code String[]} of length 3 – {health, damage, difficulty}</li>
     * </ul>
     */
    protected Map<String, Object> events = new HashMap<>();

    /**
     * Constructs an Event of the given type and pre-populates the
     * {@link #events} map with the corresponding data set.
     *
     * @param name      display name of the event
     * @param givenType category of the event (determines which data is loaded)
     */
    public Event(String name, type givenType) {
        this.name = name;
        this.eventType = givenType;

        if (this.eventType == type.BIPATH) {
            // Event format: "Name" → "Correct Answer"
            events.put("Wormhole", "jump over");
            events.put("Crossroads", "dark");
            events.put("Mimic_Chest", "open");
            events.put("TrueMimicChest", "run away");
            events.put("lever_wall", "pull");
            events.put("eat_mushroom", "eat");
            events.put("rope_bridge", "find another way");
            events.put("spaceship_ctrl_console", "don't touch");
            events.put("hooded_strangr_coin", "take");
            events.put("large_red_buttn", "push");
            events.put("Whirlpool", "jump in");
            events.put("bright_berries", "don't eat");
            events.put("unlocked_diary", "leave it");
            events.put("glowing_crystal", "leave it");
            events.put("mech_cat", "pet");
            events.put("ancient_totem", "grab");
            events.put("portal", "stay away");
            events.put("snail", "RUN");

        } else if (this.eventType == type.POLYPATH) {
            // Event format: "Name" → String[] of option descriptions
            events.put("three_tunnels", new String[]{
                    "left path leads to gold",
                    "middle path triggers a trap",
                    "right path goes back"
            });
            events.put("four_doors", new String[]{
                    "red door burns",
                    "blue door floods",
                    "green door heals",
                    "yellow door opens the exit"
            });
            events.put("junction", new String[]{
                    "north path has enemies",
                    "east path holds treasure",
                    "south path leads home",
                    "west path is blocked"
            });
            events.put("chasm", new String[]{
                    "jump across safely",
                    "use the rope",
                    "fall down",
                    "turn back"
            });
            events.put("shrine", new String[]{
                    "pray for health",
                    "steal the gem",
                    "destroy the altar",
                    "ignore the shrine"
            });
            events.put("ancient_obelisk", new String[]{
                    "touch the runes",
                    "read the inscription",
                    "walk away"
            });
            events.put("floating_islands", new String[]{
                    "use glider",
                    "swim in the mist",
                    "climb the chain"
            });
            events.put("mystic_pool", new String[]{
                    "drink the water",
                    "wash your face",
                    "fish in the depths",
                    "leave it alone"
            });
            events.put("spider_nest", new String[]{
                    "sneak past",
                    "burn the webs",
                    "fight the queen",
                    "turn back"
            });
            events.put("crystal_cave", new String[]{
                    "mine the gems",
                    "listen to the hum",
                    "smash the pillars",
                    "exit quickly"
            });

        } else if (this.eventType == type.BASIC_BATTLE) {
            // Event format: "Name" → {health, damage}
            events.put("goblin", new String[]{"50", "10"});
            events.put("slime", new String[]{"30", "5"});
            events.put("bandit", new String[]{"70", "15"});
            events.put("wolf", new String[]{"40", "12"});
            events.put("skeleton", new String[]{"45", "14"});
            events.put("giant_spider", new String[]{"60", "12"});
            events.put("zombie", new String[]{"55", "8"});
            events.put("venomous_snake", new String[]{"25", "18"});
            events.put("wild_boar", new String[]{"65", "14"});
            events.put("rogue_knight", new String[]{"80", "16"});

        } else if (this.eventType == type.ADV_BATTLE) {
            // Event format: "Name" → {health, damage, difficulty}
            events.put("dragon", new String[]{"200", "35", "hard"});
            events.put("orc_warlord", new String[]{"150", "25", "medium"});
            events.put("lich", new String[]{"120", "40", "expert"});
            events.put("golem", new String[]{"250", "20", "hard"});
            events.put("demon", new String[]{"180", "30", "hard"});
            events.put("vampire_lord", new String[]{"160", "32", "hard"});
            events.put("shadow_assassin", new String[]{"100", "45", "expert"});
            events.put("minotaur", new String[]{"220", "28", "medium"});
            events.put("hydra", new String[]{"300", "38", "expert"});
            events.put("frost_giant", new String[]{"280", "30", "hard"});
            events.put("goblin_scout", new String[]{"40", "8", "easy"});
            events.put("young_wolf", new String[]{"35", "6", "easy"});
            events.put("corrupted_slime", new String[]{"45", "7", "easy"});
            events.put("bandit_recruit", new String[]{"50", "10", "easy"});
            events.put("skeleton_guard", new String[]{"45", "9", "easy"});

        } else {
            // SHOP (and any future types)
            Loggy.println("Shop Incoming!");
        }
    }

    /**
     * Returns the display name of this event.
     *
     * @return the event name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the category of this event.
     *
     * @return the {@link type} of the event
     */
    public type getEventType() {
        return eventType;
    }

    /**
     * Handles the event logic when it is triggered.
     * <p>
     * This method is currently unfinished and must be implemented by concrete
     * subclasses. Typical responsibilities include presenting choices to the
     * player, resolving outcomes, applying damage/rewards, etc.
     * </p>
     */
    protected abstract void handle();
}
