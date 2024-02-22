package game.engine.interfaces;

// Interface containing the methods available to all objects that gets attacked within the
// game.

public interface Attackee {
	public int getCurrentHealth(); // a method to retrieve the current Health
	public void setCurrentHealth(int health); // set the current health
	public int getResourcesValue(); // get the resources
}
