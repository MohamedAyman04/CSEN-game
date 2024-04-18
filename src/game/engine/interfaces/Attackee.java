package game.engine.interfaces;

public interface Attackee
{
	int getCurrentHealth();

	void setCurrentHealth(int health);

	int getResourcesValue();
	default int takeDamage(int damage) {
		if(getCurrentHealth() - damage <= 0) {
			setCurrentHealth(0);
			return getResourcesValue();
		}
		setCurrentHealth(getCurrentHealth() - damage);
		return 0;
	}
	default boolean isDefeated() {
		return getCurrentHealth() <= 0;
	}
}
