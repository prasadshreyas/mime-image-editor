package mime;

import java.io.File;
import java.util.Scanner;

import mime.controller.CLIController;
import mime.controller.Controller;
import mime.model.Model;
import mime.model.RGBModel;
import mime.utils.ArgumentParser;
import mime.utils.Config;
import mime.utils.ConfigurationException;
import mime.view.CommandLineView;
import mime.view.View;
import mime.view.ViewFactory;

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
      Controller controller = new CLIController(model, view, new Scanner(System.in), false);
      controller.run();
    } catch (ConfigurationException e) {
      System.err.println("Failed to start the application: " + e.getMessage());
      System.exit(1);
    }

  }
}