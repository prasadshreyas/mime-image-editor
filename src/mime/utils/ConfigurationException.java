package mime.utils;

/**
 * This class represents an exception that is thrown when the program is not configured correctly.
 */
public class ConfigurationException extends Exception {

  /**
   * Constructs a ConfigurationException with the specified detail message.
   *
   * @param message the detail message
   */
  public ConfigurationException(String message) {
    super(message);
  }
}
