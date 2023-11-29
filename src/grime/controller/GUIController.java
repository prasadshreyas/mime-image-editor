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

    view.addListener("list-images", new ComboBoxUpdateListener());
    CommandRegistry.registerCommand("list-images", new LoadCommand(model));
  }

  public void run() {
    view.displayMessage("Controller is running.");
  }

  private void handleException(Exception e) {
    view.displayMessage(e.getMessage());
    e.printStackTrace();
  }

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

  private class ComboBoxUpdateListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        updateImageList();
      } catch (Exception ex) {
        handleException(ex);
      }
    }

    private void updateImageList() {
      String[] imageList = model.getImages().toArray(new String[0]);
      view.updateView("list-images", imageList);
    }
  }

  private class LoadButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "gif");
        fileChooser.setFileFilter(imageFilter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          java.io.File selectedFile = fileChooser.getSelectedFile();
          String selectedFilePath = selectedFile.getAbsolutePath();
          String imageName = view.getInput("Enter the name of the image: ");

          String[] args = { selectedFilePath, imageName };
          executeCommand("load", args);
        }
      } catch (Exception ex) {
        handleException(ex);
      }
    }
  }

  // Additional ActionListener classes for other functionalities like 'save' can be added here.
}
