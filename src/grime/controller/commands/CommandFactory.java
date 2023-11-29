package grime.controller.commands;

import grime.model.Model;

public class CommandFactory {

  public static Command createCommand(String commandType, Model model) {
    switch (commandType) {
      case "load":
        return new LoadCommand(model);
      // Add more cases for other commands
      default:
        throw new IllegalArgumentException("Unknown command type: " + commandType);
    }
  }
}