package net.hashsploit.clocktowerbot;

import net.dv8tion.jda.core.entities.Message;

public interface ICommand {
	
	public String getCommandName();
	
	public String getCommandDescription();
	
	public String[] getPrimaryParams();
	
	public boolean invoke(String[] args, Message message);
	
}
