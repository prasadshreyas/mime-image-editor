package mime;

import java.io.File;
import java.util.Scanner;

import mime.controller.Controller;
import mime.model.Model;
import mime.model.RGBModel;
import mime.utils.ArgumentParser;
import mime.utils.Config;
import mime.utils.ConfigurationException;
import mime.view.CommandLineView;
import mime.view.View;

/**
 * This class represents the main class for the program.
 */
public class Main {

  /**
   * The main method creates the model, view, and controller and runs the program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws ConfigurationException {

    Config config = ArgumentParser.parse(args);

    Scanner scanner;

    // either 0 or 2 arguments are accepted
    if (args.length != 0 && args.length != 2) {
      System.out.println("Usage: java -jar mime.jar -file <filepath>");
      System.exit(1);
    }

    // if 2, check that the first argument is "-file"
    if (args.length == 2 && !args[0].equals("-file")) {
      System.out.println("Usage: java -jar mime.jar -file <filepath>");
      System.exit(1);
    }

    // check if the file is readable
    if (args.length == 2) {
      String filePath = args[1];
      java.io.File file = new java.io.File(filePath);
      if (!file.exists() || !file.canRead()) {
        System.out.println("File does not exist or is not readable.");
        System.exit(1);
      }
    }
    scanner = new Scanner(System.in); // Read from console by default
    if (args.length == 2) {
      try {
        scanner = new Scanner(new File(args[1])); // Change to read from file
      } catch (Exception e) {
        System.out.println("File does not exist or is not readable.");
        System.exit(1);
      }
    }

    Model model = new RGBModel();
    View commandLineView = new CommandLineView();
    Controller controller = new Controller(model, commandLineView, scanner,
            args.length == 2); // Pass a flag to indicate file mode

    controller.run();
  }
}