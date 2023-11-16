package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the command to get the red component of an image.
 */
public class RedComponentCommand implements Command {
  private final Model model;

  /**
   * Constructor for RedComponentCommand that takes in a model.
   * @param model
   */
  public RedComponentCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 2);
    if (model.containsImage(args[0])) {
      if (!model.containsImage(args[1])) {
        model.getColorComponent(args[0], Model.Channel.RED, args[1]);
      } else {
        throw new IllegalArgumentException("Image " + args[1] + " already exists.");
      }
    } else {
      throw new IllegalArgumentException("Image " + args[0] + " does not exist.");
    }
  }
}
