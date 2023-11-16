package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the command to get the sepia version of an image.
 */
public class SepiaCommand implements Command {
  private final Model model;

  /**
   * Constructor for SepiaCommand that takes in a model.
   * @param model
   */
  public SepiaCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {

    int[] expectedLength = {2, 4};
    ArgValidator.validate(args, expectedLength);

    if (!model.containsImage(args[0])) {
      throw new IllegalArgumentException("No such image exists");
    }
    if (model.containsImage(args[1])) {
      throw new IllegalArgumentException("New Image already exists");
    }

    if (args.length == 2) {
      model.sepia(args[0], args[1]);
    } else if (args.length == 4) {
      if (!args[2].equals("-split")) {
        throw new IllegalArgumentException("Invalid argument: " + args[2]);
      }
      try {
        int splitValue = Integer.parseInt(args[3]);
        model.sepia(args[0], args[1]);
        model.splitView(args[0], args[1], splitValue);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Split value must be an integer");
      }
    }
  }
}
