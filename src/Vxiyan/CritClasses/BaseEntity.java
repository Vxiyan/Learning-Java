package Vxiyan.CritClasses;

import Vxiyan.Inter.Entity;
import java.util.Map;
import java.util.HashMap;

/**
 * Abstract base implementation of {@link Entity} that provides shared stats,
 * health management, combat, and inventory handling for game entities.
 *
 * <h2>Stats</h2>
 * <p>
 * Each entity has four core stats ({@code mana}, {@code strength},
 * {@code charisma}, {@code defense}). All four are initialized to the same
 * value ({@code baseStat}) when the entity is created. Health starts at
 * {@code maxHealth} and is clamped between {@code 0} and {@code maxHealth}.
 * </p>
 *
 * <h2>Inventory</h2>
 * <p>
 * Items are stored in the {@code inv} map (item name → quantity).
 * Items can be added, removed, or used. When an item is used, its effect is
 * looked up from a caller-supplied {@code HashMap<String, ItemEffect>}.
 * </p>
 *
 * <h2>Item Effects</h2>
 * <ul>
 *   <li>{@link ItemEffect#HEAL} – restores health</li>
 *   <li>{@link ItemEffect#DAMAGE} – damages this entity</li>
 *   <li>{@link ItemEffect#STATUP_MANA} – increases mana</li>
 *   <li>{@link ItemEffect#STATUP_STRENGTH} – increases strength</li>
 *   <li>{@link ItemEffect#STATUP_CHARISMA} – increases charisma</li>
 *   <li>{@link ItemEffect#STATUP_DEFENSE} – increases defense</li>
 *   <li>{@link ItemEffect#DEFAULT} – no effect (prints a message)</li>
 * </ul>
 *
 * @author Vxiyan
 * @see Entity
 * @see Player
 * @see Event
 */
public abstract class BaseEntity implements Entity {

    /** Current mana points. */
    protected double mana;

    /** Current strength points. */
    protected double strength;

    /** Current charisma points. */
    protected double charisma;

    /** Current defense points. */
    protected double defense;

    /** Current health points (clamped 0 … maxHealth). */
    protected double healthPoints;

    /** Maximum possible health. */
    protected double maxHealth;

    /** Base value used to initialize the four core stats. */
    protected double baseStat;

    /**
     * Flag indicating whether the entity has entered a portal.
     * Public for convenience; consider encapsulating later.
     */
    public boolean enteredPortal;

    /**
     * Possible effects that usable items can apply.
     */
    public enum ItemEffect {
        /** Restore health. */
        HEAL,
        /** Deal damage to this entity. */
        DAMAGE,
        /** Increase mana. */
        STATUP_MANA,
        /** Increase strength. */
        STATUP_STRENGTH,
        /** Increase charisma. */
        STATUP_CHARISMA,
        /** Increase defense. */
        STATUP_DEFENSE,
        /** No effect / unknown item. */
        DEFAULT
    }

    /**
     * Inventory map: item name → quantity held.
     * Quantities of zero are removed from the map.
     */
    protected Map<String, Integer> inv = new HashMap<>();

    /**
     * Creates a new entity at full health with every core stat set to
     * {@code baseStat}.
     *
     * @param maxHealth maximum (and starting) health
     * @param baseStat  initial value for mana, strength, charisma and defense
     */
    public BaseEntity(double maxHealth, double baseStat) {
        this.baseStat = baseStat;
        this.maxHealth = maxHealth;
        this.healthPoints = maxHealth;
        this.mana = baseStat;
        this.strength = baseStat;
        this.charisma = baseStat;
        this.defense = baseStat;
    }

    /**
     * Returns the entity's current health points.
     *
     * @return current health (never negative, never above {@code maxHealth})
     */
    public double getHealthPoints() {
        return healthPoints;
    }

    /**
     * Reduces the entity's health by the given amount.
     * Health will not fall below zero.
     *
     * @param dmg amount of damage to apply
     */
    public void damage(double dmg) {
        healthPoints = Math.max(0, healthPoints - dmg);
    }

    /**
     * Increases the entity's health by the given amount.
     * Health will not exceed {@code maxHealth}.
     *
     * @param hp amount of health to restore
     */
    public void heal(double hp) {
        healthPoints = Math.min(maxHealth, healthPoints + hp);
    }

    /**
     * Deals damage to another entity.
     *
     * @param dmg    amount of damage to deal
     * @param target the entity that will receive the damage (must not be null)
     */
    public void attack(double dmg, Entity target) {
        target.damage(dmg);
    }

    /**
     * Adds (or increases) an item in the inventory.
     * <p>
     * If the item already exists, the quantities are summed.
     * If it does not exist, it is inserted with the given quantity.
     * </p>
     *
     * @param iName name of the item
     * @param amt   quantity to add (should be positive)
     */
    public void itemGain(String iName, int amt) {
        if (inv.containsKey(iName)) {
            inv.merge(iName, amt, Integer::sum);
        } else {
            inv.put(iName, amt);
        }
    }

    /**
     * @return The Entity's stats.
     */
    public double[] getStats() { return new double[]{mana, strength, charisma, defense}; }

    /**
     * Removes a quantity of an item from the inventory.
     * <p>
     * If the remaining quantity reaches zero the entry is removed from the map.
     * Throws {@link RuntimeException} if the item is missing or the entity
     * does not hold enough of it.
     * </p>
     *
     * @param iName name of the item
     * @param amt   quantity to remove
     * @throws RuntimeException if the item is missing or quantity is insufficient
     */
    public void itemLoss(String iName, int amt) {
        if (!inv.containsKey(iName) || inv.get(iName) < amt) {
            throw new RuntimeException("Invalid item amount");
        }

        inv.compute(iName, (k, currentAmt) -> {
            int remaining = currentAmt - amt;
            return remaining == 0 ? null : remaining;   // remove key when zero
        });
    }

    /**
     * Returns the live inventory map (not a defensive copy).
     * Callers that mutate the returned map will affect this entity.
     *
     * @return the inventory map (item name → quantity)
     */
    public Map<String, Integer> getInventory() {
        return inv;
    }

    /**
     * Uses a quantity of an item and applies its effect.
     * <p>
     * The item is first deducted from inventory. Then the effect is looked up
     * in the supplied {@code UsableImap}. Supported effects are defined by
     * {@link ItemEffect}.
     * </p>
     *
     * @param UsableImap map of item name → effect
     * @param iName      name of the item to use
     * @param amtU       quantity to consume
     * @param amtG       magnitude of the effect (heal amount, damage, stat boost, etc.)
     * @throws RuntimeException if the item is missing or quantity is insufficient
     */
    public void itemUse(HashMap<String, ItemEffect> UsableImap, String iName, int amtU, double amtG) {
        if (!inv.containsKey(iName) || inv.get(iName) < amtU) {
            throw new RuntimeException("Item not found or insufficient quantity");
        }

        // Deduct the used quantity and remove the key if it reaches zero
        inv.compute(iName, (k, currentAmount) -> {
            int remaining = currentAmount - amtU;
            return remaining == 0 ? null : remaining;
        });

        switch (UsableImap.getOrDefault(iName, ItemEffect.DEFAULT)) {
            case HEAL -> heal(amtG);
            case DAMAGE -> damage(amtG);
            case STATUP_MANA -> mana += amtG;
            case STATUP_CHARISMA -> charisma += amtG;
            case STATUP_DEFENSE -> defense += amtG;
            case STATUP_STRENGTH -> strength += amtG;
            default -> System.out.println("Unknown item/use case: " + iName);
        }
    }
}