package game.engine.weapons;

public class VolleySpreadCannon extends Weapon {
    private final int minRange;
    private final int maxRange;

    public VolleySpreadCannon(int baseDamage,int minRange,int maxRange) {
        super(baseDamage);
        this.minRange=minRange;
        this.maxRange=maxRange;
    }
    public int getMinRange() {
        return minRange;
    }
    public int getMaxRange() {
        return maxRange;
    }
}
