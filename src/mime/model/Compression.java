package mime.model;

import java.util.Arrays;

/**
 * This class represents the model of the image in the RGB color space.
 */
public class Compression {


  public static int[][] compressAndUncompress(int[][] matrix, int compressionRatio) {

    int height = matrix.length;
    int width = matrix[0].length;

    int s = calculateNextPowerOfTwo(Math.max(height, width));

    double[][] paddedMatrix = padToNextPowerOfTwo(matrix, s);

    // Apply the Haar wavelet transform
    haar2D(paddedMatrix, s);

    // Calculate the compression threshold
    double threshold = calculateThreshold(paddedMatrix, compressionRatio / 100.0, height, width);

    // Apply the threshold to compress the elements in the matrix X
    setBelowThreshold(paddedMatrix, threshold);

    // Apply the inverse Haar wavelet transform
    invHaar2D(paddedMatrix, s);

    double[][] unPaddedMatrix = unpadToOriginalSize(paddedMatrix, height, width);

    int[][] unPaddedMatrixInt = new int[height][width];

    for (int i = 0; i < unPaddedMatrix.length; i++) {
      for (int j = 0; j < unPaddedMatrix[i].length; j++) {
        unPaddedMatrixInt[i][j] = (int) Math.round(unPaddedMatrix[i][j]);
      }
    }

    return unPaddedMatrixInt;

  }


  /**
   * Applies the threshold to the given matrix.
   *
   * @param X                The matrix to be compressed.
   * @param threshold        The threshold to be applied.
   */
  public static void setBelowThreshold(double[][] X, double threshold) {
    // Apply threshold to compress the elements in the matrix X
    for (int i = 0; i < X.length; i++) {
      for (int j = 0; j < X[i].length; j++) {
        if (Math.abs(X[i][j]) < threshold) {
          X[i][j] = 0.0; // Compress elements below the threshold by setting them to zero
        }
      }
    }
  }


  /**
   * Calculates the compression threshold for the given matrix.
   *
   * @param X                The padded matrix.
   * @param compressionRatio The compression ratio.
   * @param height           The height of the original matrix.
   * @param width            The width of the original matrix.
   * @return The compression threshold.
   */
  public static double calculateThreshold(double[][] X, double compressionRatio, int height, int width) {
    int totalElements = height * width;
    double[] temp = new double[totalElements];
    int count = 0;

    // Store absolute values of all elements in X into temp, traverse only till height and width
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        temp[count++] = Math.abs(X[i][j]);
      }
    }

    // Sort temp to find the threshold
    Arrays.sort(temp);

    // Calculate the index for the compression threshold
    int valuesToKeep = (int) (totalElements * (1 - compressionRatio));
    // Ensure that valuesToKeep is within the array bounds
    valuesToKeep = Math.max(0, Math.min(valuesToKeep, totalElements - 1));

    // Find the threshold value
    double threshold = temp[totalElements - valuesToKeep - 1]; // Adjust index to account for zero-based array indexing

    return threshold;
  }


  /**
   * Applies the Haar wavelet transform to the given matrix.
   *
   * @param X The matrix to be transformed.
   * @param s The size of the square matrix.
   */
  public static void haar2D(double[][] X, int s) {
    int c = s;
    while (c > 1) {
      for (int i = 0; i < c; i++) {
        // Apply transform T to each row
        double[] row = new double[c];
        System.arraycopy(X[i], 0, row, 0, c);
        row = T(row, c);
        System.arraycopy(row, 0, X[i], 0, c);
      }
      for (int j = 0; j < c; j++) {
        // Apply transform T to each column
        double[] col = new double[c];
        for (int i = 0; i < c; i++) {
          col[i] = X[i][j];
        }
        col = T(col, c);
        for (int i = 0; i < c; i++) {
          X[i][j] = col[i];
        }
      }
      c = c / 2;
    }
  }

  public static void invHaar2D(double[][] X, int s) {
    int c = 2;
    while (c <= s) {
      for (int j = 0; j < c; j++) {
        // Apply inverse transform I to each column
        double[] col = new double[c];
        for (int i = 0; i < c; i++) {
          col[i] = X[i][j];
        }
        col = I(col, c);
        for (int i = 0; i < c; i++) {
          X[i][j] = col[i];
        }
      }
      for (int i = 0; i < c; i++) {
        // Apply inverse transform I to each row
        double[] row = new double[c];
        System.arraycopy(X[i], 0, row, 0, c);
        row = I(row, c);
        System.arraycopy(row, 0, X[i], 0, c);
      }
      c = c * 2;
    }
  }

  /**
   * Applies the inverse Haar wavelet transform to the given array.
   *
   * @param s The array to be transformed.
   * @param l The size of the array.
   * @return The transformed array.
   */
  public static double[] I(double[] s, int l) {
    double[] result = new double[l];
    int m = 2; // Start with the smallest grouping of 2

    // We will be working with a temp array for each step of the inverse transform
    while (m <= l) {
      double[] temp = new double[l]; // Temporary array to store intermediate values

      // Copy s to temp for manipulation
      for (int i = 0; i < l; i++) {
        temp[i] = s[i];
      }

      // Perform the inverse operation on pairs within the current grouping
      for (int i = 0; i < m / 2; i++) {
        // Calculate the original values from the averages and differences
        result[2 * i] = (temp[i] + temp[m / 2 + i]) / Math.sqrt(2);
        result[2 * i + 1] = (temp[i] - temp[m / 2 + i]) / Math.sqrt(2);
      }

      // Prepare for the next iteration by copying the results back to s
      for (int i = 0; i < m; i++) {
        s[i] = result[i];
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
  public static double[] T(double[] s, int m) {
    while (m > 1) {
      double[] temp = new double[m];
      for (int i = 0; i < m; i++) {
        temp[i] = s[i]; // Copy current segment to a temp array
      }
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
   * @param matrix The matrix to be padded.
   * @param s      The next power of two.
   * @return The padded matrix.
   */
  public static double[][] padToNextPowerOfTwo(int[][] matrix, int s) {
    double[][] newMatrix = new double[s][s];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        newMatrix[i][j] = matrix[i][j];
      }
    }
    return newMatrix;
  }

  /**
   * Unpads the matrix to the original size.
   *
   * @param matrix The matrix to be unpadded.
   * @param height The original height of the matrix.
   * @param width  The original width of the matrix.
   * @return The unpadded matrix.
   */
  public static double[][] unpadToOriginalSize(double[][] matrix, int height, int width) {
    double[][] newMatrix = new double[height][width];
    for (int i = 0; i < newMatrix.length; i++) {
      for (int j = 0; j < newMatrix[i].length; j++) {
        newMatrix[i][j] = matrix[i][j];
      }
    }
    return newMatrix;
  }


  /**
   * Calculates the next power of two of the given number.
   *
   * @param n The number to calculate the next power of two.
   * @return The next power of two of the given number.
   */
  public static int calculateNextPowerOfTwo(int n) {
    int power = 1;
    while (power < n) {
      power *= 2;
    }
    return power;
  }


}
