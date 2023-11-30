package grime.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 800;
  private JMenuItem loadMenuItem;
  private JMenuItem saveMenuItem;
  private JComboBox<String> imageComboBox;
  private DialogManager dialogManager;
  private JButton refreshButton; // Declare the refresh button


  public GUIView() {
    super("GRIME");
    setupWindow();
    setupComponents();
    makeVisible();
    this.dialogManager = new DialogManager();
  }

  private void setupWindow() {
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  private void setupComponents() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    loadMenuItem = new JMenuItem("Load");
    fileMenu.add(loadMenuItem);

    saveMenuItem = new JMenuItem("Save");
    fileMenu.add(saveMenuItem);

    JMenu editMenu = new JMenu("Edit");

    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    setJMenuBar(menuBar);

    imageComboBox = new JComboBox<>();
    add(imageComboBox, BorderLayout.NORTH);
    add(imageComboBox, BorderLayout.NORTH);


    refreshButton = new JButton("Refresh Images");
    add(refreshButton, BorderLayout.SOUTH);


    JPanel imagePanel = new JPanel();
    add(imagePanel, BorderLayout.CENTER);
  }


  public void makeVisible() {
    setVisible(true);
  }

  public void addListener(String actionCommand, ActionListener listener) {
    switch (actionCommand) {
      case "load":
        loadMenuItem.addActionListener(listener);
        break;
      case "save":
        saveMenuItem.addActionListener(listener);
        break;
      case "list-images":
        imageComboBox.addActionListener(listener);
        break;
      case "refresh-images":
        refreshButton.addActionListener(listener);
        break;
      default:
        throw new IllegalArgumentException("Invalid action command");
    }
  }

  @Override
  public void updateView(String viewType, Object data) {
    if ("image-list".equals(viewType) && data instanceof java.util.List) {
      imageComboBox.removeAllItems();
      java.util.List<String> images = (java.util.List<String>) data;
      for (String image : images) {
        imageComboBox.addItem(image);
      }
    }
  }


  @Override
  public void displayMessage(String message) {
    dialogManager.showMessage(this, message);
  }

  @Override
  public String getInput(String prompt) {
    return dialogManager.getInput(this, prompt);
  }
}

// Other classes like DialogManager, etc., are not included here but should be defined as needed.
