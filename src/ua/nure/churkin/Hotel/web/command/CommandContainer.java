package ua.nure.churkin.Hotel.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CommandContainer {

	private static final Logger LOG = Logger.getLogger(CommandContainer.class);

	private static Map<String, Command> commands = new TreeMap<String, Command>();

	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewSettings", new ViewSettingsCommand());

		commands.put("noCommand", new NoCommand());

		// client commands
		//commands.put("advanced_select", new ListMenuCommand());
		commands.put("select", new ListMenuCommand());
		commands.put("advanced", new ListAdvancedMenu());
		commands.put("simple", new ListSimpleMenu());

		// admin commands
		commands.put("listOrders", new ListOrdersCommand());
		commands.put("admin_order", new ListOrdersCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand");
		}

		return commands.get(commandName);
	}

}