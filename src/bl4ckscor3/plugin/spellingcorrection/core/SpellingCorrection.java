package bl4ckscor3.plugin.spellingcorrection.core;

import org.bukkit.plugin.java.JavaPlugin;

import bl4ckscor3.plugin.bl4ckkitCore.core.bl4ckkitCore;
import bl4ckscor3.plugin.spellingcorrection.listener.AsyncPlayerChatAndQuitListener;

public class SpellingCorrection extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		bl4ckkitCore.getPluginManager().registerEvents(this, new AsyncPlayerChatAndQuitListener(this));
		bl4ckkitCore.registerPlugin(this);
		bl4ckkitCore.getMessageManager().sendEnabledMessage(this);
	}

	@Override
	public void onDisable()
	{
		bl4ckkitCore.unregisterPlugin(this);
		bl4ckkitCore.getMessageManager().sendDisabledMessage(this);
	}
}