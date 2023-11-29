package grime.view;

import java.awt.*;

import javax.swing.*;

/**
 * This class represents the DialogManager for the program.
 * This class is used to show messages and get input from the user.
 */
public class DialogManager {

  /**
   * Shows a message to the user.
   *
   * @param parentComponent the parent component of the dialog
   * @param message         the message to show
   */
  public void showMessage(Component parentComponent, String message) {
    JOptionPane.showMessageDialog(parentComponent, message);
  }

  /**
   * Shows a message to the user.
   *
   * @param parentComponent the parent component of the dialog
   * @param prompt          the prompt to show
   * @return the user's input
   */
  public String getInput(Component parentComponent, String prompt) {
    return JOptionPane.showInputDialog(parentComponent, prompt);
  }
}
