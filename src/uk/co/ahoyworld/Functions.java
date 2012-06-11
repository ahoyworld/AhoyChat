package uk.co.ahoyworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Functions 
{
	private AhoyChat plugin;
	
	public Functions (AhoyChat plugin)
	{
		this.plugin = plugin;
	}
	
	
	
	//Get Players within a specified radius
	public List<Player> getPlayerList (Integer radius, String playerName)
	{
		List<Player> playerList = new ArrayList<Player>();
		if (radius > -1)
		{
			Player player = Bukkit.getPlayer(playerName);
			List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
			for (Entity ent : nearbyEntities)
			{
				if (ent instanceof Player)
				{
					playerList.add((Player) ent);
				}
			}
			return playerList;
		} else {
			for (Player player : Bukkit.getOnlinePlayers())
			{
				playerList.add(player);
			}
			return playerList;
		}

	}
	
	
	
	//Gets a channel's prefix
	public String getPrefix (String channel)
	{
		String path = channel + ".prefix";
		if (plugin.channels.contains(path))
		{
			return plugin.channels.getString(path);
		} else {
			return "";
		}
	}
	
	
	
	//Gets a channel's suffix
	public String getSuffix (String channel)
	{
		String path = channel + ".suffix";
		if (plugin.channels.contains(path))
		{
			return plugin.channels.getString(path);
		} else {
			return "";
		}
	}
	
	
	
	//Send message
	public void sendMessage (String player, String message, String channel, List<Player> playerList)
	{
		String prefix = getPrefix(channel);
		String suffix = getSuffix(channel);
		for (Player receiver : playerList)
		{
			receiver.sendMessage(prefix + " " + player + " " + message + " " + suffix);
		}
	}
	
	
	
	//Get aliases for selected channel
	public List<String> getAliases (String channel)
	{
		List<String> aliases = new ArrayList<String>();
		aliases.addAll(plugin.channels.getStringList(channel + ".aliases"));
		return aliases;
	}
	
	
	
	//Switch channel
	public void switchChannel (String player, String channel)
	{
		/*
		 * Later incorporate checking for if player can join channel or not
		 * (e.g. use permissions like ahoychat.channels.vip etc)
		 */
		
		plugin.chatMode.put(player, channel);
	}
	
	
	
	//Get all channels and aliases and place them in a list
	public HashMap<String, String> getAllChannels ()
	{		
		HashMap<String, String> channelList = new HashMap<String, String>();
		for (String channel : plugin.channels.getKeys(false))
		{
			channelList.put(channel, channel);
			List<String> aliasList = plugin.channels.getStringList(channel + ".aliases"); 
			if (!aliasList.isEmpty())
			{
				for (String alias : aliasList)
				{
					channelList.put(channel, alias);
				}
			}
		}
		return channelList;
	}
	
	
	
	//Get key from value
	public <K, V> K getKeyFromValue( HashMap<K, V> map, V value)
	{
		for (Entry<K, V> entry: map.entrySet())
		{
			if (value.equals(entry.getValue()))
			{
				return entry.getKey();
			}
		}
		return null;
	}
	
	
	
	//Is the channel sticky?
	public Boolean isSticky (String channel)
	{
		if (plugin.channels.contains(channel + ".sticky"))
		{
			if (plugin.channels.getBoolean(channel + ".sticky") == true)
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	//Combine args together to make a string
	public String getMessage (String[] args)
	{
		String message = "";
		
		for (String str : args)
		{
			message = message + str + " ";
		}
		
		return message;
	}
	
	
	
	//Get channel radius
	public Integer getRadius (String channel)
	{
		String path = channel + ".radius";
		if (plugin.channels.contains(path))
		{
			return plugin.channels.getInt(path);
		} else {
			return -1;
		}
	}
}
