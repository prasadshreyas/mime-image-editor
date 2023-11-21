package mime.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import mime.model.Model;
import mime.view.View;

/**
 * This class represents a Command Line Interface (CLI) controller for the program.
 */
public class CLIController2 extends AbstractController {
  private Scanner scanner;

  /**
   * Constructs a CLIController object.
   *
   * @param model   the model to be used by the controller.
   * @param view    the view to be used by the controller.
   * @param scanner the scanner to be used by the controller.
   */
  public CLIController2(Model model, View view, Scanner scanner) {
    super(model, view);
    this.scanner = scanner;
  }

  @Override
  protected List<String> readInput() {
    view.display("Enter a command:");
    return scanner.hasNextLine() ? Arrays.asList(scanner.nextLine().split("\\s+")) : null;
  }
}
