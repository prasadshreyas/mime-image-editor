package mime.model.operations;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the LevelAdjuster class.
 */
public class LevelAdjusterTest {

  private LevelAdjuster levelAdjuster;
  private BufferedImage testImage;

  @Before
  public void setUp() {
    levelAdjuster = new LevelAdjuster();
    testImage = createTestImage();
  }

  private BufferedImage createTestImage() {
    BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
    // Populate the image with test data
    return img;
  }

  @Test
  public void testNoAdjustmentNeeded() {
    BufferedImage result = levelAdjuster.adjustLevels(testImage, 0, 128, 255);
    assertEquals(testImage, result);
  }

  @Test(expected = ArithmeticException.class)
  public void testInvalidInput() {
    levelAdjuster.adjustLevels(testImage, 0, 0, 0);
  }

  @Test
  public void testNonDefaultLevelAdjustment() {
    BufferedImage result = levelAdjuster.adjustLevels(testImage, 30, 150, 200);
    assertNotEquals(testImage, result);
  }

  @Test
  public void testColorIntegrity() {
    BufferedImage result = levelAdjuster.adjustLevels(testImage, 50, 100, 200);
    // Check each pixel to ensure color values are within 0-255
    for (int i = 0; i < result.getWidth(); i++) {
      for (int j = 0; j < result.getHeight(); j++) {
        int rgb = result.getRGB(i, j);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        assertTrue(red >= 0 && red <= 255);
        assertTrue(green >= 0 && green <= 255);
        assertTrue(blue >= 0 && blue <= 255);
      }
    }
  }

  @Test
  public void testImageSizePreservation() {
    BufferedImage result = levelAdjuster.adjustLevels(testImage, 10, 130, 230);
    assertEquals(testImage.getWidth(), result.getWidth());
    assertEquals(testImage.getHeight(), result.getHeight());
  }

}