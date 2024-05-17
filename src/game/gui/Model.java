package game.gui;

import game.engine.Battle;
import game.engine.BattlePhase;

import game.engine.exceptions.InsufficientResourcesException;

import game.engine.exceptions.InvalidLaneException;

import game.engine.lanes.Lane;
import game.engine.weapons.WeaponRegistry;
import game.engine.weapons.factory.FactoryResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private final Battle battle;

    public Model (int numLanes, int initialResources) throws IOException {
        battle = new Battle(1, 0, 100, numLanes, initialResources);
    }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return battle.getWeaponFactory().getWeaponShop();
    }

    public void pass() {
        battle.passTurn();
    }

    public ArrayList<Lane> getLanes() {
        return battle.getOriginalLanes();
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

    public int getTitanSpawnDistance() {
        return battle.getTitanSpawnDistance();
    }

    public FactoryResponse buyWeapon(int res , int code) throws InsufficientResourcesException {return battle.getWeaponFactory().buyWeapon(res,code) ;}

    public ArrayList<Integer> DangerLevel() {
        ArrayList<Lane> temp = new ArrayList<>(battle.getOriginalLanes());
        ArrayList<Integer> lanesDangerLevel = new ArrayList<>();
        for (Lane lane: temp) {
            lanesDangerLevel.add(lane.getDangerLevel());
        }
        return lanesDangerLevel;
    }

    public void purchaseWeapon(int code, Lane lane) throws InvalidLaneException, InsufficientResourcesException {
        battle.purchaseWeapon(code, lane);
    }

    public boolean isGameOver() {
        return battle.isGameOver();
    }

}
