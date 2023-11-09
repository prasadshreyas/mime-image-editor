package mime.model;

import org.junit.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import static mime.model.LevelAdjuster.adjustLevels;
import static org.junit.Assert.*;

public class LevelAdjusterTest {

  @Test
  public void testLevelAdjuster(){
    // read resources/test.jpg
    BufferedImage img1 = null;
    try {
      img1 = ImageIO.read(new File("resources/test.jpg"));
      img1 = adjustLevels(img1, 0, 128, 255);
      ImageIO.write(img1, "jpg", new File("resources/level-adjusted.jpg"));
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

}