package grime.model.operations;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class represents the test suite for the Histogram operation.
 */
public class HistogramTest {

  @Test
  public void testCreateHistogramWithSimpleImage() {
    BufferedImage testImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Histogram histogram = new Histogram(testImage);
    BufferedImage histogramImage = histogram.createHistogram();
    assertNotNull("Histogram image should not be null", histogramImage);
  }

  // Correct Histogram Calculation
  @Test
  public void testCreateHistogramWithSimpleImageCorrectHistogram() {
    BufferedImage testImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    testImage.setRGB(0, 0, 0xFF0000);
    Histogram histogram = new Histogram(testImage);
    BufferedImage histogramImage = histogram.createHistogram();
    int[] redHistogram = histogram.getRedHistogram();
    assertEquals("Red histogram should have 1 red pixel", 1,
            redHistogram[255]);

    // Blue
    int[] blueHistogram = histogram.getBlueHistogram();
    assertEquals("Blue histogram should have 1 blue pixel", 1,
            blueHistogram[0]);

    // Green
    int[] greenHistogram = histogram.getGreenHistogram();
    assertEquals("Green histogram should have 1 green pixel", 1,
            greenHistogram[0]);
  }

  // Empty Histogram Calculation

  @Test
  public void testHistogramWithDifferentImageSizes() {
    // Test with a very small image
    BufferedImage smallImage = new BufferedImage(1, 1,
            BufferedImage.TYPE_INT_ARGB);
    Histogram histogram = new Histogram(smallImage);
    BufferedImage histogramImage = histogram.createHistogram();
    assertNotNull("Histogram image should not be null", histogramImage);


    // Test with a moderately sized image
    BufferedImage mediumImage = new BufferedImage(100, 100,
            BufferedImage.TYPE_INT_ARGB);
    histogram = new Histogram(mediumImage);
    histogramImage = histogram.createHistogram();
    assertNotNull("Histogram image should not be null", histogramImage);


    // Test with a very large image
    BufferedImage largeImage = new BufferedImage(2000, 2000,
            BufferedImage.TYPE_INT_ARGB);
    histogram = new Histogram(largeImage);
    histogramImage = histogram.createHistogram();
    assertNotNull("Histogram image should not be null", histogramImage);


    // Test with a very large image
    BufferedImage vLargeImage = new BufferedImage(20000, 20000,
            BufferedImage.TYPE_INT_ARGB);
    histogram = new Histogram(vLargeImage);
    histogramImage = histogram.createHistogram();
    assertNotNull("Histogram image should not be null", histogramImage);

  }

  @Test
  public void testHistogramWithNullImage() {
    assertNotNull("Histogram image should not be null", new Histogram(null));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testCreateHistogramWithEmptyImage() {
    BufferedImage emptyImage = new BufferedImage(0, 0, BufferedImage.TYPE_INT_ARGB);
    Histogram histogram = new Histogram(emptyImage);
    histogram.createHistogram();
  }


}