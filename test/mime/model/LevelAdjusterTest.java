package mime.model;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import mime.model.operations.LevelAdjuster;

public class LevelAdjusterTest {

  LevelAdjuster levelAdjuster = new LevelAdjuster();

  @Test
  public void testLevelAdjuster() {
    // read resources/test.jpg
    BufferedImage img1 = null;
    try {
      img1 = ImageIO.read(new File("resources/test.jpg"));
      img1 = levelAdjuster.adjustLevels(img1, 15, 158, 238);
      ImageIO.write(img1, "jpg", new File("resources/level-adjusted.jpg"));
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}