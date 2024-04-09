package game.engine.interfaces;

public interface Mobil
{
	int getDistance();

	void setDistance(int distance);

	int getSpeed();

	void setSpeed(int speed);

	default boolean hasReachedTarget() {
		return false;
	}

	default boolean move() {
		return false;
	}
}
