package grime.controller;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import grime.controller.commands.Command;
import grime.controller.commands.CommandFactory;
import grime.model.Model;
import grime.view.View;

/**
 * This class represents the controller for the GUI mode.
 */
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
    view.addListener("list-images", this::imageSelectedAction);
    view.addListener("save", this::saveAction);
  }


  private void imageSelectedAction(ActionEvent e) {
    JComboBox comboBox = (JComboBox) e.getSource();
    String selectedImageName = (String) comboBox.getSelectedItem();
    if (selectedImageName != null && !selectedImageName.isEmpty()) {
      try {
        BufferedImage image = model.getImage(selectedImageName);
        view.updateView("image-display", image);
      } catch (Exception ex) {
        handleException(ex);
      }
    }
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

  private void saveAction(ActionEvent e) {
    BufferedImage imageToSave = view.getCurrentImage();
    if (imageToSave == null) {
      JOptionPane.showMessageDialog(null, "No image to save.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    // Set file extension filters
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG file", "png"));
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("BMP file", "bmp"));
    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PPM file", "ppm")); // Implement PPM format save if needed

    fileChooser.setAcceptAllFileFilterUsed(false);

    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
      try {
        File fileToSave = fileChooser.getSelectedFile();
        String ext = getFileExtension(fileToSave.getName());
        ImageIO.write(imageToSave, ext, fileToSave);
        // Display success message
        JOptionPane.showMessageDialog(null, "Image saved successfully.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error saving image: "
                + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private String getFileExtension(String fileName) {
    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      return fileName.substring(i + 1);
    } else {
      return "jpg"; // Default format
    }
  }

  private void handleException(Exception e) {
    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
  }

  // Additional methods and inner classes
}
