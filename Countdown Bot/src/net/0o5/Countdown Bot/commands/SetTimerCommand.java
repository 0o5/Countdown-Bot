package net.hashsploit.clocktowerbot.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import net.dv8tion.jda.core.entities.Message;
import net.hashsploit.clocktowerbot.ClockTimer;
import net.hashsploit.clocktowerbot.ClockTower;
import net.hashsploit.clocktowerbot.ICommand;

public class SetTimerCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "set";
	}

	@Override
	public String getCommandDescription() {
		return "Create a new countdown, `" + ClockTower.PREFIX + getCommandName() + " {NameOfTimer} {hh:mm:ss}`";
	}

	@Override
	public String[] getPrimaryParams() {
		return new String[] {
				"NameOfTimer",
				"hh:mm:ss"
		};
	}

	@Override
	public boolean invoke(String[] args, Message message) {
		
		if (args.length != 2) {
			message.getChannel().sendMessage(":timer: Invalid timer arguments!").queue();
			return false;
		}
		
		// TODO: Verify the name is alphanumeric (no special characters)
		String name = args[0];
		
		
		for (ClockTimer t : ClockTimer.getTimers()) {
			if (t.getGuild() == message.getGuild().getIdLong()) {
				if (t.getName().equalsIgnoreCase(name)) {
					message.getChannel().sendMessage(":timer: The timer '**" + name + ":**' already exists!").queue();
					return true;
				}
			}
		}
		
		// TODO: Verify time input
		String timeStr = args[1];
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		try {
			Date date = sdf.parse(timeStr);
			
			message.getChannel().sendMessage(":timer: **" + name + ":** Set!").queue(
				msg -> new ClockTimer(name, (date.getTime() + System.currentTimeMillis()), message.getGuild().getIdLong(), msg) 
			);
			
			message.delete().queue();
		} catch (ParseException e) {
			message.getChannel().sendMessage(":timer: Invalid date format! Use `hh:mm:ss`").queue();
		}
		
		return true;
	}

}
