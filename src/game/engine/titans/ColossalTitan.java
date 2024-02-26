package game.engine.titans;

public class ColossalTitan extends Titan{
    private final int TITAN_CODE=4;
    public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,int speed, int resourcesValue, int dangerLevel){
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
