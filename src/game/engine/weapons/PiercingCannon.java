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

		for (int i=0; !laneTitans.isEmpty() && i<5 ; i++) {
			arr[i] = laneTitans.poll();
		}

        for (int i=0; i<arr.length && arr[i] != null; i++) {
				int resources = arr[i].takeDamage(super.getDamage());
				if (resources == 0) {
					laneTitans.add(arr[i]);
				}
				resourcesSoFar += resources;
        }

		return resourcesSoFar;
	}

}
