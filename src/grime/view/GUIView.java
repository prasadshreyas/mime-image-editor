package grime.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
  private JPanel imagePanel; // Declare the image panel
  private JLabel imageLabel; // To display the image


  public GUIView() {
    super("GRIME");
    setupWindow();
    setupComponents();
    makeVisible();
    this.dialogManager = new DialogManager();
  }

  private void setupWindow() {
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Change to do nothing on close
    setLayout(new BorderLayout());

    // Add window listener for close operation
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        if (JOptionPane.showConfirmDialog(GUIView.this,
                "Are you sure you want to close this window?", "Close Window?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
          System.exit(0);
        }
      }
    });
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


    imagePanel = new JPanel();
    add(imagePanel, BorderLayout.CENTER);
    imageLabel = new JLabel();
    imagePanel.add(imageLabel);
  }

  // Method to update the image in the view
  public void updateImage(BufferedImage image) {
    // Scale the image
    BufferedImage scaledImage = scaleImage(image, imagePanel.getWidth(), imagePanel.getHeight());

    ImageIcon imageIcon = new ImageIcon(scaledImage);
    imageLabel.setIcon(imageIcon);
    imageLabel.revalidate();
    imageLabel.repaint();
  }

  // Method to scale the image
  private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
    // Create a new scaled image
    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = outputImage.createGraphics();
    g2d.drawImage(resultingImage, 0, 0, null);
    g2d.dispose();

    return outputImage;
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
    if ("image-display".equals(viewType) && data instanceof BufferedImage) {
      BufferedImage image = (BufferedImage) data;
      updateImage(image);
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

