package mime.controller.commands;

import mime.controller.commands.Command;
import mime.model.Model;

public class HistogramCommand implements Command {
  private final Model model;

  public HistogramCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {

    int[] expectedLength = {2};
    ArgValidator.validate(args, expectedLength);

    if (!model.containsImage(args[0])) {
      throw new IllegalArgumentException("No such image exists");
    }
    if (model.containsImage(args[1])) {
      throw new IllegalArgumentException("New Image already exists");
    }

    if (args.length == 2) {
      model.histogram(args[0], args[1]);
    }
  }
}
