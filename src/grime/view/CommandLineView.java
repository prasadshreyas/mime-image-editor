package grime.view;
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
  public void display(String message) {
    try {
      this.out.append(message).append("\n");
    } catch (Exception e) {
      throw new IllegalStateException("Failed to display message: " + e.getMessage());
    }
  }


}
