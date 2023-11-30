package grime.controller;

import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import grime.controller.commands.Command;
import grime.controller.commands.CommandFactory;
import grime.model.Model;
import grime.view.View;

public class GUIController implements Controller {
  private final Model model;
  private final View view;

  public GUIController(Model model, View view) {
    this.model = model;
    this.view = view;
    addListeners();
    listImagesAction(); // Populate the ComboBox initially
  }

  private void addListeners() {
    view.addListener("load", this::loadAction);
    view.addListener("list-images", this::listImagesAction);
    view.addListener("refresh-images", this::refreshImagesAction);
  }


  private void refreshImagesAction(ActionEvent e) {
    listImagesAction();
  }

  private void listImagesAction(ActionEvent actionEvent) {
    java.util.List<String> images = model.getImages();
    view.updateView("image-list", images);
  }

  private void listImagesAction() {
    java.util.List<String> images = model.getImages();
    view.updateView("image-list", images);
  }


  private void loadAction(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "png", "jpg", "jpeg", "gif");
    fileChooser.setFileFilter(imageFilter);

    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
      try {
        java.io.File selectedFile = fileChooser.getSelectedFile();
        String selectedFilePath = selectedFile.getAbsolutePath();
        String imageName = view.getInput("Enter the name of the image: ");

        String[] args = {selectedFilePath, imageName};
        executeCommand("load", args);
      } catch (Exception ex) {
        handleException(ex);
      }
    }
  }

  public void run() {
    view.displayMessage("Controller is running.");
  }

  public void executeCommand(String commandName, String[] args) {
    try {
      Command command = CommandFactory.createCommand(commandName, model);
      command.execute(args);
    } catch (Exception e) {
      handleException(e);
    }
  }

  private void handleException(Exception e) {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
  }

  // Additional methods and inner classes
}
