package mime.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import mime.model.image.Image;
import mime.model.image.RGBImage;
import mime.model.operations.ColorCorrection;
import mime.model.operations.Compression;
import mime.model.operations.Histogram;
import mime.model.operations.ImageProcessor;
import mime.model.operations.LevelAdjuster;

/**
 * This class represents the model of the image in the RGB color space.
 */
public class RGBModel implements Model {
  private final HashMap<String, Image> images;
  private final ImageProcessor imageProcessor;


  /**
   * Constructs HashMap of images.
   */
  public RGBModel() {
    images = new HashMap<>();
    imageProcessor = new ImageProcessor();
  }

  @Override
  public boolean containsImage(String imageName) {
    return images.containsKey(imageName);
  }

  @Override
  public void compressImage(int percentage, String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Compression compression = new Compression(image, percentage);
    Image newImage = compression.compressAndUncompress();
    images.put(newImageName, newImage);
  }

  @Override
  public void levelAdjuster(String imageName, int shadow, int mid, int highlight,
                            String newImageName) {
    Image image = getExistingImage(imageName);
    LevelAdjuster levelAdjuster = new LevelAdjuster();
    BufferedImage adjustedImage = levelAdjuster.adjustLevels(image.getBufferedImage(),
            shadow, mid, highlight);
    Image rgbImage = new RGBImage(adjustedImage);
    images.put(newImageName, rgbImage);
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
  public void colorCorrection(String imageName, String newImageName) {

    Image image = getExistingImage(imageName);
    ColorCorrection colorCorrection = new ColorCorrection();
    BufferedImage correctedImage = colorCorrection.correctColor(image.getBufferedImage());
    Image rgbImage = new RGBImage(correctedImage);
    images.put(newImageName, rgbImage);
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

  @Override
  public void blur(String imageName, String newImageName) {
    Image image = getExistingImage(imageName);
    Image blurredImage = imageProcessor.blur(image);
    images.put(newImageName, blurredImage);
  }

  @Override
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
  public void rgbSplit(String imageName, String imageNameRed, String imageNameGreen,
                       String imageNameBlue) {

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
  public void rgbCombine(String imageName, String imageNameRed, String imageNameGreen,
                         String imageNameBlue) {
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
    Image resultImage = imageProcessor.getGrayscaleImage(brightness, image);
    images.put(newImageName, resultImage);
  }

  @Override
  public void splitView(String img1, String img2, int percentage) {
    Image image = getExistingImage(img1);
    Image image2 = getExistingImage(img2);
    Image splitImage = imageProcessor.getSplitView(percentage, image, image2);
    images.put(img2, splitImage);

  }


  private Image getExistingImage(String imageName) {
    return images.get(imageName);
  }
}
