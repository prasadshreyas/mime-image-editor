package grime.controller;


import java.util.List;
import java.util.Map;

import grime.controller.commands.BlueComponentCommand;
import grime.controller.commands.BlurCommand;
import grime.controller.commands.BrightenCommand;
import grime.controller.commands.ColorCorrectCommand;
import grime.controller.commands.Command;
import grime.controller.commands.CompressCommand;
import grime.controller.commands.FlipHorizontallyCommand;
import grime.controller.commands.FlipVerticallyCommand;
import grime.controller.commands.GetImagesCommand;
import grime.controller.commands.GreenComponentCommand;
import grime.controller.commands.HistogramCommand;
import grime.controller.commands.IntensityComponentCommand;
import grime.controller.commands.LevelAdjustCommand;
import grime.controller.commands.LoadCommand;
import grime.controller.commands.LumaComponentCommand;
import grime.controller.commands.RGBCombineCommand;
import grime.controller.commands.RGBSplitCommand;
import grime.controller.commands.RedComponentCommand;
import grime.controller.commands.RunScriptCommand;
import grime.controller.commands.SaveCommand;
import grime.controller.commands.SepiaCommand;
import grime.controller.commands.SharpenCommand;
import grime.controller.commands.ValueComponentCommand;
import grime.model.Model;

/**
 * This class represents the command executor for the program that executes commands.
 * <p>
 * Each command is represented by a string and an object of the corresponding command class.
 * The command executor takes a string and executes the corresponding command.
 * </p>
 */
public class CommandExecutor {
  private Map<String, Command> commands;

  public CommandExecutor(Model model) {
    commands = new java.util.HashMap<>();
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
    commands.put("get-images", new GetImagesCommand(model));
  }

  public boolean executeCommand(List<String> lineArgs) throws Exception {
    String commandName = lineArgs.get(0);
    Command command = commands.get(commandName);

    if (command != null) {
      command.execute(lineArgs.subList(1, lineArgs.size()).toArray(new String[0]));
      return true;
    } else if ("quit".equals(commandName)) {
      return false;
    } else {
      throw new IllegalArgumentException("Invalid command. Enter a valid command or 'quit' to exit.");
    }
  }
}
