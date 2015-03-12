package bl4ckscor3.plugin.spellingcorrection.core;

import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.spellingcorrection.listener.AsyncPlayerChatAndQuitListener;

public class SpellingCorrection extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new AsyncPlayerChatAndQuitListener(this), this);
		System.out.println("[" + getDescription().getName() + "] v " + getDescription().getVersion() + " enabled.");
	}

	@Override
	public void onDisable()
	{
		System.out.println("[" + getDescription().getName() + "] v " + getDescription().getVersion() + " disabled.");
	}
}