The 5zig Mod API 2.1
====================
The 5zig Mod ServerAPI is a plugin created for easy communication from a Plugin to a Client using Custom Payloads / Plugin messaging. This will of course only work if the user has got The 5zig Mod installed (http://5zig.eu/downloads).

Download link of the API is here: http://5zig.eu/u/api (Last updated on 10.05.2015-10:00)

The API can currently send Stats and even images to the client, within a few lines of code.
On the client side, that could look like this: ![The 5zig Mod API](http://5zig.eu/scr/fiYJpk7.jpg)


The API provides 2 Custom Events: The5zigModUserLoginEvent and The5zigModUserJoinEvent.

The The5zigModUserLoginEvent is called when the client attempts to connect to the Server via a Plugin Message. If you cancel this event, the ServerAPI will disconnect the Mod User.

The The5zigModUserJoinEvent is called when the client is already connected to the Server. Use this event, if you want to send Data when the Player joins the Server.
This event also has got an instance of the mod user. Use event.getModUser() or The5zigMod.getInstance().getUserManager().getUser(player) to access the mod user class. In this class you can simply add new stats, set a display name or send a image to the client, with just 1 line of code.

An example Plugin could look like the following:
```java
public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		ImageRegistry imageRegistry = The5zigMod.getInstance().getImageRegistry();
		try {
			imageRegistry.register("test.anim1", "anim/5zig1.png"); // Register a new Image
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onModUserJoin(The5zigModUserJoinEvent event) { // Listen for The The5zigModUserJoinEvent
		final ModUser modUser = event.getModUser(); // Get the ModUser
		modUser.getStatsManager().setDisplayName("Server Stats of " + modUser.getPlayer().getName()); // Change the Display Name.
		modUser.getStatsManager().getStat("Foo").setScore("Bar"); // Add random stats
		modUser.getStatsManager().sendImage("test.anim1"); // Send the privously registered image
		getServer().getScheduler().runTaskLater(this, new Runnable() {
			@Override
			public void run() {
				modUser.getStatsManager().resetImage(); // Remove the image after 50 ticks
			}
		}, 50);
	}

}
```
