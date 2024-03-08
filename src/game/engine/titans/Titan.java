package game.engine.titans;

// imports
import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;

// A class representing the Titans available in the game. A titan class can do the following
// functionalities

public abstract class Titan implements Attackee, Attacker, Mobil, Comparable<Titan> {
	private final int baseHealth;
	private int currentHealth;
	private final int baseDamage;
	private final int heightInMeters;
	private int distanceFromBase;
	private int speed;
	private final int resourcesValue;
	private final int dangerLevel;
	
	public Titan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed, int resourcesValue, int dangerLevel)
	{
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.distanceFromBase = distanceFromBase;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel; 
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int health) {
        currentHealth = Math.max(health, 0);
	}

	public int getResourcesValue() {
		return resourcesValue;
	}

	public int getDamage() {
		return baseDamage;
	}

	public int getDistance() {
		return distanceFromBase;
	}
	public void setDistance(int distance) {

		distanceFromBase = Math.max(0, distance);
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = Math.max(0, speed);
	}

	public int compareTo(Titan o) {
		return this.distanceFromBase - o.distanceFromBase;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	public int getHeightInMeters() {
		return heightInMeters;
	}

	public int getBaseHealth() {
		return baseHealth;
	}
}
