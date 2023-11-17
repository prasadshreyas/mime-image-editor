package mime.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import mime.model.image.Image;
import mime.model.image.RGBImage;
import mime.model.operations.Compression;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the Compression class.
 */
public class CompressionTest {


  private Image image1;
  private Compression compression;

  @Before
  public void setUp() {
    int[][] r_channel = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
    };
    image1 = new RGBImage(r_channel, r_channel, r_channel);
    compression = new Compression(image1, 50);
  }

  @Test
  public void testCompressAndUncompress() {
    Image uncompressedImage = compression.compressAndUncompress();
    assertNotNull("Uncompressed image should not be null", uncompressedImage);
    assertEquals("Uncompressed image should have same height as original",
            image1.getHeight(), uncompressedImage.getHeight());
    assertEquals("Uncompressed image should have same width as original",
            image1.getWidth(), uncompressedImage.getWidth());
  }

  @Test
  public void testPadToNextPowerOfTwo() {

    int[][][] matrix = {{{1, 2, 3}, {4, 5, 6}}};
    int s = 4;
    double[][][] paddedMatrix = compression.padToNextPowerOfTwo(matrix, s);
    double[][][] expected = {{{1, 2, 3, 0}, {4, 5, 6, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}};
    assertArrayEquals(expected, paddedMatrix);
  }

  @Test
  public void testPowerOfTwo() {
    assertEquals(1, compression.calculateNextPowerOfTwo(1));
    assertEquals(4, compression.calculateNextPowerOfTwo(3));
    assertEquals(8, compression.calculateNextPowerOfTwo(6));
    assertEquals(16, compression.calculateNextPowerOfTwo(10));
    assertEquals(16, compression.calculateNextPowerOfTwo(15));
    assertEquals(64, compression.calculateNextPowerOfTwo(40));
  }

  @Test
  public void testUnpadToOriginalSize() {
    double[][] matrix = {{1, 2, 3, 0}, {4, 5, 6, 0}, {7, 8, 9, 0}, {0, 0, 0, 0}};
    int height = 3;
    int width = 3;
    double[][] unPaddedMatrix = compression.unpadToOriginalSize(matrix, height, width);
    double[][] expected = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    assertArrayEquals(expected, unPaddedMatrix);

  }


  @Test
  public void testT() {

    double[] input3 = {};
    double[] expected3 = {};
    double[] actual3 = compression.transform(input3, input3.length);
    assertArrayEquals(expected3, actual3, 0.00001);

    double[] input4 = {5, 3, 2, 4, 2, 1, 0, 3};
    double[] expected4 = {7.071, 2.828, 1.000, 0.000, 1.414, -1.414, 0.707, -2.121};
    double[] actual4 = compression.transform(input4, input4.length);

    assertArrayEquals(expected4, actual4, 0.001);


  }

  @Test
  public void testSetBelowThreshold() {
    double[][] matrix = new double[][]{
            {-0.5, 1.0, 5.0},
            {2.3, 0.1, -2.2},
            {-3.0, 4.5, -0.001}
    };
    compression.setBelowThreshold(matrix, 1.0);
    double[][] expected = {
            {0.0, 1.0, 5.0},
            {2.3, 0.0, -2.2},
            {-3.0, 4.5, 0.0}
    };
    assertArrayEquals(expected, matrix);

    compression.setBelowThreshold(matrix, 10.0);
    expected = new double[][]{
            {0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0}
    };
    assertArrayEquals(expected, matrix);


  }


  @Test
  public void testHaar2D() {
    double[][] matrix = new double[][]{
            {5, 4},
            {3, 2}
    };
    compression.haar2D(matrix, 2);
    double[][] expected = new double[][]{
            {7, 1},
            {2, 0}
    };
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        assertEquals(expected[i][j], matrix[i][j], 0.1);
      }
    }


  }

  @Test
  public void testInvHaar2D() {
    double[][] matrix = new double[][]{
            {7, 1},
            {2, 0}
    };
    compression.invHaar2D(matrix, 2);
    double[][] expected = new double[][]{
            {5, 4},
            {3, 2}
    };
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        assertEquals(expected[i][j], matrix[i][j], 0.1);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressionWithNullImage() {
    new Compression(null, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressionWithInvalidPercentage() {
    new Compression(image1, 150);
  }

  @Test
  public void testDifferentCompressionRatios() {
    Compression compressionLow = new Compression(image1, 10);
    Compression compressionHigh = new Compression(image1, 90);

    Image lowCompressedImage = compressionLow.compressAndUncompress();
    Image highCompressedImage = compressionHigh.compressAndUncompress();

    double differenceScore = compareImages(lowCompressedImage, highCompressedImage);

    assertTrue("Images with different compression ratios should differ",
            differenceScore < 100);
  }

  @Test
  public void testCalculateNextPowerOfTwoEdgeCases() {
    assertEquals(1, compression.calculateNextPowerOfTwo(0));
    assertEquals(1, compression.calculateNextPowerOfTwo(-5));
  }

  @Test
  public void testSetBelowThresholdRandomized() {
    Random random = new Random();
    double[][] matrix = new double[10][10];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        matrix[i][j] = random.nextDouble() * 10;
      }
    }
    double threshold = 5.0;
    compression.setBelowThreshold(matrix, threshold);
    for (double[] row : matrix) {
      for (double value : row) {
        assertTrue("Values should be either 0 or above the threshold",
                value == 0.0 || value >= threshold);
      }
    }
  }


  private double compareImages(Image lowCompressedImage, Image highCompressedImage) {
    int[][][] lowCompressedChannels = lowCompressedImage.getChannels();
    int[][][] highCompressedChannels = highCompressedImage.getChannels();

    double differenceScore = 0.0;
    for (int channel = 0; channel < 3; channel++) {
      differenceScore += compareChannels(lowCompressedChannels[channel],
              highCompressedChannels[channel]);
    }
    return differenceScore;

  }

  private double compareChannels(int[][] channel1, int[][] channel2) {
    double differenceScore = 0.0;
    for (int i = 0; i < channel1.length; i++) {
      for (int j = 0; j < channel1[i].length; j++) {
        differenceScore += Math.abs(channel1[i][j] - channel2[i][j]);
      }
    }
    return differenceScore;
  }

}