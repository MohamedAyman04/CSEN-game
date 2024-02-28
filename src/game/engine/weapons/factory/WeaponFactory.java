package game.engine.weapons.factory;
import game.engine.weapons.WeaponRegistry;
import java.util.HashMap;
import java.io.*;
import static game.engine.dataloader.DataLoader.readWeaponRegistry;


public class WeaponFactory {
   private final HashMap<Integer, WeaponRegistry> weaponShop;
   public WeaponFactory() throws IOException {
        this.weaponShop = readWeaponRegistry();
   }

    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }
}
