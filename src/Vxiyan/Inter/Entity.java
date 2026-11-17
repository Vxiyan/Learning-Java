package Vxiyan.Inter;

import java.util.Map;

public interface Entity {
    double getHealthPoints();
    void damage(double dmg);
    void heal(double hp);
    void itemGain(String iName, int amt);
    void itemLoss(String iName, int amt);
    void attack(double dmg, Entity name);
    Map getInventory();
    }
