package mime.model.operations;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Histogram {

  // Instance variables for the image and its histograms
  private BufferedImage img;
  private int[] redHistogram;
  private int[] greenHistogram;
  private int[] blueHistogram;

  public Histogram(BufferedImage img) {
    this.img = img;
    this.redHistogram = new int[256];
    this.greenHistogram = new int[256];
    this.blueHistogram = new int[256];
  }

  public BufferedImage createHistogram() {
    // Calculate the color occurrence for each pixel
    calculateHistograms();

    // Create a new image to draw the histograms
    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
    drawHistograms(histogramImage);

    return histogramImage;
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

  private void drawHistograms(BufferedImage histogramImage) {
    Graphics2D g = histogramImage.createGraphics();

    // Set the background to white
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, histogramImage.getWidth(), histogramImage.getHeight());

    // Calculate the maximum value across all histograms for consistent scaling
    int maxHistogramValue = findOverallMax(redHistogram, greenHistogram, blueHistogram);

    // Optional: draw a grid
    g.setColor(Color.GRAY);
    for (int i = 0; i <= 256; i += 16) {
      g.drawLine(i, 0, i, 255); // vertical lines
      g.drawLine(0, i, 255, i); // horizontal lines
    }

    // Draw the histograms as line graphs
    drawHistogramLineGraph(g, redHistogram, new Color(255, 0, 0, 128), maxHistogramValue);
    drawHistogramLineGraph(g, greenHistogram, new Color(0, 255, 0, 128), maxHistogramValue);
    drawHistogramLineGraph(g, blueHistogram, new Color(0, 0, 255, 128), maxHistogramValue);

    g.dispose(); // Dispose graphics to release resources
  }

  private void drawHistogramLineGraph(Graphics2D g, int[] histogram, Color color, int maxHistogramValue) {
    g.setColor(color);
    g.setStroke(new BasicStroke(2.0f));

    for (int i = 0; i < 255; i++) {
      int value = (histogram[i] * 255) / maxHistogramValue;
      int valueNext = (histogram[i + 1] * 255) / maxHistogramValue;
      g.drawLine(i, 255 - value, i + 1, 255 - valueNext);
    }
  }

  private int findOverallMax(int[]... histograms) {
    int max = 0;
    for (int[] histogram : histograms) {
      max = Math.max(max, max(histogram));
    }
    return max;
  }

  private int max(int[] array) {
    int max = 0;
    for (int value : array) {
      max = Math.max(max, value);
    }
    return max;
  }

}
