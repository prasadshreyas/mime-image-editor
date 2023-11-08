package mime.controller.commands;


/**
 * ArgValidator class for the program. This class is responsible for validating the arguments
 * passed to the program.
 */
class ArgValidator {
  /**
   * Validates the arguments passed to the program.
   *
   * @param args           command line arguments
   * @param expectedLength expected number of arguments
   * @throws IllegalArgumentException if the number of arguments is not equal to the expected
   *                                  number of arguments
   */
  static void validate(String[] args, int expectedLength) {
    if (args.length != expectedLength) {
      throw new IllegalArgumentException("Invalid number of arguments");
    }
  }
}