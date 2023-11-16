package mime.model.operations;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class represents the color correction operation.
 */
public class ColorCorrection {
  int[] redHistogram;
  int[] greenHistogram;
  int[] blueHistogram;
  BufferedImage img;

  /**
   * Constructs a ColorCorrection object.
   */
  public ColorCorrection() {
    this.redHistogram = new int[256];
    this.greenHistogram = new int[256];
    this.blueHistogram = new int[256];
  }

  /**
   * Corrects the color of the image.
   *
   * @param img image to be corrected.
   * @return BufferedImage corrected image.
   */
  public BufferedImage correctColor(BufferedImage img) {

    this.img = img;

    calculateHistograms();


    int redPeak = findMeaningfulPeak(redHistogram);
    int greenPeak = findMeaningfulPeak(greenHistogram);
    int bluePeak = findMeaningfulPeak(blueHistogram);

    int averagePeak = (redPeak + greenPeak + bluePeak) / 3;

    BufferedImage correctedImage = new BufferedImage(img.getWidth(), img.getHeight(),
            BufferedImage.TYPE_INT_RGB);

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

  private int findMeaningfulPeak(int[] histogram) {
    int peakIndex = 0;
    int peakValue = 0;

    for (int i = 10; i <= 245; i++) {
      if (histogram[i] > peakValue) {
        peakValue = histogram[i];
        peakIndex = i;
      }
    }

    return peakIndex;
  }

  private int clampColorValue(int value) {
    return Math.min(Math.max(value, 0), 255);
  }
}
