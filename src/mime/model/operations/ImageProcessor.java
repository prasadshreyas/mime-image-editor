package mime.model.operations;

import mime.model.image.Image;
import mime.model.image.RGBImage;

/**
 * This class represents the available image processing operations.
 */
public class ImageProcessor {

  /**
   * Clamps the given value to the [0,255] range.
   * @param value Value to be clamped.
   * @return Clamped value.
   */
  int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }

  int[][] applyFilter(int[][] channel, double[][] filter) {
    int filterHeight = filter.length;
    int filterWidth = filter[0].length;
    int height = channel.length;
    int width = channel[0].length;

    // Find the anchor points of the filter (assumes filter dimensions are odd)
    int anchorX = filterWidth / 2;
    int anchorY = filterHeight / 2;

    // Prepare new channel data for the result of the filter operation
    int[][] newChannelData = new int[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Apply the filter to each pixel (x,y)
        double filteredValue = 0.0;

        for (int filterY = 0; filterY < filterHeight; filterY++) {
          for (int filterX = 0; filterX < filterWidth; filterX++) {
            int imageX = x + filterX - anchorX;
            int imageY = y + filterY - anchorY;

            // Verify image bounds
            if (imageX >= 0 && imageX < width && imageY >= 0 && imageY < height) {
              filteredValue += channel[imageY][imageX] * filter[filterY][filterX];
            }
          }
        }

        // Clamp the result to the [0,255] range and assign to the new channel data
        newChannelData[y][x] = clamp((int) Math.round(filteredValue));
      }
    }

    return newChannelData;
  }


  /**
   * Blurs the image.
   * @param image Image to be blurred.
   * @return Blurred image.
   */
  public Image blur(Image image) {

    double[][] filter = {{1.0 / 16, 2.0 / 16, 1.0 / 16}, {2.0 / 16, 4.0 / 16, 2.0 / 16}, {1.0 / 16, 2.0 / 16, 1.0 / 16}};
    int[][][] channels = image.getChannels();

    for (int channel = 0; channel < channels.length; channel++) {
      channels[channel] = applyFilter(channels[channel], filter);
    }

    return new RGBImage(channels[0], channels[1], channels[2]);

  }

  public Image sharpen(Image image) {
    double[][] filter = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};
    int[][][] channels = image.getChannels();
    for (int channel = 0; channel < channels.length; channel++) {
      channels[channel] = applyFilter(channels[channel], filter);
    }

    return new RGBImage(channels[0], channels[1], channels[2]);
  }



  /**
   * Flips the image vertically.
   * @param image Image to be flipped.
   * @return Flipped image.
   */
  public Image flipVertically(Image image) {
    int[][][] channels = image.getChannels();
    for (int channel = 0; channel < 3; channel++) {
      channels[channel] = flipChannel(channels[channel], true, false);
    }
    Image flippedImage = new RGBImage(channels[0], channels[1], channels[2]);
    return flippedImage;
  }

  /**
   * Flips the image horizontally.
   * @param image Image to be flipped.
   * @return Flipped image.
   */
  public Image flipHorizontally(Image image) {
    int[][][] channels = image.getChannels();
    for (int channel = 0; channel < 3; channel++) {
      channels[channel] = flipChannel(channels[channel], false, true);
    }
    Image flippedImage = new RGBImage(channels[0], channels[1], channels[2]);
    return flippedImage;
  }


  /**
   * Applies a sepia filter to the image.
   * @param image Image to be sepia toned.
   * @return Sepia toned image.
   */
  public Image sepia(Image image) {
    int height = image.getHeight();
    int width = image.getWidth();

    int[][][] channels = image.getChannels();
    int[][] redChannel = channels[0];
    int[][] greenChannel = channels[1];
    int[][] blueChannel = channels[2];

    int[][] sepiaRedChannel = new int[height][width];
    int[][] sepiaGreenChannel = new int[height][width];
    int[][] sepiaBlueChannel = new int[height][width];

    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        // Original RGB values
        int originalRed = redChannel[x][y];
        int originalGreen = greenChannel[x][y];
        int originalBlue = blueChannel[x][y];

        // Calculate new RGB values
        int newRed = clamp((int) (0.393 * originalRed + 0.769 * originalGreen + 0.189 * originalBlue));
        int newGreen = clamp((int) (0.349 * originalRed + 0.686 * originalGreen + 0.168 * originalBlue));
        int newBlue = clamp((int) (0.272 * originalRed + 0.534 * originalGreen + 0.131 * originalBlue));

        // Assign new values to the sepia channels
        sepiaRedChannel[x][y] = newRed;
        sepiaGreenChannel[x][y] = newGreen;
        sepiaBlueChannel[x][y] = newBlue;
      }
    }

    Image sepiaImage = new RGBImage(sepiaRedChannel, sepiaGreenChannel, sepiaBlueChannel);
    return sepiaImage;
  }



  private int[][] flipChannel(int[][] channel, boolean vertical, boolean horizontal) {
    int[][] flippedChannel = new int[channel.length][channel[0].length];

    for (int row = 0; row < channel.length; row++) {
      for (int col = 0; col < channel[0].length; col++) {
        int newRow = vertical ? channel.length - 1 - row : row;
        int newCol = horizontal ? channel[0].length - 1 - col : col;
        flippedChannel[newRow][newCol] = channel[row][col];
      }
    }
    return flippedChannel;
  }

  /**
   * Returns a new image with the brightness increased by the given increment
   * @param increment the amount to increase the brightness by
   * @param image the image to brighten
   * @return the brightened image
   */
  public Image getBrightenedImage(int increment, Image image) {
    int[][][] channels = image.getChannels();
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        for (int channel = 0; channel < 3; channel++) {
          channels[channel][row][col] = channels[channel][row][col] + increment;
        }
      }
    }

    Image brightenedImage = new RGBImage(channels[0], channels[1], channels[2]);
    return brightenedImage;
  }
}
