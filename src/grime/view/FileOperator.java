package grime.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileOperator {

  /**
   * Opens a file chooser dialog to select an image file.
   *
   * @param parentComponent The parent component for the file chooser dialog.
   * @return The absolute path of the selected file, or null if no file is selected.
   */
  public String selectFile(Component parentComponent) {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
            "Image files", "png", "jpg", "jpeg", "gif");
    fileChooser.setFileFilter(imageFilter);

    int result = fileChooser.showOpenDialog(parentComponent);
    if (result == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }
    return null; // Return null if no file is selected
  }

  // Additional file operations (like saving files) can be added here as needed.
}
