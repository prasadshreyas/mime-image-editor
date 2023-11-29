package grime;

import javax.swing.*;

import grime.controller.Controller;
import grime.controller.ControllerFactory;
import grime.model.Model;
import grime.model.RGBModel;
import grime.utils.ArgumentParser;
import grime.utils.Config;
import grime.utils.ConfigurationException;
import grime.view.View;
import grime.view.ViewFactory;

/**
 * This class represents the main class for the program.
 */
public class Main {

  /**
   * The main method creates the model, view, and controller and runs the program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args){
    try {
      Config config = ArgumentParser.parse(args);
      View view = ViewFactory.createView(config);
      Model model = new RGBModel();
      if (config.isGraphicalMode()) {
        SwingUtilities.invokeLater(() -> {
          Controller controller = ControllerFactory.createController(config, model, view);
          controller.run(); // Assuming your controller has a run method to start the GUI
        });
      } else {
        // For CLI or File based, you can proceed normally as they don't require the EDT
        Controller controller = ControllerFactory.createController(config, model, view);
        controller.run();
      }
    } catch (ConfigurationException e) {
      System.err.println("Failed to start the application: " + e.getMessage());
      System.exit(1);
    }

  }
}