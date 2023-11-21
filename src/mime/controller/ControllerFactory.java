package mime.controller;

import java.io.File;
import java.util.Scanner;

import mime.model.Model;
import mime.utils.Config;
import mime.view.View;

/**
 * This class represents the controller factory for the program.
 */
public class ControllerFactory {
  /**
   * Creates a controller based on the given configuration.
   *
   * @param config the configuration
   * @return the controller
   */
  public static Controller createController(Config config, Model model, View view) {
    if (config.isGraphicalMode()) {
      return new GUIController();
    } else if (config.isCommandLineMode()) {
      return new CLIController(model, view, new Scanner(System.in));
    } else {
      return new FileController(model, view, new File(config.getFilePath()));
    }
  }

}
