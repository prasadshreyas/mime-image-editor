package mime.controller.commands;
import mime.model.Model;

/**
 * This class represents the HistogramCommand class that implements the Command interface. It
 * represents the command that creates a histogram of an image.
 */
public class HistogramCommand implements Command {
  private final Model model;

  /**
   * Constructor for HistogramCommand that takes in a model.
   * @param model
   */
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
