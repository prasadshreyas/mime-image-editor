package mime.model;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorCorrection {

  // Method to correct the color of an input image
  public static BufferedImage correctColor(BufferedImage img) {
    // Calculate histograms
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];
    Histogram.calculateHistograms(img, redHistogram, greenHistogram, blueHistogram);

    // Find peaks for color correction
    int redPeak = findMeaningfulPeak(redHistogram);
    int greenPeak = findMeaningfulPeak(greenHistogram);
    int bluePeak = findMeaningfulPeak(blueHistogram);

    // Compute the average peak value
    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    // Create a new image for the corrected output
    BufferedImage correctedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

    // Correct the color of each pixel
    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        Color originalColor = new Color(img.getRGB(i, j));
        int redValue = clampColorValue(originalColor.getRed() + averagePeak - redPeak);
        int greenValue = clampColorValue(originalColor.getGreen() + averagePeak - greenPeak);
        int blueValue = clampColorValue(originalColor.getBlue() + averagePeak - bluePeak);
        Color correctedColor = new Color(redValue, greenValue, blueValue);
        correctedImage.setRGB(i, j, correctedColor.getRGB());
      }
    }

    return correctedImage;
  }

  // Helper method to find the meaningful peak within the given range
  private static int findMeaningfulPeak(int[] histogram) {
    int peakIndex = 0;
    int peakValue = 0;

    for (int i = 10; i <= 245; i++) { // Exclude the extreme ends
      if (histogram[i] > peakValue) {
        peakValue = histogram[i];
        peakIndex = i;
      }
    }

    return peakIndex;
  }

  // Helper method to clamp color values to valid range
  private static int clampColorValue(int value) {
    return Math.min(Math.max(value, 0), 255);
  }
}
