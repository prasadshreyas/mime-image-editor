package mime.model.image;

import org.junit.Test;

import java.awt.image.BufferedImage;

import mime.model.Model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

/**
 * This class tests the RGBImage class.
 */
public class RGBImageTest {

  @Test
  public void testConstructorWithBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    // Populate with some test data
    for (int i = 0; i < bufferedImage.getHeight(); i++) {
      for (int j = 0; j < bufferedImage.getWidth(); j++) {
        bufferedImage.setRGB(j, i, (i << 16) | (j << 8) | i); // some arbitrary pixel value
      }
    }

    RGBImage rgbImage = new RGBImage(bufferedImage);
    assertNotNull(rgbImage);
    assertEquals(10, rgbImage.getWidth());
    assertEquals(10, rgbImage.getHeight());

  }

  @Test
  public void testConstructorWithChannels() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{255, 0}, {0, 255}};
    int[][] blueChannel = {{255, 0}, {0, 255}};

    RGBImage rgbImage = new RGBImage(redChannel, greenChannel, blueChannel);
    assertNotNull(rgbImage);
    assertEquals(2, rgbImage.getWidth());
    assertEquals(2, rgbImage.getHeight());

  }

  @Test
  public void testInvalidChannelLengthConstructor() {
    int[][] redChannel = {{255}, {0}}; // 2x1
    int[][] greenChannel = {{255, 0}}; // 1x2
    int[][] blueChannel = {{255}, {0}, {0}}; // 3x1

    assertThrows(IllegalArgumentException.class, () -> {
      new RGBImage(redChannel, greenChannel, blueChannel);
    });
  }

  @Test
  public void testGetBufferedImage() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{255, 0}, {0, 255}};
    int[][] blueChannel = {{255, 0}, {0, 255}};

    RGBImage rgbImage = new RGBImage(redChannel, greenChannel, blueChannel);
    BufferedImage image = rgbImage.getBufferedImage();

    for (int i = 0; i < rgbImage.getHeight(); i++) {
      for (int j = 0; j < rgbImage.getWidth(); j++) {
        int rgb = image.getRGB(j, i);
        assertEquals(redChannel[i][j], (rgb >> 16) & 0xFF);
        assertEquals(greenChannel[i][j], (rgb >> 8) & 0xFF);
        assertEquals(blueChannel[i][j], rgb & 0xFF);
      }
    }
  }

  @Test
  public void testGetChannel() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{255, 0}, {0, 255}};
    int[][] blueChannel = {{255, 0}, {0, 255}};

    RGBImage rgbImage = new RGBImage(redChannel, greenChannel, blueChannel);

    assertArrayEquals(redChannel, rgbImage.getChannel(Model.Channel.RED));
    assertArrayEquals(greenChannel, rgbImage.getChannel(Model.Channel.GREEN));
    assertArrayEquals(blueChannel, rgbImage.getChannel(Model.Channel.BLUE));
  }

  @Test
  public void testGetChannels() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{255, 0}, {0, 255}};
    int[][] blueChannel = {{255, 0}, {0, 255}};

    RGBImage rgbImage = new RGBImage(redChannel, greenChannel, blueChannel);
    int[][][] channels = rgbImage.getChannels();

    assertArrayEquals(redChannel, channels[0]);
    assertArrayEquals(greenChannel, channels[1]);
    assertArrayEquals(blueChannel, channels[2]);
  }

  @Test
  public void testHashCode() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{255, 0}, {0, 255}};
    int[][] blueChannel = {{255, 0}, {0, 255}};

    RGBImage rgbImage1 = new RGBImage(redChannel, greenChannel, blueChannel);
    RGBImage rgbImage2 = new RGBImage(redChannel, greenChannel, blueChannel);

    assertEquals(rgbImage1.hashCode(), rgbImage2.hashCode());
  }

  @Test
  public void testEquals() {
    int[][] redChannel = {{255, 0}, {0, 255}};
    int[][] greenChannel = {{255, 0}, {0, 255}};
    int[][] blueChannel = {{255, 0}, {0, 255}};

    RGBImage rgbImage1 = new RGBImage(redChannel, greenChannel, blueChannel);
    RGBImage rgbImage2 = new RGBImage(redChannel, greenChannel, blueChannel);
    RGBImage rgbImage3 = new RGBImage(redChannel, greenChannel, new int[][]{{0, 255}, {255, 0}});

    assertEquals(rgbImage1, rgbImage2);
    assertNotEquals(rgbImage1, rgbImage3);
  }
}