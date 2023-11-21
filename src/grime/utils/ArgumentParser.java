package grime.utils;

/**
 * This class parses the command-line arguments and creates a Config object.
 */
public class ArgumentParser {


  /**
   * Parses the provided command-line arguments and creates a Config object.
   *
   * @param args The command-line arguments passed to the application.
   * @return A Config object representing the parsed configuration settings.
   */
  public static Config parse(String[] args) throws ConfigurationException {
    Config config = new Config();

    if (args.length == 0) {
      // No arguments, default to GUI mode
      config.setGraphicalMode(true);
    } else if (args.length == 1 && "-text".equals(args[0])) {
      // Text mode
      config.setCommandLineMode(true);
    } else if (args.length == 2 && "-file".equals(args[0])) {
      // File mode
      String filePath = args[1];
      checkFileReadability(filePath);
      config.setFilePath(filePath);
    } else {
      throw new ConfigurationException("Invalid arguments."
              + " Usage: java -jar Program.jar [-file path-of-script-file | -text]");
    }

    return config;
  }

  /**
   * Checks if the file exists and is readable.
   *
   * @param filePath The path of the file to check.
   * @throws ConfigurationException If the file does not exist or is not readable.
   */
  private static void checkFileReadability(String filePath) throws ConfigurationException {
    java.io.File file = new java.io.File(filePath);
    if (!file.exists()) {
      throw new ConfigurationException("File does not exist: " + filePath);
    }
    if (!file.canRead()) {
      throw new ConfigurationException("File is not readable: " + filePath);
    }
  }
}