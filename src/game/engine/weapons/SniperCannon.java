package game.engine.weapons;

import game.engine.interfaces.Attacker;
import game.engine.titans.Titan;

import java.util.PriorityQueue;

public class SniperCannon extends Weapon
{
	public static final int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage)
	{
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		if (!laneTitans.isEmpty()) {
			Titan titan = laneTitans.poll();
			int newHealth = titan.getCurrentHealth() - super.getDamage();
			if (newHealth <= 0) {
				return titan.getResourcesValue();
			} else {
				titan.setCurrentHealth(newHealth);
				laneTitans.add(titan);
			}
		}
		return 0;
	}

}
