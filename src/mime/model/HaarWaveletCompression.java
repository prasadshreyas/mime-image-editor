package mime.model;

import java.util.Arrays;

public class HaarWaveletCompression {

  // Perform the Haar Wavelet Transform on a 2D array
  public static void haar2D(double[][] data) {
    // Placeholder for Haar Wavelet Transform
  }

  // Perform the inverse Haar Wavelet Transform on a 2D array
  public static void invHaar2D(double[][] data) {
    // Placeholder for inverse Haar Wavelet Transform
  }

  // Apply thresholding for lossy compression
  public static void applyCompression(double[][] data, int compressionPercentage) {
    double max = Arrays.stream(data).flatMapToDouble(Arrays::stream).max().orElse(1);
    double threshold = max * compressionPercentage / 100.0;

    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (Math.abs(data[i][j]) < threshold) {
          data[i][j] = 0;
        }
      }
    }
  }

  // Convert the int[][] channel to double[][]
  public static double[][] toDoubleArray(int[][] channel) {
    double[][] doubleChannel = new double[channel.length][channel[0].length];
    for (int i = 0; i < channel.length; i++) {
      for (int j = 0; j < channel[i].length; j++) {
        doubleChannel[i][j] = channel[i][j];
      }
    }
    return doubleChannel;
  }

  // Convert the double[][] channel back to int[][]
  public static int[][] toIntArray(double[][] channel) {
    int[][] intChannel = new int[channel.length][channel[0].length];
    for (int i = 0; i < channel.length; i++) {
      for (int j = 0; j < channel[i].length; j++) {
        intChannel[i][j] = (int) Math.round(channel[i][j]);
      }
    }
    return intChannel;
  }

  // Compress the provided channel using the specified compression percentage
  public static double[][] compressChannel(int[][] channel, int compressionPercentage) {
    double[][] doubleChannel = toDoubleArray(channel);
    haar2D(doubleChannel);
    applyCompression(doubleChannel, compressionPercentage);
    return doubleChannel;
  }

  // Decompress a previously compressed channel
  public static int[][] decompressChannel(double[][] compressedChannel) {
    invHaar2D(compressedChannel);
    return toIntArray(compressedChannel);
  }
}
