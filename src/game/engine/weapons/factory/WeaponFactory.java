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
		if (resources <= weaponRegistry.getPrice()) {
			throw new InsufficientResourcesException(resources);
		} else {
			resources -= weaponRegistry.getPrice();
			switch (weaponCode) {
				case 1:
					PiercingCannon piercingCannon = new PiercingCannon(weaponRegistry.getDamage());
					return new FactoryResponse(piercingCannon, resources);
				case 2:
					SniperCannon sniperCannon = new SniperCannon(weaponRegistry.getDamage());
					return new FactoryResponse(sniperCannon, resources);
				case 3:
					VolleySpreadCannon volleySpreadCannon = new VolleySpreadCannon(weaponRegistry.getDamage(), weaponRegistry.getMinRange(), weaponRegistry.getMaxRange());
					return new FactoryResponse(volleySpreadCannon, resources);
				default:
					WallTrap wallTrap = new WallTrap(weaponRegistry.getDamage());
					return new FactoryResponse(wallTrap, resources);
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
