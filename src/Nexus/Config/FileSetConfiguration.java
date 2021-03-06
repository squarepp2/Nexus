package Nexus.Config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import Nexus.Core.NexusCore;
import Nexus.Util.Util;

public class FileSetConfiguration {
	private final String FILE_NAME;

	private FileConfiguration resources;

	protected FileSetConfiguration Instance;

	NexusCore plugin;

	public FileSetConfiguration(NexusCore par1Plugin, String par2FileName){
		plugin = par1Plugin;
		FILE_NAME = par2FileName;
		Instance = this;
	}

	public void save(){
		initialize();
		try {
			resources.save(plugin.getDataFolder() + File.separator + FILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		plugin.log.info("Saved Configuration.");
	}

	public void initialize() {
		initialize(false);
	}
	public void initialize(boolean isForce) {
		if(resources != null){
			return;
		}
		File file = new File(
				plugin.getDataFolder() +
				File.separator + FILE_NAME);

		if ( !file.exists() || isForce) {
			Util.copyFileFromJar(plugin.getPluginJarFile(),
					file, FILE_NAME, false);
		}
		resources = YamlConfiguration.loadConfiguration(file);
	}
	public String getString(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return resources.getString(par1Key);
		}else{
			return "";
		}
	}
	public void setString(String par1Path, String par2Val){
		initialize();
		set(par1Path, par2Val);
	}
	public Integer getInteger(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return resources.getInt(par1Key);
		}else{
			return -1;
		}
	}
	public void setInteger(String par1Key, Integer par2Val){
		initialize();
		set(par1Key, par2Val);
	}
	public Double getDouble(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return resources.getDouble(par1Key);
		}else{
			return -1.0;
		}
	}
	public void setDouble(String par1Key, Double par2Val){
		initialize();
		set(par1Key, par2Val);
	}
	public Boolean getBoolean(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return resources.getBoolean(par1Key);
		}else{
			return false;
		}
	}
	public void setBoolean(String par1Path, boolean par2Val){
		initialize();
		set(par1Path, par2Val);
	}
	public List<?> getList(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return resources.getList(par1Key);
		}else{
			return new LinkedList<Object>();
		}
	}
	public void setList(String par1Key, List<?> par2Val){
		initialize();
		set(par1Key, par2Val);
	}
	public List<String> getStringList(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return resources.getStringList(par1Key);
		}else{
			return new LinkedList<String>();
		}
	}
	public void setStringList(String par1Key, List<String> par2Val){
		initialize();
		set(par1Key, par2Val);
	}
	public Location getLocation(String par1Key){
		initialize();
		if(!isnull(par1Key)){
			return new Location(
					getWorld(par1Key + ".world"),
					getDouble(par1Key + ".x"),
					getDouble(par1Key + ".y"),
					getDouble(par1Key + ".z")
					);
		}else{
			return new Location(
					plugin.getServer().getWorld("world"), 0.0, 0.0, 0.0);
		}
	}
	public void setLocation(String par1Key, Location par2Val){
		initialize();
		setLocation(par1Key,
				par2Val.getX(),
				par2Val.getY(),
				par2Val.getZ(),
				par2Val.getWorld()
				);
	}
	public void setLocation(String par1Key, Double x, Double y,
					Double z, World World){
		initialize();

		setWorld(par1Key + ".world", World);
		setDouble(par1Key + ".x", x);
		setDouble(par1Key + ".y", y);
		setDouble(par1Key + ".z", z);
	}

	public World getWorld(String par1Key) {
		initialize();
		if(!isnull(par1Key)){
			return plugin.getServer().getWorld(getString(par1Key));
		}else{
			return null;
		}
	}
	public void setWorld(String par1Key, World par2Val) {
		initialize();
		set(par1Key, par2Val.getName());
	}

	public void addDefault(String par1Key, Object par2Obj){
		initialize();
		resources.addDefault(par1Key, par2Obj);
	}
	public void set(String par1Key, Object par2Val){
		initialize();
		resources.set(par1Key, par2Val);
		save();
	}
	public boolean isnull(String par1Key){
		initialize();
		return resources.get(par1Key) == null;
	}

}
