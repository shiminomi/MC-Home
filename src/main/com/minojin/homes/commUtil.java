package com.minojin.homes;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class commUtil {
	private MinoHome instance;
	
	public commUtil(MinoHome instance) {
		this.instance = instance;
	}
	
	public int getHomes(Player p) {
		String pID = p.getUniqueId().toString();
		if(!instance.homes.contains(pID)) {
			return 0;
		}
		
		return instance.homes.getConfigurationSection(pID).getKeys(false).size();
	}

	public boolean homeExists(Player p, String name) {
		String pID = p.getUniqueId().toString();
		
		return instance.homes.contains(pID + "." + name);
	}
	
	public void setHome(Player p, String name) {
		String pID = p.getUniqueId().toString();
		
        instance.homes.set(pID + "." + name + ".X", p.getLocation().getX());
        instance.homes.set(pID + "." + name + ".Y", p.getLocation().getY());
        instance.homes.set(pID + "." + name + ".Z", p.getLocation().getZ());
        instance.homes.set(pID + "." + name + ".Yaw", p.getLocation().getYaw());
        instance.homes.set(pID + "." + name + ".Pitch", p.getLocation().getPitch());
        instance.homes.set(pID + "." + name + ".World", p.getLocation().getWorld().getName());
        instance.saveHomes();
    }
	
	public void delHome(Player p, String name) {
		String pID = p.getUniqueId().toString();
		
		instance.homes.set(pID + "." + name + ".X", null);
        instance.homes.set(pID + "." + name + ".Y", null);
        instance.homes.set(pID + "." + name + ".Z", null);
        instance.homes.set(pID + "." + name + ".Yaw", null);
        instance.homes.set(pID + "." + name + ".Pitch", null);
        instance.homes.set(pID + "." + name + ".World", null);
        instance.homes.set(pID + "." + name, null);
        instance.saveHomes();
	}
	
	public void home(Player p, String name) {
		String pID = p.getUniqueId().toString();

        Location home = new Location(
                Bukkit.getWorld(
                instance.homes.getString(pID + "." + name + ".World"))
                , instance.homes.getDouble(pID + "." + name + ".X")
                , instance.homes.getDouble(pID + "." + name + ".Y")
                , instance.homes.getDouble(pID + "." + name + ".Z")
                , instance.homes.getLong(pID + "." + name + ".Yaw")
                , instance.homes.getLong(pID + "." + name + ".Pitch")
        );
        
        p.teleport(home);
	}
	
	public Set<String> homes(Player p) {
		String pID = p.getUniqueId().toString();
		
		return instance.homes.getConfigurationSection(pID).getKeys(false);
	}
}