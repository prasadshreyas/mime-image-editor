package grime.view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * This class represents the command line view.
 */
public class CommandLineView implements View {

  private final Appendable out;


  /**
   * Constructs a CommandLineView object.
   */
  public CommandLineView() {
    this.out = System.out;
  }

  @Override
  public void displayMessage(String message) {
    try {
      this.out.append(message).append("\n");
    } catch (Exception e) {
      throw new IllegalStateException("Failed to display message: " + e.getMessage());
    }
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public String getInput(String prompt) {
    return null;
  }


  public void addListener(String actionCommand, ActionListener listener) {
    // TODO Auto-generated method stub
  }

  @Override
  public void updateView(String viewType, Object data) {

  }


}
