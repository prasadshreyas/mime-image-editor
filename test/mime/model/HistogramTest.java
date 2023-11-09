package mime.model;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

public class HistogramTest {
  @Test
  public void testCreateHistogram() {

//    load image at resources/test.jpg
    BufferedImage img1 = null;
    try {
      img1 = ImageIO.read(new File("resources/galaxy.png"));
    }
    catch (Exception e) {
      System.out.println(e);
    }

    BufferedImage histogram1 = Histogram.createHistogram(img1);

    // save the histogram image
    try {
      // overwrite the histogram image
      ImageIO.write(histogram1, "png", new File("resources/histogram2.png"));
    }
    catch (Exception e) {
      System.out.println(e);
    }


  }

}