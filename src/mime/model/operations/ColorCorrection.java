package mime.model.operations;
import java.awt.*;
import java.awt.image.BufferedImage;

import mime.model.operations.Histogram;

public class ColorCorrection {
  int[] redHistogram;
  int[] greenHistogram;
  int[] blueHistogram;
  BufferedImage img;
  public  ColorCorrection() {
    this.redHistogram = new int[256];
    this.greenHistogram = new int[256];
    this.blueHistogram = new int[256];
  }

  // Method to correct the color of an input image
  public BufferedImage correctColor(BufferedImage img) {
    // Calculate histograms
    calculateHistograms();

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

  private void calculateHistograms() {
    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        Color pixelColor = new Color(img.getRGB(i, j), true);
        redHistogram[pixelColor.getRed()]++;
        greenHistogram[pixelColor.getGreen()]++;
        blueHistogram[pixelColor.getBlue()]++;
      }
    }
  }

  // Helper method to find the meaningful peak within the given range
  private int findMeaningfulPeak(int[] histogram) {
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
  private int clampColorValue(int value) {
    return Math.min(Math.max(value, 0), 255);
  }
}
