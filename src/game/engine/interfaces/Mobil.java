package game.engine.interfaces;

// Interface containing the methods available to all objects that has mobility (i.e can move)
// within the game

public interface Mobil {
	public int getDistance(); // a getter method to get the distances
	public void setDistance(int distance); // set the distance
	public int getSpeed(); // get the speed value
	public void setSpeed(int speed); // set the speed value
}
