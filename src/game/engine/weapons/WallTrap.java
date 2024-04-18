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
			Titan titan = laneTitans.poll();
			if (titan.getDistance() == 0) {
				int resources = titan.takeDamage(super.getDamage());
				if (resources == 0) {
					laneTitans.add(titan);
				}
				return resources;
			} else {
				laneTitans.add(titan);
			}
		}
		return 0;
	}

}
