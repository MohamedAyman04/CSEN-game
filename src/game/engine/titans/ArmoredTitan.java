package game.engine.titans;

import game.engine.interfaces.Attackee;

public class ArmoredTitan extends Titan
{
	public static final int TITAN_CODE = 3;

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel)
	{
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	public int takeDamage(int damage) {
		if(this.getCurrentHealth()-(0.25*damage)<=0){
			return this.getResourcesValue();
		}
		return 0;
	}


}
