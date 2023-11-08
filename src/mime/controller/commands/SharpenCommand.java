package mime.controller.commands;

import mime.model.Model;

public class SharpenCommand implements Command {
  private final Model model;

  public SharpenCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    ArgValidator.validate(args, 2);
    if (model.containsImage(args[0])) {
      if (!model.containsImage(args[1])) {
        model.sharpen(args[0], args[1]);
      } else {
        throw new IllegalArgumentException("New Image already exists");
      }
    } else {
      throw new IllegalArgumentException("No such image exists");
    }
  }
}
