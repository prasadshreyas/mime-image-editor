package mime.model;

import org.junit.Test;

import java.util.Arrays;

import static mime.model.Compression.haar2D;
import static mime.model.Compression.invHaar2D;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CompressionTest {

  @Test
  public void testPadToNextPowerOfTwo() {
    int[][] matrix = {{1, 2, 3}, {4, 5, 6}};
    int s = 4;
    double[][] paddedMatrix = Compression.padToNextPowerOfTwo(matrix, s);
    double[][] expected = {{1, 2, 3, 0}, {4, 5, 6, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    assertArrayEquals(expected, paddedMatrix);
  }

  @Test
  public void testPowerOfTwo() {
    assertEquals(1, Compression.calculateNextPowerOfTwo(1));
    assertEquals(4, Compression.calculateNextPowerOfTwo(3));
    assertEquals(8, Compression.calculateNextPowerOfTwo(6));
    assertEquals(16, Compression.calculateNextPowerOfTwo(10));
    assertEquals(16, Compression.calculateNextPowerOfTwo(15));
    assertEquals(64, Compression.calculateNextPowerOfTwo(40));
  }

  @Test
  public void testUnpadToOriginalSize() {
    double[][] matrix = {{1, 2, 3, 0}, {4, 5, 6, 0}, {7, 8, 9, 0}, {0, 0, 0, 0}};
    int height = 3;
    int width = 3;
    double[][] unPaddedMatrix = Compression.unpadToOriginalSize(matrix, height, width);
    double[][] expected = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    assertArrayEquals(expected, unPaddedMatrix);

  }


  @Test
  public void testT() {

    // Test case 3 - empty array
    double[] input3 = {};
    double[] expected3 = {};
    double[] actual3 = Compression.T(input3, input3.length);
    assertArrayEquals(expected3, actual3, 0.00001);

    // Test case 4 - from the assignment
    double[] input4 = {5, 3, 2, 4,2, 1, 0, 3};
    double[] expected4 = {7.071, 2.828, 1.000, 0.000, 1.414, -1.414, 0.707, -2.121};
    double[] actual4 = Compression.T(input4, input4.length);

    assertArrayEquals(expected4, actual4, 0.001);


  }

  @Test
  public void testInverseHaarWaveletTransform() {
    // Test case 1 - from the assignment
    double[] input1 = {7.071, 2.828, 1.000, 0.000, 1.414, -1.414, 0.707, -2.121};
    double[] expected1 = {5, 3, 2, 4, 2, 1, 0, 3};
    double[] actual1 = Compression.I(input1, input1.length);
    for (int i = 0; i < actual1.length; i++) {
      actual1[i] = Math.round(actual1[i]);
    }
    assertArrayEquals(expected1, actual1, 0.001);



    double[] input3 = {};
    double[] expectedOutput3 = {};
    assertArrayEquals(expectedOutput3, Compression.I(input3, input3.length), 0.0);
  }

  @Test
  public void testSetBelowThreshold() {
    double[][] matrix = new double[][]{
            {-0.5, 1.0, 5.0},
            {2.3, 0.1, -2.2},
            {-3.0, 4.5, -0.001}
    };
    // Apply the method with threshold of 1.0
    Compression.setBelowThreshold(matrix, 1.0);
    double[][] expected = {
            { 0.0, 1.0, 5.0 },
            { 2.3, 0.0, -2.2 },
            { -3.0, 4.5, 0.0 }
    };
    assertArrayEquals(expected, matrix);
  }

  @Test
  public void testCompressAndUncompress(){
    int[][] matrix = new int[][]{
            {5, 4},
            {3, 2}

    };
    int[][] actual = Compression.compressAndUncompress(matrix, 0);
    System.out.println(Arrays.deepToString(actual));
    assertArrayEquals(matrix, actual);

  }

  @Test
  public void testHaar2D(){
    double[][] matrix = new double[][]{
            {5, 4},
            {3, 2}
    };
    haar2D(matrix, 2);
    // 7,1,2,0
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
  public void testInvHaar2D(){
    double[][] matrix = new double[][]{
            {7, 1},
            {2, 0}
    };
    invHaar2D(matrix, 2);
    // 5,4,3,2
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

}