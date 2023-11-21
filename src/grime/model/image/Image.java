package grime.model.image;

import java.awt.image.BufferedImage;
import grime.model.Model.Channel ;

/**
 * This interface represents the image. It contains methods to get the width, height, BufferedImage,
 * and channels of the image.
 */
public interface Image {

  /**
   * Returns the width of the image.
   *
   * @return int width of the image.
   */
  int getWidth();

  /**
   * Returns the height of the image.
   *
   * @return int height of the image.
   */
  int getHeight();

  /**
   * Returns the BufferedImage of the image.
   *
   * @return BufferedImage of the image.
   */
  BufferedImage getBufferedImage();

  /**
   * Returns the color component of the image.
   *
   * @param channel color component of the image.
   * @return int[][] channel of the image.
   */
  int[][] getChannel(Channel channel);

  /**
   * Returns all the channels of the image.
   *
   * @return int[][][] channels of the image.
   */
  int[][][] getChannels();

}

