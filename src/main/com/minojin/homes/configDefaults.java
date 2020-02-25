package com.minojin.homes;
import org.bukkit.configuration.file.FileConfiguration;

public class configDefaults {
	public static void setDefaults(FileConfiguration config) {
		config.addDefault("homeLimit", 2);
		config.options().copyDefaults(true);
	}
}
