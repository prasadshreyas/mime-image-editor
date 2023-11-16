package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the BrightenCommand class that implements the Command interface. It
 * represents the command that brightens an image.
 */
public class BrightenCommand implements Command {
  private final Model model;

  /**
   * Constructor for BrightenCommand that takes in a model.
   * @param model model to be used
   */
  public BrightenCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 3);
    if (model.containsImage(args[1])) {
      if (!model.containsImage(args[2])) {
        model.brighten(Integer.parseInt(args[0]), args[1], args[2]);
      } else {
        throw new IllegalArgumentException("New Image already exists");
      }
    } else {
      throw new IllegalArgumentException("No such image exists");
    }
  }

}
