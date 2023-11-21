package grime.controller;

import java.io.File;
import java.util.Scanner;

import grime.model.Model;
import grime.utils.Config;
import grime.view.View;

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
      return new GUIController(model, view);
    } else if (config.isCommandLineMode()) {
      return new CLIController(model, view, new Scanner(System.in));
    } else {
      return new FileController(model, view, new File(config.getFilePath()));
    }
  }

}
