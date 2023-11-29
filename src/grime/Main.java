package grime;

import grime.controller.CLIController;
import grime.controller.Controller;
import grime.controller.ControllerFactory;
import grime.controller.FileController;
import grime.model.Model;
import grime.model.RGBModel;
import grime.utils.ArgumentParser;
import grime.utils.Config;
import grime.utils.ConfigurationException;
import grime.view.CommandLineView;
import grime.view.View;
import grime.view.ViewFactory;

import java.util.Scanner;

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
        Controller controller = ControllerFactory.createController(config, model, view);
        controller.run();
    } catch (ConfigurationException e) {
      System.err.println("Failed to start the application: " + e.getMessage());
      System.exit(1);
    }

  }
}