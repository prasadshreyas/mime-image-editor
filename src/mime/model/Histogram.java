package mime.model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Histogram {

  public static BufferedImage createHistogram(BufferedImage img) {
    // Create histograms arrays
    int[] redHistogram = new int[256];
    int[] greenHistogram = new int[256];
    int[] blueHistogram = new int[256];

    // Calculate the color occurrence for each pixel
    calculateHistograms(img, redHistogram, greenHistogram, blueHistogram);

    // Create a new image to draw the histograms
    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
    drawHistograms(histogramImage, redHistogram, greenHistogram, blueHistogram);

    return histogramImage;
  }

  static void calculateHistograms(BufferedImage img, int[] redHistogram, int[] greenHistogram, int[] blueHistogram) {
    for (int i = 0; i < img.getWidth(); i++) {
      for (int j = 0; j < img.getHeight(); j++) {
        Color pixelColor = new Color(img.getRGB(i, j), true);
        redHistogram[pixelColor.getRed()]++;
        greenHistogram[pixelColor.getGreen()]++;
        blueHistogram[pixelColor.getBlue()]++;
      }
    }
  }
  private static void drawHistograms(BufferedImage histogramImage, int[] redHistogram, int[] greenHistogram, int[] blueHistogram) {
    Graphics2D g = histogramImage.createGraphics();

    boolean backgroundWhite = true;
    boolean drawGrid = true;


    // Set the background to white
    if (backgroundWhite) {
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, histogramImage.getWidth(), histogramImage.getHeight());
    }



    // Calculate the maximum value across all histograms for consistent scaling
    int maxHistogramValue = findOverallMax(redHistogram, greenHistogram, blueHistogram);

    // Optional: draw a grid
    if (drawGrid) {
      g.setColor(Color.GRAY);
      for (int i = 0; i <= 256; i += 16) {
        g.drawLine(i, 0, i, 255); // vertical lines
        g.drawLine(0, i, 255, i); // horizontal lines
      }
    }


    // Draw the histograms as line graphs
    drawHistogramLineGraph(g, redHistogram, new Color(255, 0, 0, 128), maxHistogramValue);
    drawHistogramLineGraph(g, greenHistogram, new Color(0, 255, 0, 128), maxHistogramValue);
    drawHistogramLineGraph(g, blueHistogram, new Color(0, 0, 255, 128), maxHistogramValue);

    g.dispose(); // Dispose graphics to release resources
  }

  private static void drawHistogramLineGraph(Graphics2D g, int[] histogram, Color color, int maxHistogramValue) {
    g.setColor(color);

    // Set the stroke to increase the line thickness
    g.setStroke(new BasicStroke(2.0f)); // Set the line thickness to 2.0f, you can change this value as needed

    // Now draw the line graph
    for (int i = 0; i < 255; i++) { // Draw line connecting points i and i+1
      int value = (histogram[i] * 255) / maxHistogramValue; // scale value to image height
      int valueNext = (histogram[i + 1] * 255) / maxHistogramValue; // scale next value to image height
      g.drawLine(i, 255 - value, i + 1, 255 - valueNext);
    }
  }

  private static int findOverallMax(int[]... histograms) {
    int max = 0;
    for (int[] histogram : histograms) {
      for (int value : histogram) {
        max = Math.max(max, value);
      }
    }
    return max;
  }

  private static int max(int[] array) {
    int max = 0;
    for (int value : array) {
      max = Math.max(max, value);
    }
    return max;
  }


}
