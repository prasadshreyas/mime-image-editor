package mime.controller.commands;

import mime.model.Model;

public class RGBSplitCommand implements Command {
  private final Model model;

  public RGBSplitCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 4);
    for (int i = 0; i < 4; i++) {
      // if i = 0, model should contain the image name
      // if i = else, model should not contain the image. Throw
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
