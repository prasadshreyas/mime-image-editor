package mime.controller.commands;

import mime.controller.commands.Command;
import mime.model.Model;

public class ColorCorrectCommand implements Command {
  private final Model model;
  public ColorCorrectCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {

    int[] expectedLength = {2, 4};
    ArgValidator.validate(args, expectedLength);
    int splitValue = 0;

    if (!model.containsImage(args[0])) {
      throw new IllegalArgumentException("No such image exists");
    }
    if (model.containsImage(args[1])) {
      throw new IllegalArgumentException("New Image already exists");
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
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Split value must be an integer");
      }
    }

    model.colorCorrection(args[0], args[1], splitValue);
  }
}
