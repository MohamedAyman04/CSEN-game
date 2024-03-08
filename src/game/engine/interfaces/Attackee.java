package game.engine.interfaces;

// Interface containing the methods available to all objects that gets attacked within the
// game.

public interface Attackee {
	int getCurrentHealth(); // a method to retrieve the current Health
	void setCurrentHealth(int health); // set the current health
	int getResourcesValue(); // get the resources
}
