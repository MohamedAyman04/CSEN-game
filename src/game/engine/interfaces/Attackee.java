package game.engine.interfaces;

public interface Attackee
{
	int getCurrentHealth();

	void setCurrentHealth(int health);

	int getResourcesValue();
	default int takeDamage(int damage) {
		if(getCurrentHealth()-damage<=0){
			return getResourcesValue();
		}
		return 0;
	}
	default boolean isDefeated() {
		return getCurrentHealth()<=0;
	}
}
