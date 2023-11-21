package mime.controller;


import java.util.List;
import java.util.Map;

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

public class CommandExecutor {
  private Map<String, Command> commands;

  public CommandExecutor(Model model) {
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

  public boolean executeCommand(List<String> lineArgs) throws Exception {
    String commandName = lineArgs.get(0);
    Command command = commands.get(commandName);

    if (command != null) {
      command.execute(lineArgs.subList(1, lineArgs.size()).toArray(new String[0]));
      return false;
    } else if ("quit".equals(commandName)) {
      return true;
    } else {
      throw new IllegalArgumentException("Invalid command. Enter a valid command or 'quit' to exit.");
    }
  }
}
