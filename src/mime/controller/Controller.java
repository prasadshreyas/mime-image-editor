package mime.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import mime.controller.commands.BlueComponentCommand;
import mime.controller.commands.BlurCommand;
import mime.controller.commands.BrightenCommand;
import mime.controller.commands.ColorCorrectCommand;
import mime.controller.commands.Command;
import mime.controller.commands.CompressCommand;
import mime.controller.commands.FlipHorizontallyCommand;
import mime.controller.commands.FlipVerticallyCommand;
import mime.controller.commands.GreenComponentCommand;
import mime.controller.commands.HistogramCommand;
import mime.controller.commands.IntensityComponentCommand;
import mime.controller.commands.LevelAdjustCommand;
import mime.controller.commands.LoadCommand;
import mime.controller.commands.LumaComponentCommand;
import mime.controller.commands.RGBCombineCommand;
import mime.controller.commands.RGBSplitCommand;
import mime.controller.commands.RedComponentCommand;
import mime.controller.commands.RunScriptCommand;
import mime.controller.commands.SaveCommand;
import mime.controller.commands.SepiaCommand;
import mime.controller.commands.SharpenCommand;
import mime.controller.commands.ValueComponentCommand;
import mime.model.Model;
import mime.view.View;

/**
 * This class represents the controller for the program.
 * It takes input from the user and passes it to the model.
 * It also takes input from the model and passes it to the view.
 */
public class Controller {
  public static final String QUIT_COMMAND = "quit";
  private static Map<String, Command> commands;
  private final Model model;
  private final View view;
  private final Scanner scanner;

  /**
   * Constructs a controller object.
   *
   * @param model the model to be used by the controller.
   * @param view  the view to be used by the controller.
   */
  public Controller(Model model, View view, Scanner scanner) {
    this.model = model;
    this.view = view;
    commands = new HashMap<>();
    initializeCommands();
    this.scanner = scanner;
  }

  public static Command getCommand(String commandName) {
    return commands.get(commandName);
  }

  private void initializeCommands() {
    commands.put("load", new LoadCommand(model));
    commands.put("save", new SaveCommand(model));
    commands.put("brighten", new BrightenCommand(model));
    commands.put("blur", new BlurCommand(model));
    commands.put("sepia", new SepiaCommand(model));
    commands.put("sharpen", new SharpenCommand(model));
    commands.put("horizontal-flip", new FlipHorizontallyCommand(model));
    commands.put("vertical-flip", new FlipVerticallyCommand(model));
    commands.put("run-script", new RunScriptCommand());
    commands.put("rgb-split", new RGBSplitCommand(model));
    commands.put("rgb-combine", new RGBCombineCommand(model));
    commands.put("value-component", new ValueComponentCommand(model));
    commands.put("luma-component", new LumaComponentCommand(model));
    commands.put("intensity-component", new IntensityComponentCommand(model));
    commands.put("red-component", new RedComponentCommand(model));
    commands.put("green-component", new GreenComponentCommand(model));
    commands.put("blue-component", new BlueComponentCommand(model));
    commands.put("compress", new CompressCommand(model));
    commands.put("color-correct", new ColorCorrectCommand(model));
    commands.put("histogram", new HistogramCommand(model));
    commands.put("level-adjust", new LevelAdjustCommand(model));
  }

  /**
   * Runs the controller by taking input from the user and passing it to the model.
   */
  public void run() {
    boolean quit = false;
    while (!quit) {
      view.display("Enter a command:");
      List<String> lineArgs = scanner.hasNextLine() ? List.of(scanner.nextLine().split("\\s+"))
              : List.of();

      if (lineArgs.isEmpty()) {
        handleException(new IllegalArgumentException("Command cannot be empty."));
        continue;
      }
      try {
        quit = executeCommand(lineArgs);
      } catch (Exception e) {
        handleException(e);
      }

    }
  }

  /**
   * Executes the command based on the provided arguments.
   *
   * @param lineArgs the command arguments
   * @return true if the quit command is executed, false otherwise
   */
  public boolean executeCommand(List<String> lineArgs) {
    String command = lineArgs.get(0);
    try {
      if (commands.containsKey(command)) {
        commands.get(command).execute(lineArgs.subList(1,
                lineArgs.size()).toArray(new String[0]));
      } else if (QUIT_COMMAND.equals(command)) {
        return true;
      } else {
        throw new IllegalArgumentException("Invalid command. Enter a valid "
                + "command or 'quit' to exit.");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return false;
  }

  private void handleException(Exception e) {
    view.display(e.getMessage());
  }
}
