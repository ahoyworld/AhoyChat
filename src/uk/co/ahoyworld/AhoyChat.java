package uk.co.ahoyworld;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class AhoyChat extends JavaPlugin
{
	String channel;
	Logger log;
	File channelsFile;
	FileConfiguration channels;
	public HashMap<String,String> chatMode = new HashMap<String,String>();

	
	
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
    	//incorporate aliases in the future
    	if (channels.getKeys(false).contains(cmd.getName()))
    	{
    		//getAliases();
    		channel = cmd.getName();
    		
    		if (args.length >= 1)
    		{
    			//post message to selected channel
    			//getMessage(args); //will get message by putting together the arguments from args
    		} else {
    			//change channel mode - sticky
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