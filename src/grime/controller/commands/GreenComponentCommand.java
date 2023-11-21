package grime.controller.commands;

import grime.model.Model;

/**
 * This class represents the GreenComponentCommand class that implements the Command interface. It
 * represents the command that gets the green component of an image.
 */
public class GreenComponentCommand implements Command {
  private final Model model;

  /**
   * Constructor for GreenComponentCommand that takes in a model.
   * @param model model to be used
   */
  public GreenComponentCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 2);
    if (model.containsImage(args[0])) {
      if (!model.containsImage(args[1])) {
        model.getColorComponent(args[0], Model.Channel.GREEN, args[1]);
      } else {
        throw new IllegalArgumentException("Image " + args[1] + " already exists.");
      }
    } else {
      throw new IllegalArgumentException("Image " + args[0] + " does not exist.");
    }
  }

}
