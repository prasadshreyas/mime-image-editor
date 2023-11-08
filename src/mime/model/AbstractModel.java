package mime.model;


abstract class AbstractModel implements Model {

  int[][] flipChannel(int[][] channel, boolean vertical, boolean horizontal) {
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

  int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }
}
