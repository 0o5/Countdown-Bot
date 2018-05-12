package net.hashsploit.clocktowerbot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.hashsploit.clocktowerbot.ClockTower;
import net.hashsploit.clocktowerbot.ICommand;

public class HelpCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public String getCommandDescription() {
		return "Help commands for the bot";
	}

	@Override
	public String[] getPrimaryParams() {
		return null;
	}

	@Override
	public boolean invoke(String[] args, Message message) {
		StringBuilder sb = new StringBuilder();
		
		for (ICommand cmd : ClockTower.getInstance().getCommands()) {
			// Hide non-descriptive commands
			if (cmd.getCommandDescription() == null) {
				continue;
			}
			
			sb.append("**- `").append(ClockTower.PREFIX).append(cmd.getCommandName()).append("`** ").append(cmd.getCommandDescription()).append('.').append('\n');
		}
		
		message.getChannel().sendMessage(":timer: **Commands:**\n" + sb.toString()).queue();
		
		return true;
	}

}
