The 5zig Mod API 2.0
====================
The 5zig Mod ServerAPI is a plugin created for easy communication from a Plugin to a Client using CustomPayloads. This will of course only work if the user has got The 5zig Mod installed (http://5zig.eu/downloads).

Temporary download link of the API is here: http://5zig.eu/u/beta-api (Last updated on 01.04.2015-17:00)
Better download management will be added SoonTM.

The API can currently send Stats and even images to the client, within a few lines of code.
On the client side, that could look like this: ![The 5zig Mod API](http://5zig.eu/i/v1elhxs) ![The 5zig Mod API](http://5zig.eu/i/fiYJpk7.jpg)


The API provides 2 Custom Events: The5zigModUserLoginEvent and The5zigModUserJoinEvent.

The The5zigModUserLoginEvent is called when the client attempts to connect to the Server via a Plugin Message. If you cancel this event, the ServerAPI will disconnect the Mod User.

The The5zigModUserJoinEvent is called when the client is already connected to the Server. Use this event, if you want to send Data when the Player joins the Server.
This event also has got an instance of the mod user. Use event.getModUser() or The5zigMod.getInstance().getUserManager().getUser(player) to access the mod user class. In this class you can simply add new stats, set a display name or send a image to the client, with just 1 line of code.

An example Plugin could look like the following:
```java
public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onModUserJoin(The5zigModUserJoinEvent event) { // Listen for The The5zigModUserJoinEvent
		final ModUser modUser = event.getModUser(); // Get the ModUser
		modUser.getStatsManager().setDisplayName("Server Stats of " + modUser.getPlayer().getName()); // Change the Display Name.
		modUser.getStatsManager().getStat("Foo").setScore("Bar"); // Add random stats
		modUser.getStatsManager().sendImage("anim/5zig1.png"); // Send an image
		getServer().getScheduler().runTaskLater(this, new Runnable() {
			@Override
			public void run() {
				modUser.getStatsManager().resetImage("anim/5zig1.png"); // Remove the image after 50 ticks
			}
		}, 50);
	}

}
```

The 5zig Mod ServerAPI 2.0 will be officially released SOON&trade; with the next update of the 5zig mod
