package mime.model.operations;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class LevelAdjuster {

  public BufferedImage adjustLevels(BufferedImage img, int black, int mid, int white) throws ArithmeticException, IllegalArgumentException {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Black, mid, and white values must be between 0 and 255");
    }

    if (black >= mid || mid >= white) {
      throw new IllegalArgumentException("Black must be less than mid, and mid must be less than white");
    }

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

    double[] fitQuadraticCurve(int shadow, int mid, int highlight) {
    double A = Math.pow(shadow, 2) * (mid - highlight) - shadow * (Math.pow(mid, 2) - Math.pow(highlight, 2)) + mid * Math.pow(highlight, 2) - highlight * Math.pow(mid, 2);

    if (A == 0) {
      throw new ArithmeticException("Invalid shadow, mid, and highlight values: they must not be collinear.");
    }

    double A_a = -shadow * (128 - 255) + 128 * highlight - 255 * mid;
    double A_b = Math.pow(shadow, 2) * (128 - 255) + 255 * Math.pow(mid, 2) - 128 * Math.pow(highlight, 2);
    double A_c = Math.pow(shadow, 2) * (255 * mid - 128 * highlight) - shadow * (255 * Math.pow(mid, 2) - 128 * Math.pow(highlight, 2));

    double a = A_a / A;
    double b = A_b / A;
    double c = A_c / A;

    return new double[]{a, b, c};
  }

  int adjustColorValue(int colorValue, double[] coefficients) {
    double a = coefficients[0];
    double b = coefficients[1];
    double c = coefficients[2];

    double adjustedValue = a * Math.pow(colorValue, 2) + b * colorValue + c;
    adjustedValue = Math.min(Math.max(adjustedValue, 0), 255);

    return (int) Math.round(adjustedValue);
  }

}
