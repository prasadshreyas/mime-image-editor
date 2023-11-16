package mime.controller.commands;

import mime.model.Model;

/**
 * This class represents the command to adjust the levels of an image.
 */
public class LevelAdjustCommand implements Command {
  private final Model model;

  /**
   * Constructor for LevelAdjustCommand that takes in a model.
   * @param model model to be used
   */
  public LevelAdjustCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) {
    int[] expectedLength = {5, 7};
    ArgValidator.validate(args, expectedLength);

    if (!model.containsImage(args[3])) {
      throw new IllegalArgumentException("No such image exists");
    }
    if (model.containsImage(args[4])) {
      throw new IllegalArgumentException("New Image already exists");
    }

    int black = Integer.parseInt(args[0]);
    int mid = Integer.parseInt(args[1]);
    int white = Integer.parseInt(args[2]);


    validateLevels(black, white, mid);

    model.levelAdjuster(args[3], black, mid,white, args[4]);

    if (args.length == 7) {
      validateSplitArgument(args[5]);
      int splitValue = parseSplitValue(args[6]);
      model.splitView(args[3], args[4], splitValue);
    }
  }

  private void validateLevels(int black, int white, int mid) {
    if (black < 0 || black > 255 || white < 0 || white > 255 || mid < 0 || mid > 255) {
      throw new IllegalArgumentException("Level values must be between 0 and 255");
    }
    if (black > white || black > mid || mid > white) {
      throw new IllegalArgumentException("Invalid level values: black <= mid <= white is required");
    }
  }

  private void validateSplitArgument(String splitArg) {
    if (!splitArg.equals("-split")) {
      throw new IllegalArgumentException("Invalid argument: " + splitArg);
    }
  }

  private int parseSplitValue(String splitValueStr) {
    int splitValue = Integer.parseInt(splitValueStr);
    if (splitValue < 0 || splitValue > 100) {
      throw new IllegalArgumentException("Split value must be between 0 and 100");
    }
    return splitValue;
  }
}
