package mime.model.operations;

import org.junit.Before;
import org.junit.Test;

import mime.model.image.Image;
import mime.model.image.RGBImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This class tests the ImageProcessor class.
 */
public class ImageProcessorTest {
  private Image image1;
  private Image image2;
  private ImageProcessor processor;
  @Before
  public void setUp() throws Exception {
    int[][] r_channel = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    };

    image1 = new RGBImage(r_channel, r_channel, r_channel);

    image2 = new RGBImage(r_channel, null, null);

    processor = new ImageProcessor();
  }

  @Test
  public void testGetSplitViewWith0Percent() {
    Image result = processor.getSplitView(0, image1, image2);
    assertNotNull(result);
    assertEquals(result, image1);
  }

  @Test
  public void testGetSplitViewWith100Percent() {
    Image result = processor.getSplitView(100, image1, image2);
    assertNotNull(result);
    assertEquals(result, image2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSplitViewWithNegativePercentage() {
    processor.getSplitView(-10, image1, image2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSplitViewWithOver100Percentage() {
    processor.getSplitView(110, image1, image2);
  }

  @Test(expected = NullPointerException.class)
  public void testGetSplitViewWithNullImage1() {
    processor.getSplitView(50, null, image2);
  }

  @Test(expected = NullPointerException.class)
  public void testGetSplitViewWithNullImage2() {
    processor.getSplitView(50, image1, null);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetSplitViewWithVaryingSizes() {
    int[][] r_channel = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    };

    Image image1 = new RGBImage(r_channel, r_channel, r_channel);

    int[][] r_channel2 = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    };

    Image image2 = new RGBImage(r_channel2, r_channel2, r_channel2);

    Image result = processor.getSplitView(50, image1, image2);
    assertNotNull(result);
    assertEquals(result, image2);
  }

}