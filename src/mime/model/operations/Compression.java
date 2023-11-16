package mime.model.operations;

import java.util.Arrays;

import mime.model.image.Image;
import mime.model.image.RGBImage;

/**
 * This class represents the Compression operation for an RGB image.
 */
public class Compression {
  Image originalImage;
  int originalHeight;
  int originalWidth;
  double compressionRatio;


  /**
   * Constructs a new Compression object initializing the original image, its height and width and
   * the compression ratio.
   */
  public Compression(Image image, int percentage) {
    this.originalImage = image;
    this.originalHeight = image.getHeight();
    this.originalWidth = image.getWidth();
    this.compressionRatio = percentage / 100.0;
  }

  /**
   * Compresses and un-compresses the image.
   *
   * @return The uncompressed image.
   */
  public Image compressAndUncompress() {

    int[][][] uncompressedChannels = new int[3][][];

    int s = Math.max(calculateNextPowerOfTwo(originalHeight),
            calculateNextPowerOfTwo(originalWidth));

    double[][][] paddedMatrix = padToNextPowerOfTwo(originalImage.getChannels(), s);

    // Apply the Haar wavelet transform
    double[][][] transformedMatrix = new double[3][s][s];
    for (int i = 0; i < 3; i++) {
      transformedMatrix[i] = haar2D(paddedMatrix[i], s);
    }

    // Calculate the compression threshold
    double threshold = calculateThreshold(transformedMatrix, originalHeight, originalWidth);


    // for all channels now,
    for (int i = 0; i < 3; i++) {

      // Apply the threshold to compress the elements in the matrix mat
      transformedMatrix[i] = setBelowThreshold(transformedMatrix[i], threshold);


      // Apply the inverse Haar wavelet transform
      transformedMatrix[i] = invHaar2D(transformedMatrix[i], s);


      double[][] unPaddedMatrix = unpadToOriginalSize(transformedMatrix[i], originalHeight,
              originalWidth);

      int[][] unPaddedMatrixInt = new int[unPaddedMatrix.length][unPaddedMatrix[0].length];

      for (int j = 0; j < unPaddedMatrix.length; j++) {
        for (int k = 0; k < unPaddedMatrix[j].length; k++) {
          unPaddedMatrixInt[j][k] = (int) Math.round(unPaddedMatrix[j][k]);
        }
      }
      uncompressedChannels[i] = unPaddedMatrixInt;
    }

    return new RGBImage(uncompressedChannels[0], uncompressedChannels[1], uncompressedChannels[2]);

  }


  /**
   * Calculates the next power of two of the given number.
   *
   * @param n The number to calculate the next power of two.
   * @return The next power of two of the given number.
   */
  private int calculateNextPowerOfTwo(int n) {
    int power = 1;
    while (power < n) {
      power *= 2;
    }
    return power;
  }


