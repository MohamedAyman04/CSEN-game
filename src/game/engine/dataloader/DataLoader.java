package game.engine.dataloader;

import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;
import java.io.*;
import java.util.HashMap;

public class DataLoader {
    private static final String TITANS_FILE_NAME = "titans.csv";
    private static final String WEAPONS_FILE_NAME = "weapons.csv";

    // reading from files technique used from reference https://youtu.be/ScUJx4aWRi0?si=Ko1Rk6kDPgGnAfmh

    public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException {
        HashMap<Integer, TitanRegistry> hash = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(TITANS_FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                int[] data = new int[words.length];
                for (int i=0; i<words.length; i++) {
                    data[i] = Integer.parseInt(words[i]);
                }
                TitanRegistry titan = new TitanRegistry(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                hash.put(data[0], titan);
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return hash;
    }

    public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException {
        HashMap<Integer, WeaponRegistry> hash = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                int[] data = new int[words.length];
                for (int i=0; i<words.length; i++) {
                    if (i != 3)
                        data[i] = Integer.parseInt(words[i]);
                }
                WeaponRegistry weapon;
                if (data[0] == 3) {
                    weapon = new WeaponRegistry(data[0], data[1], data[2], words[3], data[4], data[5]);
                } else {
                    weapon = new WeaponRegistry(data[0], data[1], data[2], words[3]);
                }
                hash.put(data[0], weapon);
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return hash;
    }
}
