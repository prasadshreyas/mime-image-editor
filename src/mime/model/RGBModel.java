package mime.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import mime.model.image.Image;
import mime.model.image.RGBImage;

/**
 * This class represents the model of the image in the RGB color space.
 */
public class RGBModel extends AbstractModel {
  private HashMap<String, Image> images;

  /**
   * Constructs HashMap of images.
   */
  public RGBModel() {
    images = new HashMap<>();
  }

  @Override
  public boolean containsImage(String imageName) {
    if (images.containsKey(imageName)) {
      return true;
    }
    return false;
  }

  @Override
  public void compressImage(int percentage, String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    int[][][] channels = image.getChannels();
    double[][] compressedRedChannel = HaarWaveletCompression.compressChannel(channels[0],
            percentage);
    double[][] compressedGreenChannel = HaarWaveletCompression.compressChannel(channels[1], percentage);
    double[][] compressedBlueChannel = HaarWaveletCompression.compressChannel(channels[2], percentage);

    // Decompress the image to check if the compression works
    int[][] decompressedRedChannel = HaarWaveletCompression.decompressChannel(compressedRedChannel);
    int[][] decompressedGreenChannel = HaarWaveletCompression.decompressChannel(compressedGreenChannel);
    int[][] decompressedBlueChannel = HaarWaveletCompression.decompressChannel(compressedBlueChannel);


    Image compressedImage = new RGBImage(decompressedRedChannel, decompressedGreenChannel,
            decompressedBlueChannel);
    images.put(newImageName, compressedImage);
  }


  @Override
  public void setImage(BufferedImage image, String fileName) {
    Image rgbImage = new RGBImage(image);
    images.put(fileName, rgbImage);
  }

  @Override
  public BufferedImage getImage(String fileName) {
    if (images.containsKey(fileName)) {
      return images.get(fileName).getBufferedImage();
    }
    throw new IllegalArgumentException("Image " + fileName + " does not exist.");

  }

  @Override
  public void brighten(int increment, String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    int[][][] channels = image.getChannels();
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        for (int channel = 0; channel < 3; channel++) {
          channels[channel][row][col] = channels[channel][row][col] + increment;
        }
      }
    }

    Image brightenedImage = new RGBImage(channels[0], channels[1], channels[2]);

    images.put(newImageName, brightenedImage);
  }

  @Override
  public void blur(String imageName, String newImageName) {
    double[][] filter = {{1.0 / 16, 2.0 / 16, 1.0 / 16}, {2.0 / 16, 4.0 / 16, 2.0 / 16}, {1.0 / 16, 2.0 / 16, 1.0 / 16}};
    Image image = getExistingImage(imageName);

    if (images.containsKey(newImageName)) {
      throw new IllegalArgumentException("Image " + newImageName + " already exists.");
    }

    int[][][] channels = image.getChannels();

    for (int channel = 0; channel < 3; channel++) {
      channels[channel] = applyFilter(channels[channel], filter);
    }

    Image blurredImage = new RGBImage(channels[0], channels[1], channels[2]);

    images.put(newImageName, blurredImage);

  }

  @Override
  public void sharpen(String imageName, String newImageName) {
    double[][] filter = {{-1, -1, -1}, {-1, 9, -1}, {-1, -1, -1}};
    Image image = getExistingImage(imageName);

    if (images.containsKey(newImageName)) {
      throw new IllegalArgumentException("Image " + newImageName + " already exists.");
    }

    int[][][] channels = image.getChannels();

    for (int channel = 0; channel < 3; channel++) {
      channels[channel] = applyFilter(channels[channel], filter);
    }

    Image sharpenedImage = new RGBImage(channels[0], channels[1], channels[2]);

    images.put(newImageName, sharpenedImage);

  }


  @Override
  public void flipVertically(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    int[][][] channels = image.getChannels();
    for (int channel = 0; channel < 3; channel++) {
      channels[channel] = flipChannel(channels[channel], true, false);
    }
    Image flippedImage = new RGBImage(channels[0], channels[1], channels[2]);
    images.put(newImageName, flippedImage);

  }


  @Override
  public void flipHorizontally(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    int[][][] channels = image.getChannels();
    for (int channel = 0; channel < 3; channel++) {
      channels[channel] = flipChannel(channels[channel], false, true);
    }
    Image flippedImage = new RGBImage(channels[0], channels[1], channels[2]);
    images.put(newImageName, flippedImage);
  }


  @Override
  public void sepia(String imageName, String newImageName) {

    Image image = getExistingImage(imageName);
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
    images.put(newImageName, sepiaImage);


  }

  @Override
  public void rgbSplit(String imageName, String imageNameRed, String imageNameGreen, String imageNameBlue) {

    Image image = getExistingImage(imageName);
    int[][][] channels = image.getChannels();
    Image redImage = new RGBImage(channels[0], null, null);
    Image greenImage = new RGBImage(null, channels[1], null);
    Image blueImage = new RGBImage(null, null, channels[2]);
    images.put(imageNameRed, redImage);
    images.put(imageNameGreen, greenImage);
    images.put(imageNameBlue, blueImage);

  }

  @Override
  public void rgbCombine(String imageName, String imageNameRed, String imageNameGreen, String imageNameBlue) {
    Image redImage = getExistingImage(imageNameRed);
    Image greenImage = getExistingImage(imageNameGreen);
    Image blueImage = getExistingImage(imageNameBlue);

    int[][][] channels = new int[3][redImage.getHeight()][redImage.getWidth()];
    channels[0] = redImage.getChannels()[0];
    channels[1] = greenImage.getChannels()[1];
    channels[2] = blueImage.getChannels()[2];

    Image rgbImage = new RGBImage(channels[0], channels[1], channels[2]);
    images.put(imageName, rgbImage);
  }

  @Override
  public void getColorComponent(String imageName, Channel component, String newImageName) {
    Image image = getExistingImage(imageName);
    int[][] channel = image.getChannel(component);
    if (component == Channel.RED) {
      Image redImage = new RGBImage(channel, null, null);
      images.put(newImageName, redImage);
    } else if (component == Channel.GREEN) {
      Image greenImage = new RGBImage(null, channel, null);
      images.put(newImageName, greenImage);
    } else if (component == Channel.BLUE) {
      Image blueImage = new RGBImage(null, null, channel);
      images.put(newImageName, blueImage);
    } else {
      throw new IllegalArgumentException("Invalid channel.");
    }

  }

  @Override
  public void getBrightnessComponent(String imageName, Brightness brightness, String newImageName) {
    Image image = getExistingImage(imageName);
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

    images.put(newImageName, resultImage);

  }

  private Image getExistingImage(String imageName) {
    return images.get(imageName);
  }
}
