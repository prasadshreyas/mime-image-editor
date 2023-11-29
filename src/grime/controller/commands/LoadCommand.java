package grime.controller.commands;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import grime.model.Model;

/**
 * This class represents the command to load an image from a file.
 */
public class LoadCommand implements Command {
  private final Model model;

  /**
   * Constructor for LoadCommand that takes in a model.
   *
   * @param model model to be used
   */
  public LoadCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) throws Exception {
      try {
        ArgValidator.validate(args, 2);
        String filePath = args[0];
        String fileName = args[1];
        BufferedImage image = load(filePath);
        model.setImage(image, fileName);
      }
      catch (Exception e) {
        throw new IllegalArgumentException("Invalid arguments for load command.");
      }

  }


  /**
   * Loads an image from the specified file path.
   *
   * @param filePath the path of the file to load.
   * @return the loaded image.
   * @throws IOException if the file does not exist or cannot be read.
   */
  private BufferedImage load(String filePath) throws IOException {
    String fileExtension = getFileExtension(filePath);

    if ("ppm".equalsIgnoreCase(fileExtension)) {
      return loadPPM(filePath);
    } else {
      File imageFile = new File(filePath);
      if (!imageFile.exists()) {
        throw new IOException("File does not exist at path: " + filePath
        );
      }
      BufferedImage loadedImage = ImageIO.read(imageFile);
      if (loadedImage == null) {
        throw new IOException("Unsupported file format or read error.");
      }
      return loadedImage;
    }
  }

  /**
   * Loads a PPM image from the specified file path.
   *
   * @param filePath the path of the file to load.
   * @return the loaded image.
   * @throws IOException if the file does not exist or cannot be read.
   */
  private BufferedImage loadPPM(String filePath) throws IOException {
    try (Scanner sc = new Scanner(new FileInputStream(filePath))) {
      String format = sc.next().trim();
      if (!format.equals("P3")) {
        throw new IOException("Invalid PPM file: must start with P3");
      }
      int width = sc.nextInt();
      int height = sc.nextInt();
      int maxValue = sc.nextInt();
      double scale = 255.0 / maxValue;

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = (int) (sc.nextInt() * scale);
          int g = (int) (sc.nextInt() * scale);
          int b = (int) (sc.nextInt() * scale);
          int rgb = (r << 16) | (g << 8) | b;
          image.setRGB(j, i, rgb);
        }
      }
      return image;
    } catch (FileNotFoundException e) {
      throw new IOException("File " + filePath + " not found!");
    }
  }

  private String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex > 0) {
      return fileName.substring(dotIndex + 1);
    } else {
      return ""; // No extension found
    }
  }
}
