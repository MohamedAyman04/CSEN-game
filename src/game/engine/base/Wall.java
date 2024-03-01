package game.engine.base;

import game.engine.interfaces.Attackee;

public class Wall implements Attackee {
    private final int baseHealth;
    private int currentHealth;
    public Wall(int baseHealth){
        this.baseHealth=baseHealth;
        this.currentHealth=baseHealth;
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, currentHealth);
    }

    @Override
    public int getResourcesValue() {
        return -1;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }
}
