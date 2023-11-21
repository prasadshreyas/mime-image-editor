package grime.controller.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import grime.controller.CLIController;

import static grime.controller.CLIController.QUIT_COMMAND;

/**
 * This class represents the command to run a script.
 */
public class RunScriptCommand implements Command {

  /**
   * Constructor for RunScriptCommand that takes in a model.
   */
  public RunScriptCommand() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }


  @Override
  public void execute(String[] args) throws Exception {
    ArgValidator.validate(args, 1);
    String inputFilePath = args[0];
    try (Scanner scanner = new Scanner(new FileReader(inputFilePath))) {
      while (scanner.hasNextLine()) {
        String curLine = scanner.nextLine().trim();
        if (curLine.isEmpty() || curLine.startsWith("#")) {
          continue;
        }

        String[] curLineArgs = curLine.split("\\s+");
        String commandName = curLineArgs[0];

        // Check for quit command in script
        if (QUIT_COMMAND.equals(commandName)) {
          break;
        }

        String[] commandArgs = new String[curLineArgs.length - 1];
        System.arraycopy(curLineArgs, 1, commandArgs, 0, curLineArgs.length - 1);

        // Get and execute the command if it's recognized
        Command command = CLIController.getCommand(commandName);
        if (command != null) {
          command.execute(commandArgs);
        } else {
          throw new IllegalArgumentException("Invalid command: " + commandName);
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: " + inputFilePath, e);
    } catch (Exception e) {
      // e stacktrace
      throw new IllegalArgumentException(e.getMessage());
    }

  }

}
