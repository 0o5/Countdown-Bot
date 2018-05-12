package net.hashsploit.clocktowerbot.commands;

import net.dv8tion.jda.core.entities.Message;
import net.hashsploit.clocktowerbot.ClockTimer;
import net.hashsploit.clocktowerbot.ClockTower;
import net.hashsploit.clocktowerbot.ICommand;

public class DeleteTimerCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "stop";
	}

	@Override
	public String getCommandDescription() {
		return "Stop a countdown, `" + ClockTower.PREFIX + getCommandName() + " {NameOfTimer}`";
	}

	@Override
	public String[] getPrimaryParams() {
		return new String[] {
				"NameOfTimer",
		};
	}

	@Override
	public boolean invoke(String[] args, Message message) {
		
		if (args.length != 1) {
			message.getChannel().sendMessage(":timer: Missing timer name!").queue();
			return false;
		}
		
		String name = args[0];
		
		for (ClockTimer t : ClockTimer.getTimers()) {
			if (t.getGuild() == message.getGuild().getIdLong()) {
				if (t.getName().equalsIgnoreCase(name)) {
					t.stop();
					message.getChannel().sendMessage(":timer: **" + name + ":** stopped!").queue();
					return true;
				}
			}
		}
		
		
		message.getChannel().sendMessage(":timer: A timer could not be found by that name!").queue();
		return true;
	}
	
}
