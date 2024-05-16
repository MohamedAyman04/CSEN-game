package game.gui;

import game.engine.Battle;
import game.engine.BattlePhase;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.weapons.WeaponRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Model {
    private final int numberLanes;
    private final Battle battle;

    public Model (int numLanes, int initialResources) throws IOException {
        numberLanes = numLanes;
        battle = new Battle(1, 0, 700, numLanes, initialResources);
    }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return battle.getWeaponFactory().getWeaponShop();
    }

    public void pass() {
        battle.passTurn();
    }

    public PriorityQueue<Lane> getLanes() {
        return battle.getLanes();
    }

    public int getScore() {
        return battle.getScore();
    }

    public int getCurrentResources() {
        return battle.getResourcesGathered();
    }

    public BattlePhase getPhase() {
        return battle.getBattlePhase();
    }

    public int getCurrentTurn() {
        return battle.getNumberOfTurns();
    }

    public int getNumLanes() {
        return numberLanes;
    }

    public int getTitanSpawnDistance() {
        return battle.getTitanSpawnDistance();
    }

    public ArrayList<Integer> DangerLevel() {
        PriorityQueue<Lane> temp = new PriorityQueue<>(battle.getLanes());
        ArrayList<Integer> lanesDangerLevel = new ArrayList<>();
        while (!temp.isEmpty()) {
            Lane lane = temp.poll();
            lanesDangerLevel.add(lane.getDangerLevel());
        }
        return lanesDangerLevel;
    }

    public void purchaseWeapon(int code, Lane lane) throws InvalidLaneException, InsufficientResourcesException {
        battle.purchaseWeapon(code, lane);
    }
}
