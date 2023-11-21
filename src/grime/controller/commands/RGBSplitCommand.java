package grime.controller.commands;

import grime.model.Model;

/**
 * This class represents the command to get the RGB components of an image.
 */
public class RGBSplitCommand implements Command {
  private final Model model;

  /**
   * Constructor for RGBSplitCommand that takes in a model.
   * @param model model to be used
   */
  public RGBSplitCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 4);
    for (int i = 0; i < 4; i++) {
      if (i == 0) {
        if (!model.containsImage(args[i])) {
          throw new IllegalArgumentException("Image " + args[i] + " does not exist.");
        }
      } else {
        if (model.containsImage(args[i])) {
          throw new IllegalArgumentException("Image " + args[i] + " already exists.");
        }
      }
    }
    model.rgbSplit(args[0], args[1], args[2], args[3]);

  }
}
