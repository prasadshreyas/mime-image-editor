package mime.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import mime.model.image.Image;
import mime.model.image.RGBImage;
import mime.model.operations.Compression;
import mime.model.operations.Histogram;
import mime.model.operations.ImageProcessor;

/**
 * This class represents the model of the image in the RGB color space.
 */
public class RGBModel implements Model {
  private HashMap<String, Image> images;
  private ImageProcessor imageProcessor;


  /**
   * Constructs HashMap of images.
   */
  public RGBModel() {
    images = new HashMap<>();
    imageProcessor = new ImageProcessor();
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

    Compression compression = new Compression();

    int[][][] channels = image.getChannels();
    int[][][] decompressedChannels = new int[3][][];

    for (int i = 0; i < 3; i++) { // Assuming 0 is red, 1 is green, and 2 is blue
      // Compress
      decompressedChannels[i] = compression.compressAndUncompress(channels[i], percentage);

    }

    // Create a new image with the decompressed channels and add it to the collection
    Image compressedImage = new RGBImage(decompressedChannels[0], decompressedChannels[1], decompressedChannels[2]);
    images.put(newImageName, compressedImage);
  }

  @Override
  public void levelAdjuster(String imageName, int shadow, int mid, int highlight, String newImageName) {

  }

  @Override
  public void histogram(String imageName, String newImageName) {

    Image image = getExistingImage(imageName);
    BufferedImage img1 = image.getBufferedImage();
    Histogram h = new Histogram(img1);
    BufferedImage histogram1 = h.createHistogram();
    images.put(newImageName, new RGBImage(histogram1));
  }

  @Override
  public void colorCorrection(String imageName, String newImageName, int splitValue) {

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
    Image brightenedImage = imageProcessor.getBrightenedImage(increment, image);

    images.put(newImageName, brightenedImage);
  }




  public void blur(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Image blurredImage = imageProcessor.blur(image);
    images.put(newImageName, blurredImage);
  }

  public void sharpen(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Image sharpenedImage = imageProcessor.sharpen(image);
    images.put(newImageName, sharpenedImage);
  }


  @Override
  public void flipVertically(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Image flippedImage = imageProcessor.flipVertically(image);
    images.put(newImageName, flippedImage);
  }


  @Override
  public void flipHorizontally(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Image flippedImage = imageProcessor.flipHorizontally(image);
    images.put(newImageName, flippedImage);
  }


  @Override
  public void sepia(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Image sepiaImage = imageProcessor.sepia(image);
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

  int clamp(int value) {
    return Math.min(Math.max(value, 0), 255);
  }

  @Override
  public void splitView(String img1, String img2, int percentage) {
    Image image = getExistingImage(img1);
    Image image2 = getExistingImage(img2);
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
    images.put(img2, splitImage);

  }

  private Image getExistingImage(String imageName) {
    return images.get(imageName);
  }
}
