package grime.model.operations;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class represents the b, m, and w levels adjustment operation.
 */
public class LevelAdjuster {

  /**
   * Adjusts the levels of the image.
   * @param img image to be adjusted.
   * @param black black level.
   * @param mid mid level.
   * @param white white level.
   * @return BufferedImage adjusted image.
   */
  public BufferedImage adjustLevels(BufferedImage img, int black, int mid, int white) {

    if (black == 0 && mid == 128 && white == 255) {
      return img; // No adjustment needed
    }

    double[] coefficients = fitQuadraticCurve(black, mid, white);
    BufferedImage adjustedImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        Color originalColor = new Color(img.getRGB(i, j)); // Support alpha channel
        int red = adjustColorValue(originalColor.getRed(), coefficients);
        int green = adjustColorValue(originalColor.getGreen(), coefficients);
        int blue = adjustColorValue(originalColor.getBlue(), coefficients);
        Color adjustedColor = new Color(red, green, blue);
        adjustedImage.setRGB(i, j, adjustedColor.getRGB());
      }
    }

    return adjustedImage;
  }

  private double[] fitQuadraticCurve(int shadow, int mid, int highlight) {
    double v = Math.pow(shadow, 2) * (mid - highlight) - shadow * (Math.pow(mid, 2)
            - Math.pow(highlight, 2)) + mid * Math.pow(highlight, 2) - highlight * Math.pow(mid, 2);

    if (v == 0) {
      throw new ArithmeticException("Invalid shadow, mid, "
              + "and highlight values: they must not be collinear.");
    }

    double aA = -shadow * (128 - 255) + 128 * highlight - 255 * mid;
    double aB = Math.pow(shadow, 2) * (128 - 255) + 255 * Math.pow(mid, 2) - 128
            * Math.pow(highlight, 2);
    double aC = Math.pow(shadow, 2) * (255 * mid - 128 * highlight) - shadow
            * (255 * Math.pow(mid, 2) - 128 * Math.pow(highlight, 2));

    double a = aA / v;
    double b = aB / v;
    double c = aC / v;

    return new double[]{a, b, c};
  }

  private int adjustColorValue(int colorValue, double[] coefficients) {
    double a = coefficients[0];
    double b = coefficients[1];
    double c = coefficients[2];

    double adjustedValue = a * Math.pow(colorValue, 2) + b * colorValue + c;
    adjustedValue = Math.min(Math.max(adjustedValue, 0), 255);

    return (int) Math.round(adjustedValue);
  }

}
