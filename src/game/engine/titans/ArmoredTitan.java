package game.engine.titans;

public class ArmoredTitan extends Titan{
    private final int TITAN_CODE=3;
    public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,int speed, int resourcesValue, int dangerLevel){
        super(baseHealth,baseDamage,heightInMeters,distanceFromBase,speed,resourcesValue,dangerLevel);
    }

    @Override
    public void setCurrentHealth(int health) {

    }

    @Override
    public void setDistance(int distance) {

    }

    @Override
    public void setSpeed(int speed) {

    }
}
