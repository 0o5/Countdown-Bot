package net.hashsploit.clocktowerbot;

import java.util.Arrays;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.RichPresence;
import net.dv8tion.jda.core.events.ExceptionEvent;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {
	
	@Override
	public void onReady(ReadyEvent event) {
		ClockTower.getInstance().getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
		ClockTower.getInstance().getJDA().getPresence().setGame(RichPresence.watching("The Flow of Time ⏲"));
	}

	public void onShutdown(ShutdownEvent event) {

	}

	@Override
	public void onException(ExceptionEvent event) {

	}

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		
	}

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		if (event.getAuthor() == event.getJDA().getSelfUser()) {
			return;
		}
		
		// Split the input into words
		String[] parts = event.getMessage().getContentRaw().split(" ");
		
		if (parts == null) {
			return;
		}
		
		if (parts.length == 0) {
			return;
		}
		
		// Mentioned via prefix
		if (parts[0].startsWith(ClockTower.PREFIX)) {
			if (parts != null) {
				if (parts.length > 0) {
					parts[0] = parts[0].substring(ClockTower.PREFIX.length());
					ClockTower.getInstance().handleCommandEvent(parts, event);
				}
			}
			return;
		}
		
		// Mentioned via @mention
		if (parts[0].startsWith("@" + event.getJDA().getSelfUser().getName()) && event.getMessage().isMentioned(event.getJDA().getSelfUser())) {
			parts = Arrays.copyOfRange(parts, 1, parts.length);
			if (parts != null) {
				if (parts.length > 0) {
					ClockTower.getInstance().handleCommandEvent(parts, event);
				}
			}
			return;
		}
	}
	
}
