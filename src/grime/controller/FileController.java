package grime.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import grime.model.Model;
import grime.view.View;

public class FileController extends AbstractController {
  private File inputFile;

  public FileController(Model model, View view, File inputFile) {
    super(model, view);
    this.inputFile = inputFile;
  }

  @Override
  protected List<String> readInput() {
    try (Scanner scanner = new Scanner(inputFile)) {
      if (scanner.hasNextLine()) {
        return Arrays.asList(scanner.nextLine().split("\\s+"));
      }
    } catch (FileNotFoundException e) {
      view.displayMessage("File not found: " + inputFile.getPath());
    }
    return null;
  }
}
