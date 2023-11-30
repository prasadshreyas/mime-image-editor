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
   * Gets the input from the user by displaying a prompt and reading the input.
   *
   * @return the string representing the input from the user.
   */
  String getInput(String prompt);

  /**
   * Adds the given listener to the view.
   *
   * @param actionCommand the action command of the button.
   * @param listener      the listener to be added.
   */
  void addListener(String actionCommand, ActionListener listener);

  /**
   * Updates the view with the given data.
   *
   * @param viewType the type of view to be updated.
   * @param data     the data to be used to update the view.
   */
  void updateView(String viewType, Object data);

  BufferedImage getCurrentImage();
}


