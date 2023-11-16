package mime.model;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import mime.model.operations.ColorCorrection;

public class ColorCorrectionTest {
  ColorCorrection colorCorrection = new ColorCorrection();

  @Test
  public void testCorrectColor() {
    try {
      BufferedImage inputImage = ImageIO.read(new File("resources/galaxy.png"));
      BufferedImage correctedImage = colorCorrection.correctColor(inputImage);
      ImageIO.write(correctedImage, "png", new File("resources/corrected-galaxy.png"));


    } catch (Exception e) {
      System.out.println("Uniform color test failed with exception: " + e.getMessage());
    }
  }
}