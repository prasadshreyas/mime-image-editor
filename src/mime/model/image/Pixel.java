package mime.model.image;

import java.util.Objects;


/**
 * The Pixel class represents a pixel with Red, Green, and Blue color components.
 * RGB values are clamped to the range 0-255.
 */
public class Pixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a Pixel with the given RGB values and clamps them to the range 0-255.
   * @param red  the red component of the pixel.
   * @param green the green component of the pixel.
   * @param blue the blue component of the pixel.
   */
  Pixel(int red, int green, int blue) {
    this.red = clamp(red);
    this.green = clamp(green);
    this.blue = clamp(blue);
  }

  int getRed() {
    return red;
  }

  int getGreen() {
    return green;
  }

  int getBlue() {
    return blue;
  }

  private int clamp(int value) {
    return Math.min(Math.max(0, value), 255);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Pixel) {
      Pixel other = (Pixel) obj;
      return this.red == other.red && this.green == other.green && this.blue == other.blue;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }

  @Override
  public String toString() {
    return "(" + red + ", " + green + ", " + blue + ")";
  }

}