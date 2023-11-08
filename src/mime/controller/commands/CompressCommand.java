package mime.controller.commands;

import mime.model.Model;

public class CompressCommand implements Command {
  private final Model model;

  public CompressCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) throws Exception {
    ArgValidator.validate(args, 3);
    if (model.containsImage(args[1])) {
      if (!model.containsImage(args[2])) {
        model.compressImage(Integer.parseInt(args[0]), args[1], args[2]);
      } else {
        throw new IllegalArgumentException("Image " + args[2] + " already exists.");
      }
    } else {
      throw new IllegalArgumentException("Image " + args[1] + " does not exist.");
    }

  }
}
