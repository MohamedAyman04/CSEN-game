package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;
import game.engine.exceptions.InsufficientResourcesException;

import game.engine.dataloader.DataLoader;
import game.engine.weapons.*;

public class WeaponFactory
{
	private final HashMap<Integer, WeaponRegistry> weaponShop;

	public WeaponFactory() throws IOException
	{
		super();
		weaponShop = DataLoader.readWeaponRegistry();
	}

	public HashMap<Integer, WeaponRegistry> getWeaponShop()
	{
		return weaponShop;
	}

	public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException {
		WeaponRegistry weaponRegistry = weaponShop.get(weaponCode);
		if (resources < weaponRegistry.getPrice()) {
			throw new InsufficientResourcesException(resources);
		} else {
			resources -= weaponRegistry.getPrice();
            switch (weaponCode) {
                case 1:
                	return new FactoryResponse(new PiercingCannon(weaponRegistry.getDamage()), resources);
                case 2:
                	return new FactoryResponse(new SniperCannon(weaponRegistry.getDamage()), resources);
                case 3:
                    return new FactoryResponse(new VolleySpreadCannon(weaponRegistry.getDamage(), weaponRegistry.getMinRange(), weaponRegistry.getMaxRange()), resources);
                case 4:
                	return new FactoryResponse(new WallTrap(weaponRegistry.getDamage()), resources);
                default: 
                	return null;
            }
		}
	}

	public void addWeaponToShop(int code, int price) {
		WeaponRegistry weaponRegistry = new WeaponRegistry(code, price);
		weaponShop.put(code, weaponRegistry);
	}

	public void addWeaponToShop(int code, int price, int damage, String name) {
		WeaponRegistry weaponRegistry = new WeaponRegistry(code, price, damage, name);
		weaponShop.put(code, weaponRegistry);
	}

	public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int maxRange) {
		WeaponRegistry weaponRegistry = new WeaponRegistry(code, price, damage, name, minRange, maxRange);
		weaponShop.put(code, weaponRegistry);
	}
}
