package uk.co.ahoyworld;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AhoyChat extends JavaPlugin
{
	private Functions func;
	
	String channel;
	Logger log;
	File channelsFile;
	FileConfiguration channels;
    public HashMap<String, String> channelList = new HashMap<String, String>(); //channel name, alias
	public HashMap<String, String> chatMode = new HashMap<String, String>(); //player name, channel
	
	
	public void onEnable()
	{
		log = this.getLogger();
		
        channelsFile = new File(getDataFolder(), "channels.yml");
        
        try
        {
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        channels = new YamlConfiguration();
        
        loadYamls();
        
        channelList = func.getAllChannels();
		
		log.info("Plugin enabled.");
	}
	
	
	public void onDisable()
	{
		log.info("Plugin disabled.");
	}
	
	
    public void saveYamls()
    {
        try
        {
            channels.save(channelsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void loadYamls()
    {
        try
        {
            channels.load(channelsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void firstRun() throws Exception
    {
        if (!channelsFile.exists())
        {
        	channelsFile.getParentFile().mkdirs();
        	copy(getResource("channels.yml"), channelsFile);
        }
    }
 
    private void copy(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0)
            {
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    	if (channelList.containsValue(cmd.getName()))
    	{
    		String player = sender.getName();
    		String channel = func.getKeyFromValue(channelList, cmd.getName());
    		boolean isSticky = func.isSticky(channel);
    		Integer radius = func.getRadius(channel);
    		
    		if (args.length >= 1)
    		{
    			List<Player> playerList = func.getPlayerList(radius, player);
    			String message = func.getMessage(args);
    			
    			func.sendMessage(player, message, channel, playerList);
    		}
    		
			if (isSticky)
			{
				func.switchChannel(player, channel);
			}
    		
    		return true;
    	}
    	
    	
    	if (cmd.getName().equalsIgnoreCase("channel"))
    	{
    		return true;
    	}
    	return false;
    }
}