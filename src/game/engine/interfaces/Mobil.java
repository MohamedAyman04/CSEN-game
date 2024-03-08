package game.engine.interfaces;

// Interface containing the methods available to all objects that has mobility (i.e can move)
// within the game

public interface Mobil {
	int getDistance(); // a getter method to get the distances
	void setDistance(int distance); // set the distance
	int getSpeed(); // get the speed value
	void setSpeed(int speed); // set the speed value
}
