package grime.controller;


import java.util.HashMap;
import java.util.Map;

import grime.controller.commands.Command;

public class CommandRegistry {
  private static Map<String, Command> commands = new HashMap<>();

  public static void registerCommand(String commandName, Command command) {
    commands.put(commandName, command);
  }

  public static Command getCommand(String commandName) {
    return commands.get(commandName);
  }

  // Optionally, you can add a method to list available commands
  public static String listAvailableCommands() {
    return String.join(", ", commands.keySet());
  }
}
