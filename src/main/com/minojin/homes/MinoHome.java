package com.minojin.homes;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.command.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class MinoHome extends JavaPlugin implements Listener {
	FileConfiguration config = this.getConfig();
	private File file = new File(getDataFolder(), "homes.yml");
	YamlConfiguration homes = YamlConfiguration.loadConfiguration(file);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) {
			getLogger().log(Level.WARNING, "Console cannot use home commands!");
		}
		commUtil utils = new commUtil(this);
		
		int lim = config.getInt("homeLimit");
		//SET HOME
		if(cmd.getName().toLowerCase().equals("sethome")) { 
			if(sender instanceof Player) {
				Player p = (Player) sender;
				String name;
				if(args.length == 0) {name = "default";} 
				else {name = args[0];}
				
				// Save player's home to homes.yml
				if(utils.homeExists(p, name) || utils.getHomes(p) < lim) {
					utils.setHome(p, name);
					p.sendMessage("Home successfully set.");
				} else {
					p.sendMessage("You have too many homes!");
				}
			} else {
				sender.sendMessage("There was an error performing this command.");
			}
			return true;
		}
		//DELETE HOME
		else if(cmd.getName().toLowerCase().equals("delhome")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				String name;
				if(args.length == 0) {name = "default";} 
				else {name = args[0];}
				
				if(utils.homeExists(p, name)) {
					utils.delHome(p, name);
					p.sendMessage("Home deleted.");
				} else {
					p.sendMessage("This home does not exist!");
				}
			} else {
				sender.sendMessage("There was an error performing this command.");
			}
			return true;
		}
		//HOME
		else if(cmd.getName().toLowerCase().equals("home")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				String name;
				if(args.length == 0) {name = "default";} 
				else {name = args[0];}
				
				//Send player to home
				if(utils.homeExists(p, name)) {
					utils.home(p, name);
				} else {
					p.sendMessage("This home does not exist!");
				}
			} else {
				sender.sendMessage("There was an error performing this command.");
			}
			return true;
		}
		//LIST HOMES
		else if(cmd.getName().toLowerCase().equals("homes")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				
				Set<String> homes = utils.homes(p);
				String message = "Homes: ";
				for (String x : homes) {
					message += x + ", ";
				}
				message = message.substring(0, message.length()-2);
				sender.sendMessage(message);
				
			} else {
				sender.sendMessage("There was an error performing this command.");
			}
			return true;
		}
		
		
		return false;
	}
	
	public void saveHomes() {
		try {
			homes.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Enabled Plugin!
	@Override
	public void onEnable() {
		configDefaults.setDefaults(config);
		saveConfig();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		getCommand("sethome").setExecutor(this);
		getCommand("delhome").setExecutor(this);
		getCommand("home").setExecutor(this);
		getCommand("homes").setExecutor(this);
		
		if(!file.exists()) {
			saveHomes();
		}
	}
	
	//Disabled Plugin!
	@Override
	public void onDisable() {
		
	}
}
