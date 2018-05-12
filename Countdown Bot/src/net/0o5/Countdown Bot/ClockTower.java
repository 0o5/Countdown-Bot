package net.hashsploit.clocktowerbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.hashsploit.clocktowerbot.commands.DeleteTimerCommand;
import net.hashsploit.clocktowerbot.commands.HelpCommand;
import net.hashsploit.clocktowerbot.commands.ListTimersCommand;
import net.hashsploit.clocktowerbot.commands.SetTimerCommand;

public class ClockTower {

	public static final String NAME = "Clock Tower";
	public static final String VERSION = "0.1.1";
	public static final String PREFIX = "$";

	private static ClockTower instance;
	private List<ICommand> commands;

	private String token;
	private JDA jda;

	public ClockTower(String accessToken) {
		instance = this;
		this.token = accessToken;
		this.commands = new ArrayList<ICommand>();

		// Register commands
		commands.add(new HelpCommand());
		commands.add(new SetTimerCommand());
		commands.add(new ListTimersCommand());
		commands.add(new DeleteTimerCommand());

		// Initialize JDA
		try {
			jda = new JDABuilder(AccountType.BOT)
					.addEventListener(new BotListener())
					.setToken(token)
					.setCorePoolSize(2)
					.setStatus(OnlineStatus.IDLE).setGame(Game.of(GameType.DEFAULT, "Initializing ..."))
					.setBulkDeleteSplittingEnabled(false)
					.setAutoReconnect(true)
					.buildAsync();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
	}

	public void handleCommandEvent(String[] args, GuildMessageReceivedEvent event) {
		String cmd = args[0];

		// Adjust args to drop the command in the 0th index
		args = Arrays.copyOfRange(args, 1, args.length);

		
		// Check permissions
		if (!event.getMember().isOwner() && !(event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
			return;
		}
		
		// Loop through all available commands
		for (ICommand c : commands) {

			if (c.getCommandName().equalsIgnoreCase(cmd)) {

				// Invoke the command
				c.invoke(args, event.getMessage());
			}
		}
		
		
		if (cmd.equalsIgnoreCase("shutdown")) {
			ClockTimer.destroy();
			jda.shutdownNow();
		}

	}
	
	public List<ICommand> getCommands() {
		return commands;
	}

	public JDA getJDA() {
		return jda;
	}

	public static ClockTower getInstance() {
		return instance;
	}

}
