package mime.model;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import mime.model.operations.Histogram;

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
    Histogram h = new Histogram(img1);

    BufferedImage histogram1 = h.createHistogram();

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