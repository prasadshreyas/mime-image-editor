package grime.view;

import java.awt.event.ActionListener;
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
  void displayMessage(String message);

  /**
   * Makes the view visible.
   */
  void makeVisible();


  /**
   * Gets the command from the user.
   *
   * @return the string representing the command.
   */
  String getCommand();

  /**
   * Adds the given listener to the view.
   *
   * @param actionCommand the action command of the button.
   * @param listener      the listener to be added.
   */
  void addListener(String actionCommand, ActionListener listener);


  /**
   * Sets the image to the given BufferedImage.
   *
   * @param image    BufferedImage to be set.
   * @param imageName name of the image.
   * @return
   */
  void setImage(BufferedImage image, String imageName);

  /**
   * Refreshes the view.
   *
   * @param imageName name of the image.
   */
  void refresh(String imageName);


}