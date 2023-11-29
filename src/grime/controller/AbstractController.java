package grime.controller;

import java.util.List;

import grime.model.Model;
import grime.view.View;

/**
 * This class represents an abstract controller for the program.
 */
public abstract class AbstractController implements Controller {
  protected CommandExecutor commandExecutor;
  protected View view;

  /**
   * Constructs an AbstractController object.
   *
   * @param model the model to be used by the controller.
   * @param view  the view to be used by the controller.
   */
  public AbstractController(Model model, View view) {
    this.commandExecutor = new CommandExecutor(model);
    this.view = view;
  }

  /**
   * Reads input from the user.
   *
   * @return the input
   */
  protected abstract List<String> readInput();

  @Override
  public void run() {
    boolean quit = false;

//    while (!quit) {
//      List<String> lineArgs = readInput();
//      if (lineArgs == null || lineArgs.isEmpty()) {
//        view.display("Command cannot be empty.");
//        continue;
//      }
//      try {
//        quit = commandExecutor.executeCommand(lineArgs);
//      } catch (Exception e) {
//        view.display(e.getMessage());
//      }
//    }
      // TODO: Implement this method
  }
}
