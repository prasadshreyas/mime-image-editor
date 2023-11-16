package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the command to get the intensity component of an image.
 */
public class IntensityComponentCommand implements Command {
  private final Model model;

  /**
   * Constructor for IntensityComponentCommand that takes in a model.
   * @param model
   */
  public IntensityComponentCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 2);
    if (model.containsImage(args[0])) {
      if (!model.containsImage(args[1])) {
        model.getBrightnessComponent(args[0], Model.Brightness.INTENSITY, args[1]);
      } else {
        throw new IllegalArgumentException("Image " + args[1] + " already exists.");
      }
    } else {
      throw new IllegalArgumentException("Image " + args[0] + " does not exist.");
    }
  }
}
