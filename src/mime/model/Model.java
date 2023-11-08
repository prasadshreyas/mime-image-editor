package mime.model;

import java.awt.image.BufferedImage;

/**
 * This interface represents the operations offered by the model of the image.
 */
public interface Model {

  /**
   * Sets the image to the given BufferedImage.
   *
   * @param image    BufferedImage to be set.
   * @param fileName name of the image.
   * @return
   */
  void setImage(BufferedImage image, String fileName);

  /**
   * Returns the BufferedImage of the image.
   *
   * @param fileName name of the image.
   * @return BufferedImage of the image.
   */
  BufferedImage getImage(String fileName);

  /**
   * Brightens the image by the given increment.
   *
   * @param increment    amount to brighten the image by.
   * @param imageName    name of the image.
   * @param newImageName name of the new image.
   */
  void brighten(int increment, String imageName, String newImageName);

  /**
   * Blurs the image.
   *
   * @param imageName    name of the image.
   * @param newImageName name of the new image.
   * @return
   */
  void blur(String imageName, String newImageName);

  /**
   * Sharpens the image.
   *
   * @param imageName    name of the image.
   * @param newImageName name of the new image.
   */
  void sharpen(String imageName, String newImageName);

  /**
   * Flips the image vertically.
   *
   * @param imageName    name of the image.
   * @param newImageName name of the new image.
   */
  void flipVertically(String imageName, String newImageName);

  /**
   * Flips the image horizontally.
   *
   * @param imageName    name of the image.
   * @param newImageName name of the new image.
   */
  void flipHorizontally(String imageName, String newImageName);

  /**
   * Applies a sepia filter to the image.
   *
   * @param imageName    name of the image.
   * @param newImageName name of the new image.
   */
  void sepia(String imageName, String newImageName);

  /**
   * Splits the image into its RGB components.
   *
   * @param imageName      name of the image.
   * @param redImageName   name of the red component of the image.
   * @param greenImageName name of the green component of the image.
   * @param blueImageName  name of the blue component of the image.
   */
  void rgbSplit(String imageName, String redImageName, String greenImageName,
                String blueImageName);

  /**
   * Combines the red, green and blue components of an image.
   *
   * @param imageName      the name of the image to be manipulated.
   * @param imageNameRed   the name of the red component of the image.
   * @param imageNameGreen the name of the green component of the image.
   * @param imageNameBlue  the name of the blue component of the image.
   */
  void rgbCombine(String imageName, String imageNameRed, String imageNameGreen,
                  String imageNameBlue);

  /**
   * Get the color component of the image.
   *
   * @param imageName name of the image.
   * @param component color component of the image.
   */
  void getColorComponent(String imageName, Channel component, String newImageName);

  /**
   * Get the brightness component of the image.
   *
   * @param imageName  name of the image.
   * @param brightness brightness component of the image.
   */
  void getBrightnessComponent(String imageName, Brightness brightness, String newImageName);

  boolean containsImage(String arg);

  void compressImage(String imageName, int percentage, String newImageName);


  /**
   * Enum representing the color components of the image.
   */
  public enum Channel {
    RED, GREEN, BLUE
  }

  /**
   * This enum represents the different color components of an image.
   */
  enum Brightness {
    LUMA, INTENSITY, VALUE;
  }
}