  /**
   * Applies the threshold to the given matrix.
   *
   * @param mat       The matrix to be compressed.
   * @param threshold The threshold to be applied.
   */
  private double[][] setBelowThreshold(double[][] mat, double threshold) {
    // Apply threshold to compress the elements in the matrix mat
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[i].length; j++) {
        if (Math.abs(mat[i][j]) < threshold) {
          mat[i][j] = 0.0; // Compress elements below the threshold by setting them to zero
        }
      }
    }
    return mat;
  }

  /**
   * Calculates the compression threshold for the given matrix.
   *
   * @param mat    The padded matrix.
   * @param height The height of the original matrix.
   * @param width  The width of the original matrix.
   * @return The compression threshold.
   */
  private double calculateThreshold(double[][][] mat, int height, int width) {
    double[] flattenedMatrix = flattenMatrix(mat, height, width);
    Arrays.sort(flattenedMatrix);
    int valuesToKeep = calculateValuesToKeep(flattenedMatrix.length, compressionRatio);
    return findThreshold(flattenedMatrix, valuesToKeep);
  }

  private int calculateValuesToKeep(int totalElements, double compressionRatio) {
    int valuesToKeep = (int) (totalElements * (1 - compressionRatio));
    // Ensure that valuesToKeep is within the array bounds
    return Math.max(0, Math.min(valuesToKeep, totalElements - 1));
  }

  private double findThreshold(double[] sortedArray, int valuesToKeep) {
    // Adjust index to account for zero-based array indexing
    return sortedArray[sortedArray.length - valuesToKeep - 1];
  }

  private double[] flattenMatrix(double[][][] mat, int height, int width) {
    double[] temp = new double[height * width * 3]; // 3 for the three color channels
    int count = 0;
    for (int c = 0; c < 3; c++) {
      for (int i = 0; i < height; i++) {
        System.arraycopy(mat[c][i], 0, temp, count, width);
        count += width;
      }
    }

    return temp;
  }

  /**
   * Applies the Haar wavelet transform to the given matrix.
   *
   * @param mat The matrix to be transformed.
   * @param c   The size of the square matrix.
   */
  private double[][] haar2D(double[][] mat, int c) {
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        // Apply transform to each row
        double[] row = new double[c];
        System.arraycopy(mat[i], 0, row, 0, c);
        row = transform(row, c);
        System.arraycopy(row, 0, mat[i], 0, c);
      }
      for (int j = 0; j < c; j++) {
        // Apply transform to each column
        double[] col = new double[c];
        for (int i = 0; i < c; i++) {
          col[i] = mat[i][j];
        }
        col = transform(col, c);
        for (int i = 0; i < c; i++) {
          mat[i][j] = col[i];
        }
      }
      c = c / 2;
    }
    return mat;
  }

  private double[][] invHaar2D(double[][] mat, int s) {
    int c = 2;
    while (c <= s) {
      for (int j = 0; j < c; j++) {
        // Apply inverse transform inverse to each column
        double[] col = new double[c];
        for (int i = 0; i < c; i++) {
          col[i] = mat[i][j];
        }
        col = inverse(col, c);
        for (int i = 0; i < c; i++) {
          mat[i][j] = col[i];
        }
      }
      for (int i = 0; i < c; i++) {
        // Apply inverse transform inverse to each row
        double[] row = new double[c];
        System.arraycopy(mat[i], 0, row, 0, c);
        row = inverse(row, c);
        System.arraycopy(row, 0, mat[i], 0, c);
      }
      c = c * 2;
    }
    return mat;
  }

  /**
   * Applies the inverse Haar wavelet transform to the given array.
   *
   * @param s The array to be transformed.
   * @param l The size of the array.
   * @return The transformed array.
   */
  private double[] inverse(double[] s, int l) {
    double[] result = new double[l];
    int m = 2; // Start with the smallest grouping of 2

    // We will be working with a temp array for each step of the inverse transform
    while (m <= l) {
      double[] temp = new double[l]; // Temporary array to store intermediate values

      // Copy s to temp for manipulation
      System.arraycopy(s, 0, temp, 0, l);

      // Perform the inverse operation on pairs within the current grouping
      for (int i = 0; i < m / 2; i++) {
        // Calculate the original values from the averages and differences
        result[2 * i] = (temp[i] + temp[m / 2 + i]) / Math.sqrt(2);
        result[2 * i + 1] = (temp[i] - temp[m / 2 + i]) / Math.sqrt(2);
      }

      // Prepare for the next iteration by copying the results back to s
      if (m >= 0) {
        System.arraycopy(result, 0, s, 0, m);
      }

      m *= 2; // Double the grouping size for the next iteration
    }
    return result;
  }

  /**
   * Applies the Haar wavelet transform to the given array.
   *
   * @param s The array to be transformed.
   * @param m The size of the array.
   * @return The transformed array.
   */
  private double[] transform(double[] s, int m) {
    while (m > 1) {
      double[] temp = new double[m];
      // Copy current segment to a temp array
      System.arraycopy(s, 0, temp, 0, m);
      for (int i = 0; i < m / 2; i++) {
        s[i] = (temp[2 * i] + temp[2 * i + 1]) / Math.sqrt(2); // Normalized average
        s[i + m / 2] = (temp[2 * i] - temp[2 * i + 1]) / Math.sqrt(2); // Normalized difference
      }
      m = m / 2; // Reduce the size by half for the next iteration
    }
    return s;
  }

  /**
   * Pads the matrix to the next power of two.
   *
   * @param channels The channels to be padded.
   * @param s        The next power of two.
   * @return The padded matrix.
   */
  private double[][][] padToNextPowerOfTwo(int[][][] channels, int s) {

    double[][][] paddedChannels = new double[channels.length][s][s];

    for (int i = 0; i < channels.length; i++) {
      for (int j = 0; j < channels[i].length; j++) {
        for (int k = 0; k < channels[i][j].length; k++) {
          paddedChannels[i][j][k] = channels[i][j][k];
        }
      }
    }

    return paddedChannels;

  }

  /**
   * Unpads the matrix to the original size.
   *
   * @param matrix The matrix to be unpadded.
   * @param height The original height of the matrix.
   * @param width  The original width of the matrix.
   * @return The unpadded matrix.
   */
  private double[][] unpadToOriginalSize(double[][] matrix, int height, int width) {
    double[][] newMatrix = new double[height][width];
    for (int i = 0; i < newMatrix.length; i++) {
      System.arraycopy(matrix[i], 0, newMatrix[i], 0, newMatrix[i].length);
    }
    return newMatrix;
  }


}
