package bl4ckscor3.plugin.spellingcorrection.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class AsyncPlayerChatAndQuitListener implements Listener
{
	private static HashMap<String, String> messages = new HashMap<String, String>();
	private static Plugin plugin;
	
	public AsyncPlayerChatAndQuitListener(Plugin pl)
	{
		plugin = pl;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if(messages.containsKey(event.getPlayer().getName()))
			messages.remove(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		String message = event.getMessage();
		String username = event.getPlayer().getName();

		//checking if someone corrects someone else
		if(message.split(" ")[0].endsWith(":") || message.split(" ")[0].endsWith(","))
		{
			boolean colon;

			if(message.split(" ")[0].endsWith(":"))
				colon = true;
			else
				colon = false;

			if(message.split(" ")[1].startsWith("s/"))
			{
				String[] split;
				String newMessage = "";
				int i = 0;

				//actually getting only the s/x/y message if it contains spaces
				for(String s : message.split(" "))
				{
					if(i != 0)
						newMessage += s + " ";
					i++;
				}

				//removing the last character of the string to prevent 2 spaces
				newMessage = newMessage.substring(0, newMessage.length() - 1);
				split = newMessage.split("/");

				if(split.length == 3 && split[0].equals("s"))
					correctSpelling(event, split, true, colon ? message.split(":")[0] : message.split(",")[0]);
				return;
			}
		}

		//checking if someone is correcting himself
		if(message.startsWith("s/"))
		{
			String[] split = message.split("/");

			if(split.length == 3 && split[0].equals("s"))
				correctSpelling(event, split, false, username);
			return;
		}

		messages.put(event.getPlayer().getName(), event.getMessage());
	}

	private static void correctSpelling(AsyncPlayerChatEvent event, String[] split, boolean correctsDifferentUser, String userToCorrect)
	{
		String toReplace = split[1];
		String replaceWith = split[2];
		String correctedMessage = messages.get(userToCorrect).replace(toReplace, replaceWith);

		messages.put(userToCorrect, correctedMessage);

		if(correctsDifferentUser)
			sendMessage(userToCorrect + " was corrected by " + event.getPlayer().getName() + " and " + ChatColor.BOLD + "meant " + ChatColor.RESET + "to say: " + correctedMessage);
		else
			sendMessage(userToCorrect + ChatColor.BOLD + " meant " + ChatColor.RESET + "to say: " + correctedMessage);
		
		event.setCancelled(true);
	}

	private static void sendMessage(String message)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage("[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.RESET + "] " + message);
		}
	}
}
