package Vxiyan.CritClasses;

public class Monster extends BaseEntity {
    String monsterName;
    public Monster(double maxHealth, double baseStat, String monsterName) {
        super(maxHealth, baseStat);
        this.monsterName = monsterName;

    }

    /**
     *
     * @return {@code monsterName}
     */
     public String getMonsterName() { return monsterName; }
}
