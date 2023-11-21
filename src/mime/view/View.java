package mime.view;

import java.awt.image.BufferedImage;

/**
 * This interface represents view. It is used to display output to the user.
 */
public interface View {

  /**
   * Displays the given message to the user.
   *
   * @param message the message to be displayed to the user.
   */
  void display(String message);
}