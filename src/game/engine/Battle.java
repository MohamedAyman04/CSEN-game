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

public class Battle {
	private static final int[][] PHASES_APPROACHING_TITANS =
			{
					{1, 1, 1, 2, 1, 3, 4},
					{2, 2, 2, 1, 3, 3, 4},
					{4, 4, 4, 4, 4, 4, 4}
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
				  int initialResourcesPerLane) throws IOException {
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

	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered() {
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered) {
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase() {
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn() {
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTitanSpawnDistance() {
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance) {
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives() {
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans() {
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes() {
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes() {
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes) {
		for (int i = 0; i < numOfLanes; i++) {
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}

	public void refillApproachingTitans() {
		switch (this.getBattlePhase()) {
			case EARLY -> {
				for (int i = 0; i < PHASES_APPROACHING_TITANS[0].length; i++) {
					int titan_code = PHASES_APPROACHING_TITANS[0][i];
					TitanRegistry t = titansArchives.get(titan_code);
					approachingTitans.add(t.spawnTitan(titanSpawnDistance));
				}
			}
			case INTENSE -> {
				for (int i = 0; i < PHASES_APPROACHING_TITANS[1].length; i++) {
					int titan_code = PHASES_APPROACHING_TITANS[1][i];
					TitanRegistry t = titansArchives.get(titan_code);
					approachingTitans.add(t.spawnTitan(titanSpawnDistance));
				}
			}
			case GRUMBLING -> {
				for (int i = 0; i < PHASES_APPROACHING_TITANS[2].length; i++) {
					int titan_code = PHASES_APPROACHING_TITANS[2][i];
					TitanRegistry t = titansArchives.get(titan_code);
					approachingTitans.add(t.spawnTitan(titanSpawnDistance));
				}
			}
		}
	}

	public boolean isGameOver() {
		return this.getLanes().isEmpty();
	}

	private void moveTitans() {
		PriorityQueue<Lane> l = new PriorityQueue<>();
		while (!lanes.isEmpty()) {
			Lane lane = lanes.poll();
			lane.moveLaneTitans();
			l.add(lane);
		}
		while(!l.isEmpty()) {
			lanes.add(l.poll());
		}
	}

	private int performTitansAttacks() {
		int totalResources = 0;
		PriorityQueue<Lane> l = new PriorityQueue<>();
		while (!lanes.isEmpty()) {
			Lane lane = lanes.poll();
			int res = lane.performLaneTitansAttacks();
			if (!lane.isLaneLost()) {
				l.add(lane);
			}
			totalResources += res;
		}
		while (!l.isEmpty()) {
			lanes.add(l.poll());
		}
		return totalResources;
	}

	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException {
		if (!lane.isLaneLost() && lanes.contains(lane) && originalLanes.contains(lane)) {
			FactoryResponse factoryResponse = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
			lane.addWeapon(factoryResponse.getWeapon());
			resourcesGathered = factoryResponse.getRemainingResources();
			performTurn();
		} else {
			throw new InvalidLaneException();
		}
	}

	private int performWeaponsAttacks() {
		PriorityQueue<Lane> l = new PriorityQueue<>();
		int totalResources = 0;
		while (!lanes.isEmpty()) {
			Lane lane = lanes.poll();
			totalResources += lane.performLaneWeaponsAttacks();
			l.add(lane);
		}
		while (!l.isEmpty()) {
			lanes.add(l.poll());
		}
		resourcesGathered += totalResources;
		score += totalResources;
		return totalResources;
	}

	private void performTurn() {
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
	}

	public void passTurn() {
		performTurn();
	}

	private void addTurnTitansToLane() {
		if (!lanes.isEmpty()) {
			Lane lane = lanes.poll();
			for (int i=0; i<numberOfTitansPerTurn; i++) {
				if (approachingTitans.isEmpty()) {
					refillApproachingTitans();
				}
				lane.addTitan(approachingTitans.removeFirst());
			}
			lanes.add(lane);
		}
	}

	private void updateLanesDangerLevels() {
		PriorityQueue<Lane> tempLanes = new PriorityQueue<>();
		while (!lanes.isEmpty()) {
			Lane tempLane = lanes.poll();
			tempLane.updateLaneDangerLevel();
			tempLanes.add(tempLane);
		}
		while (!tempLanes.isEmpty()) {
			lanes.add(tempLanes.poll());
		}
	}

	private void finalizeTurns() {
		numberOfTurns++;
		if (numberOfTurns < 15)
			battlePhase = BattlePhase.EARLY;

		if (numberOfTurns < 30 && numberOfTurns >= 15)
			battlePhase = BattlePhase.INTENSE;

		if (numberOfTurns >= 30)
			battlePhase = BattlePhase.GRUMBLING;

		if (numberOfTurns > 30 && numberOfTurns % 5 == 0)
			numberOfTitansPerTurn *= 2;
	}
}