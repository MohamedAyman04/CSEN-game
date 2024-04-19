package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

public class Battle
{
	private static final int[][] PHASES_APPROACHING_TITANS =
	{
		{ 1, 1, 1, 2, 1, 3, 4 },
		{ 2, 2, 2, 1, 3, 3, 4 },
		{ 4, 4, 4, 4, 4, 4, 4 } 
	}; // order of the types of titans (codes) during each phase
	private static final int WALL_BASE_HEALTH = 10000;

	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn; // initially equals to 1
	private int score; // Number of Enemies Killed
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans; // treated as a Queue
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException
	{
		super();
		this.numberOfTurns = numberOfTurns;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		this.initializeLanes(initialNumOfLanes);
	}

	public int getNumberOfTurns()
	{
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns)
	{
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered()
	{
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered)
	{
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase()
	{
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase)
	{
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn()
	{
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn)
	{
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getTitanSpawnDistance()
	{
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance)
	{
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory()
	{
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives()
	{
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans()
	{
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes()
	{
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes()
	{
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes)
	{
		for (int i = 0; i < numOfLanes; i++)
		{
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}
	public void refillApproachingTitans(){
		switch (this.getBattlePhase()){
			case EARLY -> {
				for (int i = 0; i < PHASES_APPROACHING_TITANS[0].length; i++) {
					int titan_code=PHASES_APPROACHING_TITANS[0][i];
					TitanRegistry t = titansArchives.get(titan_code);
					if(titan_code==1){
						approachingTitans.add(i, new PureTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					}
					else if (titan_code==2){
						approachingTitans.add(i, new AbnormalTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					} else if (titan_code==3) {
						approachingTitans.add(i, new ArmoredTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					}
					else{
						approachingTitans.add(i, new ColossalTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					}
				}
			}
			case INTENSE -> {
				for (int i = 0; i < PHASES_APPROACHING_TITANS[1].length; i++) {
					int titan_code=PHASES_APPROACHING_TITANS[1][i];
					TitanRegistry t = titansArchives.get(titan_code);
					if(titan_code==1){
						approachingTitans.add(i, new PureTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					}
					else if (titan_code==2){
						approachingTitans.add(i, new AbnormalTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					} else if (titan_code==3) {
						approachingTitans.add(i, new ArmoredTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					}
					else{
						approachingTitans.add(i, new ColossalTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
					}
				}
			}
			case GRUMBLING -> {
				for (int i = 0; i < PHASES_APPROACHING_TITANS[2].length; i++) {
					int titan_code=PHASES_APPROACHING_TITANS[2][i];
					TitanRegistry t = titansArchives.get(titan_code);
					approachingTitans.add(i, new ColossalTitan(t.getBaseHealth(),t.getBaseDamage(),t.getHeightInMeters(),titanSpawnDistance,t.getSpeed(),t.getResourcesValue(),t.getDangerLevel()));
				}
			}
		}
	}
	public boolean isGameOver(){
		if(this.getLanes().isEmpty()){
			return true;
		}
		return false;
	}
	private void moveTitans(){
		approachingTitans.forEach(titan -> {
			if(!titan.hasReachedTarget()){
				titan.move();
			}
		});
	}
//	private int performTitansAttacks(){
//
//		approachingTitans.forEach(titan -> {
//
//		});
//	}
	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
			FactoryResponse factoryResponse = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
			resourcesGathered = factoryResponse.getRemainingResources();
			if (lane.isLaneLost()) {
				throw new InvalidLaneException();
			} else {
				lane.addWeapon(factoryResponse.getWeapon());
			}
	}

}
