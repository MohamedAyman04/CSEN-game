package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

public class Lane implements Comparable<Lane>
{
	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall)
	{
		super();
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<>();
		this.weapons = new ArrayList<>();
	}

	public Wall getLaneWall()
	{
		return this.laneWall;
	}

	public int getDangerLevel()
	{
		return this.dangerLevel;
	}

	public void setDangerLevel(int dangerLevel)
	{
		this.dangerLevel = dangerLevel;
	}

	public PriorityQueue<Titan> getTitans()
	{
		return this.titans;
	}

	public ArrayList<Weapon> getWeapons()
	{
		return this.weapons;
	}

	@Override
	public int compareTo(Lane o)
	{
		return this.dangerLevel - o.dangerLevel;
	}

	public void addTitan(Titan titan){titans.add(titan);}
	public void addWeapon(Weapon weapon){weapons.addLast(weapon);}

	public void moveLaneTitans() {
		PriorityQueue<Titan> temp = new PriorityQueue<>();
		while (!titans.isEmpty()) {
			Titan t = titans.remove();
			if (!t.hasReachedTarget()) {
				t.move();
			}
			temp.add(t);
		}
		while(!temp.isEmpty())
			titans.add(temp.remove());
	}
	public int performLaneTitansAttacks() {
		PriorityQueue<Titan> temp2 = new PriorityQueue<>();
		int sum = 0;
		while (!titans.isEmpty()) {
			Titan t2 = titans.remove();
			if (t2.hasReachedTarget())
				sum += t2.attack(laneWall);
			temp2.add(t2);
		}
		while(!temp2.isEmpty())
			titans.add(temp2.remove());
		return sum;
	}
	public int performLaneWeaponsAttacks() {
		int resourcesSoFar = 0;
        for (Weapon weapon : weapons) {
            resourcesSoFar += weapon.turnAttack(titans);
        }
		return resourcesSoFar;
	}

	public boolean isLaneLost(){
        return laneWall.isDefeated();
    }

	public void updateLaneDangerLevel(){
		PriorityQueue<Titan> temp4 = new PriorityQueue<>();
		int sum3 = 0;
		while (!titans.isEmpty()) {
			Titan t4 = titans.remove();
			sum3 += t4.getDangerLevel();
			temp4.add(t4);
		}
		while(!temp4.isEmpty())
			titans.add(temp4.remove());
		dangerLevel = sum3;
	}

}
