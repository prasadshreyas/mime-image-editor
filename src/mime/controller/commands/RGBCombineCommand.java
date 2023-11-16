package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the command to combine the RGB components of three images into one.
 */
public class RGBCombineCommand implements Command {
  private final Model model;

  /**
   * Constructor for RGBCombineCommand that takes in a model.
   * @param model
   */
  public RGBCombineCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 4);
    for (int i = 0; i < 4; i++) {
      // if i = 0, model should not contain the image name. Throw
      // if i = else, model should contain the image.
      if (i == 0) {
        if (model.containsImage(args[i])) {
          throw new IllegalArgumentException("Image " + args[i] + " already exists.");
        }
      } else {
        if (!model.containsImage(args[i])) {
          throw new IllegalArgumentException("Image " + args[i] + " does not exist.");
        }
      }
    }
    model.rgbCombine(args[0], args[1], args[2], args[3]);
  }
}
