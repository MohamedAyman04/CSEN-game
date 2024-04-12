package game.engine.interfaces;

public interface Attacker
{
	int getDamage(); // gets the damage value to be applied

	default int attack(Attackee target) {
		target.takeDamage(getDamage());
		if (target.isDefeated()) {
			return target.getResourcesValue();
		}
		else
			return 0;
	}

}
