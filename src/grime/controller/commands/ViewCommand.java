package grime.controller.commands;

import grime.model.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * This class represents the command to save an image to a file.
 */
public class ViewCommand implements Command {
  private final Model model;

  /**
   * Constructor for SaveCommand that takes in a model.
   *
   * @param model model to be used
   */
  public ViewCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) throws Exception {
    ArgValidator.validate(args, 2);
    String filePath = args[0];
    String fileName = args[1];
    BufferedImage image = model.getImage(fileName);
    save(filePath, image);
  }


  private void savePPM(String filePath, BufferedImage image) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileOutputStream(filePath))) {
      // Write the header
      pw.println("P3");
      pw.println(image.getWidth() + " " + image.getHeight());
      pw.println(255); // Assuming the max color value is 255

      // Write pixel data
      for (int i = 0; i < image.getHeight(); i++) {
        for (int j = 0; j < image.getWidth(); j++) {
          int rgb = image.getRGB(j, i);
          int r = (rgb >> 16) & 0xFF;
          int g = (rgb >> 8) & 0xFF;
          int b = rgb & 0xFF;
          pw.println(r + " " + g + " " + b);
        }
      }
    } catch (FileNotFoundException e) {
      throw new IOException("File " + filePath + " could not be opened for writing.");
    }
  }

  /**
   * Saves the given image to the specified file path.
   *
   * @param filePath the path of the file to save.
   * @param image    the image to save.
   * @throws IOException if the file cannot be created or written to.
   */
  private void save(String filePath, BufferedImage image) throws IOException {
    String fileExtension = getFileExtension(filePath);

    if ("ppm".equalsIgnoreCase(fileExtension)) {
      savePPM(filePath, image);
    } else {

      File outputFile = new File(filePath);

      if (!outputFile.createNewFile()) {
        throw new IOException("File could not be created.");
      }


      ImageIO.write(image, fileExtension, outputFile);

      if (!outputFile.exists()) {
        throw new IOException("File could not be written.");
      }
    }
  }

  private String getFileExtension(String filePath) {
    int dotIndex = filePath.lastIndexOf('.');
    if (dotIndex > 0) {
      return filePath.substring(dotIndex + 1);
    } else {
      return ""; // No extension found
    }
  }
}
