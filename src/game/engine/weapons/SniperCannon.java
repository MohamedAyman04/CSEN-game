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
			int resources = titan.takeDamage(super.getDamage());
			if (!titan.isDefeated()) {
				laneTitans.add(titan);
			}
			return resources;
		}
		return 0;
	}

}
