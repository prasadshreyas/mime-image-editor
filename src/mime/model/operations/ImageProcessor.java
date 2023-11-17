package mime.model.operations;

import mime.model.Model;
import mime.model.image.Image;
import mime.model.image.RGBImage;

/**
 * This class represents the available image processing operations.
 */
public class ImageProcessor {

  /**
   * Clamps the given value to the [0,255] range.
   *
   * @param value Value to be clamped.
   * @return Clamped value.
   */
  int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }

  /**
   * Applies the given filter to the given channel.
   *
   * @param channel Channel to be filtered.
   * @param filter  Filter to be applied.
   * @return Filtered channel.
   */
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
   *
   * @param image Image to be blurred.
   * @return Blurred image.
   */
  public Image blur(Image image) {

    double[][] filter = {
            {1.0 / 16, 2.0 / 16, 1.0 / 16},
            {2.0 / 16, 4.0 / 16, 2.0 / 16},
            {1.0 / 16, 2.0 / 16, 1.0 / 16}};
    int[][][] channels = image.getChannels();

    for (int channel = 0; channel < channels.length; channel++) {
      channels[channel] = applyFilter(channels[channel], filter);
    }

    return new RGBImage(channels[0], channels[1], channels[2]);

  }

  /**
   * Sharpens the image.
   *
   * @param image Image to be sharpened.
   * @return Sharpened image.
   */
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
   *
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
   *
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
   *
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
        int newRed = clamp((int) (0.393 * originalRed + 0.769 * originalGreen + 0.189
                * originalBlue));
        int newGreen = clamp((int) (0.349 * originalRed + 0.686 * originalGreen + 0.168
                * originalBlue));
        int newBlue = clamp((int) (0.272 * originalRed + 0.534 * originalGreen + 0.131
                * originalBlue));

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
   * Returns a new image with the brightness increased by the given increment.
   *
   * @param increment the amount to increase the brightness by
   * @param image     the image to brighten
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

  /**
   * Returns a new image with the greyscale component of the given brightness.
   *
   * @param brightness the brightness to use
   * @param image      the image to darken
   * @return the darkened image
   */
  public Image getGrayscaleImage(Model.Brightness brightness, Image image) {
    int[][][] channels = image.getChannels();

    int height = image.getHeight();
    int width = image.getWidth();

    int[][] brightnessChannel = new int[height][width];
    Image resultImage;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int redValue = channels[0][i][j];
        int greenValue = channels[1][i][j];
        int blueValue = channels[2][i][j];
        int componentValue;

        switch (brightness) {
          case LUMA:
            componentValue = (int) (0.2126 * redValue + 0.7152 * greenValue + 0.0722 * blueValue);
            break;
          case INTENSITY:
            componentValue = (redValue + greenValue + blueValue) / 3;
            break;
          default: // Assuming default case to be VALUE
            componentValue = Math.max(redValue, Math.max(greenValue, blueValue));
            break;
        }
        brightnessChannel[i][j] = clamp(componentValue);
      }
    }

    resultImage = new RGBImage(brightnessChannel, brightnessChannel, brightnessChannel);
    return resultImage;
  }

  /**
   * Given an image, returns a new image with the given percentage of the original image's width.
   *
   * @param percentage the percentage of the original image's width to use
   * @param image      the image to split
   * @param image2     the image to split
   * @return the split image
   */
  public Image getSplitView(int percentage, Image image, Image image2) {
    if (percentage == 0) {
      return image;
    }

    if (percentage == 100) {
      return image2;
    }

    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Percentage must be between 0 and 100.");
    }

    if (image.getHeight() != image2.getHeight() || image.getWidth() != image2.getWidth()) {
      throw new IllegalArgumentException("Images must be the same size.");
    }



    int[][][] channels = image.getChannels();
    int[][][] channels2 = image2.getChannels();
    int[][][] newChannels = new int[3][image.getHeight()][image.getWidth()];
    int width = image.getWidth();
    int newWidth = (int) (width * (percentage / 100.0));

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < newWidth; j++) {
        newChannels[0][i][j] = channels[0][i][j];
        newChannels[1][i][j] = channels[1][i][j];
        newChannels[2][i][j] = channels[2][i][j];
      }
      for (int j = newWidth; j < image.getWidth(); j++) {
        newChannels[0][i][j] = channels2[0][i][j];
        newChannels[1][i][j] = channels2[1][i][j];
        newChannels[2][i][j] = channels2[2][i][j];
      }
    }

    // Add a vertical line to separate the two images
    for (int i = 0; i < image.getHeight(); i++) {
      newChannels[0][i][newWidth] = 0;
      newChannels[1][i][newWidth] = 0;
      newChannels[2][i][newWidth] = 0;
    }


    Image splitImage = new RGBImage(newChannels[0], newChannels[1], newChannels[2]);
    return splitImage;
  }
}
