package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the FlipVerticallyCommand class that implements the Command interface. It
 * represents the command that flips an image vertically.
 */
public class FlipVerticallyCommand implements Command {
  private final Model model;

  /**
   * Constructor for FlipVerticallyCommand that takes in a model.
   * @param model
   */
  public FlipVerticallyCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 2);
    if (model.containsImage(args[0])) {
      if (!model.containsImage(args[1])) {
        model.flipVertically(args[0], args[1]);
      } else {
        throw new IllegalArgumentException("New Image already exists");
      }
    } else {
      throw new IllegalArgumentException("No such image exists");
    }
  }
}
