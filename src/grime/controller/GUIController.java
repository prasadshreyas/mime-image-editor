package grime.controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import grime.controller.commands.*;
import grime.model.Model;
import grime.view.GUIView;
import grime.view.View;


public class GUIController implements Controller {

  private static Map<String, Command> commands;

  public static final String QUIT_COMMAND = "quit";
  private static Model model;
  private final View view;
 // private final Scanner scanner;

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

  public GUIController(Model model, View view) {
    //super(model, view);

    this.model = model;
    this.view = view;
   // this.scanner = scanner;
    commands = new HashMap<>();
    initializeCommands();
  }

//  @Override
//  protected List<String> readInput() {
//    // Implementation for GUI input (e.g., button clicks, text fields)
//    return null;
//  }

  public static Command getCommand(String commandName) {
    return commands.get(commandName);
  }




  public static boolean executeCommand(List<String> lineArgs) {
    String command = lineArgs.get(0);
    try {
      if (commands.containsKey(command)) {
        commands.get(command).execute(lineArgs.subList(1,
                lineArgs.size()).toArray(new String[0]));
        BufferedImage image = model.getImage(lineArgs.get(2));
        GUIView.updateImage(image);
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

  /**
   * Runs the controller.
   */
  @Override
  public void run() {

  }
}
