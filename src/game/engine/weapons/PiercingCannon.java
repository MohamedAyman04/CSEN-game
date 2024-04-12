package game.engine.weapons;

import game.engine.titans.Titan;
import java.util.PriorityQueue;

public class PiercingCannon extends Weapon
{
	public static final int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage)
	{
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		Titan[] arr = new Titan[5];
		int resourcesSoFar = 0;

		for (int i=0; i<5 && !laneTitans.isEmpty(); i++) {
			arr[i] = laneTitans.poll();
		}

		for (int i=0; i<5; i++) {
            int newHealth = arr[i].getCurrentHealth() - super.getDamage();
			if (newHealth <= 0) {
				resourcesSoFar += arr[i].getResourcesValue();
			} else {
				arr[i].setCurrentHealth(newHealth);
				laneTitans.add(arr[i]);
			}
		}

		return resourcesSoFar;
	}

}
