package mime.controller.commands;

import mime.model.Model;

/**
 * BlurCommand class that implements Command interface. This class represents the blur command that
 * blurs an image.
 */
public class BlurCommand implements Command {
  private final Model model;

  /**
   * Constructor for BlurCommand that takes in a model.
   *
   * @param model model to be used
   */
  public BlurCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    int splitValue;
    int[] expectedLength = {2, 4};

    ArgValidator.validate(args, expectedLength);


    if (!model.containsImage(args[0])) {
      throw new IllegalArgumentException("No such image exists");
    }
    if (model.containsImage(args[1])) {
      throw new IllegalArgumentException("New Image already exists");
    }
    if (args.length == 2) {
      model.blur(args[0], args[1]);
    }
    if (args.length == 4) {
      if (!args[2].equals("-split")) {
        throw new IllegalArgumentException("Invalid argument: " + args[2]);
      }
      try {
        splitValue = Integer.parseInt(args[3]);
        // Check if split value is between 0 and 100
        if (splitValue < 0 || splitValue > 100) {
          throw new IllegalArgumentException("Split value must be between 0 and 100");
        }
        model.blur(args[0], args[1]);
        model.splitView(args[0], args[1], splitValue);

      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Split value must be an integer");
      }
    }


  }
}






