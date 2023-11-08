package mime;

import java.util.Scanner;

import mime.controller.Controller;
import mime.model.Model;
import mime.model.RGBModel;
import mime.view.CommandLineView;
import mime.view.View;

public class Main {

  /**
   * ime.Main method for the program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    Model model = new RGBModel();
    View commandLineView = new CommandLineView();
    Controller controller = new Controller(model, commandLineView, scanner);

    controller.run();
  }
}