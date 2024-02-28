package game.engine.weapons.factory;
import game.engine.weapons.WeaponRegistry;
import java.util.HashMap;
import java.io.*;



public class WeaponFactory {
   private final HashMap<Integer, WeaponRegistry> weaponShop;
   public WeaponFactory() throws IOException {
        this.weaponShop = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("weapons.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                int[] data = new int[words.length];
                for (int i=0; i<words.length; i++) {
                    data[i] = Integer.parseInt(words[i]);
                }

                WeaponRegistry weaponRegistry = new WeaponRegistry(data[0], data[1], data[2],String.valueOf(data[3]), data[4], data[5]);
                weaponShop.put(data[0], weaponRegistry);
            }
            reader.close();
            } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public HashMap<Integer, WeaponRegistry> getWeaponShop() {
        return weaponShop;
    }
}
