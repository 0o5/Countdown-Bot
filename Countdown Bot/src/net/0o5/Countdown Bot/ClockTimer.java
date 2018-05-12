package net.hashsploit.clocktowerbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.Message;

public class ClockTimer {
	
	private static List<ClockTimer> timers = new ArrayList<ClockTimer>();
	
	private String name;
	private long guild;
	private long time;
	private Timer timer;
	private Message message;
	
	/**
	 * Create a new ClockTimer
	 * @param name The name to refer the timer by
	 * @param time The UNIX time at which the timer will expire
	 * @param message The message that we will edit every second to update
	 */
	public ClockTimer(String name, long time, long guild, Message message) {
		this.name = name;
		this.time = time;
		this.guild = guild;
		
		timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				long millis = (time - System.currentTimeMillis());
				
				if (millis <= 0) {
					message.delete().queue();
					message.getChannel().sendMessage(":timer: **" + name + ":** :boom: **__TIME IS UP__** :boom:").queue();
					stop();
					return;
				}
				
				long hours = TimeUnit.MILLISECONDS.toHours(millis);
				millis -= TimeUnit.HOURS.toMillis(hours);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
				millis -= TimeUnit.MINUTES.toMillis(minutes);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
				
				StringBuilder sb = new StringBuilder();
				sb.append(":timer: **").append(name).append(':').append(' ');
				sb.append((hours <= 9 ? "0" + hours : hours)).append(':'); // Hours
				sb.append((minutes <= 9 ? "0" + minutes : minutes)).append(':'); // Minutes
				sb.append((seconds <= 9 ? "0" + seconds : seconds)).append("**"); // Seconds
				
				message.editMessage(sb.toString()).complete();
			}
		}, 1000L, 2000L);
		
		timers.add(this);
	}
	
	public String getName() {
		return name;
	}
	
	public long getTime() {
		return time;
	}
	
	public long getGuild() {
		return guild;
	}
	
	public synchronized void stop() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
		if (message != null) {
			message.delete().queue();
		}
		if (timers != null) {
			timers.remove(this);
		}
	}
	
	public static synchronized List<ClockTimer> getTimers() {
		return timers;
	}
	
	public static synchronized void destroy() {
		for (ClockTimer t : timers) {
			if (t != null) {
				t.stop();
			}
		}
		timers.clear();
	}
	
}
