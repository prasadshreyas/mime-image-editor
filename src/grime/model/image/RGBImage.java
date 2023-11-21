package grime.model.image;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.Function;

import grime.model.Model;

/**
 * This class represents a RGBImage object.
 */
public class RGBImage implements Image {
  private final int[][] redChannel;
  private final int[][] greenChannel;
  private final int[][] blueChannel;
  private final int width;
  private final int height;

  /**
   * Constructs a RGBImage object.
   *
   * @param bufferedImage BufferedImage object.
   */
  public RGBImage(BufferedImage bufferedImage) {
    this.width = bufferedImage.getWidth();
    this.height = bufferedImage.getHeight();
    this.redChannel = getChannelFromBufferedImage(16, bufferedImage);
    this.greenChannel = getChannelFromBufferedImage(8, bufferedImage);
    this.blueChannel = getChannelFromBufferedImage(0, bufferedImage);
  }


  /**
   * Constructs a RGBImage object.
   */
  public RGBImage(int[][] redChannel, int[][] greenChannel, int[][] blueChannel) {

    // Check if all parameters are null
    if (redChannel == null && greenChannel == null && blueChannel == null) {
      throw new IllegalArgumentException("At least one channel must be non-null.");
    }

    // get width and height from  the non-null channel
    if (redChannel != null) {
      this.height = redChannel.length;
      this.width = redChannel[0].length;
    } else if (greenChannel != null) {
      this.height = greenChannel.length;
      this.width = greenChannel[0].length;
    } else {
      this.height = blueChannel.length;
      this.width = blueChannel[0].length;
    }

    // if any channel is null, set it to a 2D array of 0s
    if (redChannel == null) {
      redChannel = new int[this.height][this.width];
    }
    if (greenChannel == null) {
      greenChannel = new int[this.height][this.width];
    }
    if (blueChannel == null) {
      blueChannel = new int[this.height][this.width];
    }

    if (redChannel.length != greenChannel.length || redChannel.length != blueChannel.length) {
      throw new IllegalArgumentException("Channel arrays must have the same length.");
    }

    if (redChannel[0].length != greenChannel[0].length
            || redChannel[0].length != blueChannel[0].length) {
      throw new IllegalArgumentException("Channel arrays must have the same length.");
    }

    this.redChannel = applyFunction(redChannel, this::clamp);
    this.greenChannel = applyFunction(greenChannel, this::clamp);
    this.blueChannel = applyFunction(blueChannel, this::clamp);

  }

  private int[][] applyFunction(int[][] channel, Function<Integer, Integer> function) {
    int[][] newChannel = new int[channel.length][channel[0].length];
    for (int row = 0; row < channel.length; row++) {
      for (int col = 0; col < channel[0].length; col++) {
        newChannel[row][col] = function.apply(channel[row][col]);
      }
    }
    return newChannel;
  }


  private int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }

  private int[][] getChannelFromBufferedImage(int channel, BufferedImage bufferedImage) {
    int[][] channelArray = new int[height][width];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {

        int pixel = bufferedImage.getRGB(col, row);
        int color = (pixel >> channel) & 0xff;
        channelArray[row][col] = clamp(color);
      }
    }

    return channelArray;
  }


  @Override
  public int getWidth() {
    return this.width;

  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public BufferedImage getBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {

        int rgb = (redChannel[row][col] << 16) | (greenChannel[row][col] << 8)
                | blueChannel[row][col];
        bufferedImage.setRGB(col, row, rgb);
      }
    }

    return bufferedImage;
  }

  @Override
  public int[][] getChannel(Model.Channel channel) {
    if (channel == null) {
      throw new IllegalArgumentException("Channel cannot be null.");
    }
    switch (channel) {
      case RED:
        return deepCopy(this.redChannel);
      case GREEN:
        return deepCopy(this.greenChannel);
      case BLUE:
        return deepCopy(this.blueChannel);
      default:
        throw new IllegalArgumentException(" Invalid channel.");
    }
  }

  @Override
  public int[][][] getChannels() {
    return new int[][][]{
            deepCopy(this.redChannel),
            deepCopy(this.greenChannel),
            deepCopy(this.blueChannel)
    };
  }

  private int[][] deepCopy(int[][] original) {
    int[][] copy = new int[original.length][];
    for (int i = 0; i < original.length; i++) {
      copy[i] = original[i].clone();
    }
    return copy;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof RGBImage) {
      RGBImage other = (RGBImage) obj;
      return this.width == other.width && this.height == other.height
              && Arrays.deepEquals(this.redChannel, other.redChannel)
              && Arrays.deepEquals(this.greenChannel, other.greenChannel)
              && Arrays.deepEquals(this.blueChannel, other.blueChannel);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.width + this.height + this.redChannel.hashCode()
            + this.greenChannel.hashCode() + this.blueChannel.hashCode();
  }

  @Override
  public String toString() {
    // return with new line
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < height; row++) {
      sb.append("[");
      for (int col = 0; col < width; col++) {
        sb.append("(").append(redChannel[row][col]).append(", ")
                .append(greenChannel[row][col]).append(", ")
                .append(blueChannel[row][col]).append(")");
        if (col != width - 1) {
          sb.append(", ");
        }
      }
      sb.append("]\n");
    }
    return sb.toString();
  }
}
