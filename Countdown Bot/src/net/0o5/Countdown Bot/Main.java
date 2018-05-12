package net.hashsploit.clocktowerbot;

public class Main {
	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.err.println("Error: you must provide a Discord Access Token as the first program argument!");
			System.exit(1);
		}
		
		// And away we go ...
		new ClockTower(args[0]);
	}
	
}
