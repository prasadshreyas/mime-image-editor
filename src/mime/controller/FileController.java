package mime.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import mime.model.Model;
import mime.view.View;

/**
 * This class represents a controller for handling file-based input.
 */
public class FileController implements Controller {
  private final Model model;
  private final View view;
  private final File inputFile;

  /**
   * Constructs a FileController object.
   *
   * @param model     the model to be used by the controller.
   * @param view      the view to be used by the controller.
   * @param inputFile the file to be read by the controller.
   */
  public FileController(Model model, View view, File inputFile) {
    this.model = model;
    this.view = view;
    this.inputFile = inputFile;
  }

  /**
   * Runs the controller by reading commands from a file and executing them.
   */
  @Override
  public void run() {
    try (Scanner scanner = new Scanner(inputFile)) {
      boolean quit = false;
      while (!quit && scanner.hasNextLine()) {
        List<String> lineArgs = List.of(scanner.nextLine().split("\\s+"));
        if (lineArgs.isEmpty()) {
          handleException(new IllegalArgumentException("Command cannot be empty."));
          continue;
        }
        try {
          quit = executeCommand(lineArgs);
        } catch (Exception e) {
          handleException(e);
        }
      }
    } catch (FileNotFoundException e) {
      view.display("File not found: " + inputFile.getPath());
    }
  }

  /**
   * Executes a command based on the provided arguments.
   *
   * @param lineArgs the command arguments
   * @return true if the quit command is executed, false otherwise
   */
  private boolean executeCommand(List<String> lineArgs) {
    String command = lineArgs.get(0);
    try {
      if (CLIController.getCommand(command) != null) {
        CLIController.getCommand(command).execute(lineArgs.subList(1, lineArgs.size()).toArray(new String[0]));
      } else if (CLIController.QUIT_COMMAND.equals(command)) {
        return true;
      } else {
        throw new IllegalArgumentException("Invalid command. Enter a valid command or 'quit' to exit.");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return false;
  }

  /**
   * Handles exceptions that occur during command execution.
   *
   * @param e the exception to be handled
   */
  private void handleException(Exception e) {
    view.display(e.getMessage());
  }
}
