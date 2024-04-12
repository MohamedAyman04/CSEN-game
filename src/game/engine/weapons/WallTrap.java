package game.engine.weapons;

import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class WallTrap extends Weapon
{
	public static final int WEAPON_CODE = 4;

	public WallTrap(int baseDamage)
	{
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		if (!laneTitans.isEmpty()) {
			int resourcesSoFar = 0;
			Titan titan = laneTitans.poll();
			if (titan.getDistance() == 0) {
				int newHealth = titan.getCurrentHealth() - super.getDamage();
				if (newHealth <= 0) {
					resourcesSoFar += titan.getResourcesValue();
				} else {
					titan.setCurrentHealth(newHealth);
					laneTitans.add(titan);
				}
			} else {
				laneTitans.add(titan);
			}
			return resourcesSoFar;
		}
		return 0;
	}

}
