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

	void addTitan(Titan titan){titans.add(titan);}
	void addWeapon(Weapon weapon){weapons.addLast(weapon);}

	void moveLaneTitans() {
		PriorityQueue<Titan> temp = null;
		while (!titans.isEmpty()) {
			Titan t = titans.remove();
			if (t.getDistance() != 0)
				t.move();
			temp.add(t);
		}
		while(!temp.isEmpty())
			titans.add(temp.remove());
	}
	int performLaneTitansAttacks(){
		PriorityQueue<Titan> temp2 = null;
		int sum=0;
		while (!titans.isEmpty()) {
			Titan t2 = titans.remove();
			if (t2.getDistance() == 0)
				sum=sum+t2.attack(laneWall);
			temp2.add(t2);
		}
		while(!temp2.isEmpty())
			titans.add(temp2.remove());
		return sum;
	}
	int performLaneWeaponsAttacks(){
		ArrayList<Weapon> temp3 = null;
		int sum2=0;
		while (!weapons.isEmpty()) {
			Weapon t3 = weapons.removeLast();
			sum2=sum2+t3.attack(laneWall);
			temp3.addLast(t3);
		}
		while(!temp3.isEmpty())
			weapons.addLast(temp3.removeLast());
		return sum2;
	}

	boolean isLaneLost(){
		if(laneWall.isDefeated()){return true;}
		return false;
    }

	void updateLaneDangerLevel(){
		PriorityQueue<Titan> temp4 = null;
		int sum3=0;
		while (!titans.isEmpty()) {
			Titan t4 = titans.remove();
			sum3=sum3+t4.getDangerLevel();
			temp4.add(t4);
		}
		while(!temp4.isEmpty())
			titans.add(temp4.remove());

		dangerLevel=sum3;
	}

}
