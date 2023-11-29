package grime.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import grime.controller.commands.LoadCommand;
import grime.model.Model;
import grime.view.View;

public class GUIController implements Controller {
  private final Model model;
  private final View view;

  public GUIController(Model model, View view) {
    this.model = model;
    this.view = view;

    view.addListener("load", new LoadButtonListener());
    CommandRegistry.registerCommand("load", new LoadCommand(model));

  }

  public void run() {
    // Display a dummy message when the controller runs
    view.displayMessage("Controller is running.");
  }

  private void handleException(Exception e) {
    view.displayMessage(e.getMessage());
  }

  // Reuse the executeCommand method to execute registered commands
  public void executeCommand(String command, String[] args) {
    try {
      if (CommandRegistry.getCommand(command) != null) {
        CommandRegistry.getCommand(command).execute(args);
      } else {
        handleException(new IllegalArgumentException("Invalid command: " + command));
      }
    } catch (Exception e) {
      handleException(e);
    }
  }

  private class LoadButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Set the file filter to allow only image files (e.g., PNG, JPG)
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "gif");
        fileChooser.setFileFilter(imageFilter);

        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
          // Get the selected file
          java.io.File selectedFile = fileChooser.getSelectedFile();

          // Get the path of the selected file
          String selectedFilePath = selectedFile.getAbsolutePath();

          // Take the input from the user for the name of the image
          String imageName = view.getInput("Enter the name of the image: ");


          // Handle the selected image file using the executeCommand method
          String[] args = {
                  selectedFilePath
                  , imageName
          };


          executeCommand("load", args);
        }
      } catch (Exception ex) {
        handleException(ex);
      }
    }
  }
}
