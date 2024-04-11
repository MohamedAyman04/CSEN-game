package game.engine.titans;

public class TitanRegistry // For storing the titan's information from the csv file read in the data loader
{
	private final int code;
	private int baseHealth;
	private int baseDamage;
	private int heightInMeters;
	private int speed; // distance moved per turn
	private int resourcesValue; // resources gained by defeating it
	private int dangerLevel;

	public TitanRegistry(int code, int baseHealth, int baseDamage, int heightInMeters, int speed, int resourcesValue,
			int dangerLevel)
	{
		super();
		this.code = code;
		this.baseHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

	public int getCode()
	{
		return code;
	}

	public int getBaseHealth()
	{
		return baseHealth;
	}

	public int getBaseDamage()
	{
		return baseDamage;
	}

	public int getHeightInMeters()
	{
		return heightInMeters;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getResourcesValue()
	{
		return resourcesValue;
	}

	public int getDangerLevel()
	{
		return dangerLevel;
	}
	public Titan spawnTitan(int distanceFromBase){
		PureTitan P = new PureTitan(100,15,15,distanceFromBase,10,10,1);
		AbnormalTitan ABT = new AbnormalTitan(100,20,10,distanceFromBase,15,15,2);
		ArmoredTitan ART = new ArmoredTitan(200,85,15,distanceFromBase,10,30,3);
		ColossalTitan C = new ColossalTitan(1000,100,60,distanceFromBase,5,60,4);
		switch (code){
			case 1: return P ;
			case 2: return ABT;
			case 3: return ART;
			case 4: return C;
		}
		return null;
	}

}
