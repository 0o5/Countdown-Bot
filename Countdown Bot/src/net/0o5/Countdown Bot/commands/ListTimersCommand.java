package net.hashsploit.clocktowerbot.commands;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.Message;
import net.hashsploit.clocktowerbot.ClockTimer;
import net.hashsploit.clocktowerbot.ClockTower;
import net.hashsploit.clocktowerbot.ICommand;

public class ListTimersCommand implements ICommand {

	@Override
	public String getCommandName() {
		return "list";
	}

	@Override
	public String getCommandDescription() {
		return "List all the timers, `" + ClockTower.PREFIX + getCommandName() + "`";
	}

	@Override
	public String[] getPrimaryParams() {
		return null;
	}

	@Override
	public boolean invoke(String[] args, Message message) {
		StringBuilder sb = new StringBuilder();
		sb.append(":timer: **Timers:**\n");
		
		for (ClockTimer t : ClockTimer.getTimers()) {
			if (message.getGuild().getIdLong() == t.getGuild()) {
				
				long millis = (t.getTime() - System.currentTimeMillis());
				
				long hours = TimeUnit.MILLISECONDS.toHours(millis);
				millis -= TimeUnit.HOURS.toMillis(hours);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
				millis -= TimeUnit.MINUTES.toMillis(minutes);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
				
				StringBuilder sb2 = new StringBuilder();
				sb2.append((hours <= 9 ? "0" + hours : hours)).append(':');
				sb2.append((minutes <= 9 ? "0" + minutes : minutes)).append(':');
				sb2.append((seconds <= 9 ? "0" + seconds : seconds));
				
				sb.append("- **").append(t.getName()).append(":** time remaining: ").append(sb2.toString()).append("\n");
			}
		}
		
		message.getChannel().sendMessage(sb.toString()).queue();
		message.delete().queue();
		return true;
	}

}
