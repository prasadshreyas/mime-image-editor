package grime.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represents the GUI view. It is used to display output to the user.
 */
public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 800;
  private JButton loadButton;
  private JButton saveButton;

  /**
   * Constructs a GUIView object.
   */
  public GUIView() {
    super("GRIME"); // Title for the window
    SwingUtilities.invokeLater(() -> {
      setupWindow();
      setupButtonPanel();
      setUpDropDown();
      initializeListeners(); // Set up listeners here
      setVisible(true); // Make the GUI visible here
    });
  }

  private void setUpDropDown() {
    String[] options = {"1", "2", "3", "4", "5"};
    JComboBox<String> dropDown = new JComboBox<>(options); // Initialize the dropdown
    dropDown.setSelectedIndex(0);
    add(dropDown, BorderLayout.NORTH);
  }

  private void setupButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1, 2));
    loadButton = new JButton("Load");
    saveButton = new JButton("Save");
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void setupWindow() {
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public void loadImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select a file");
    // Image files only
    fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "ppm",
            "jpeg", "bmp"));

    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      displayMessage("File selected: " + fileChooser.getSelectedFile().getAbsolutePath());
    }

    // Now, ask for the name of the image
    String imageName = JOptionPane.showInputDialog(this, "Enter the name of the image");
    displayMessage("Image name: " + imageName);


  }

  private void initializeListeners() {
    loadButton.addActionListener(e -> loadImage());
    saveButton.addActionListener(e -> saveImage());
  }

  public void saveImage() {
    // Implement save functionality here
    displayMessage("Save functionality not implemented yet.");
  }

  @Override
  public void addListener(String actionCommand, ActionListener listener) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setImage(BufferedImage image, String imageName) {

  }

  @Override
  public String getCommand() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void refresh(String imageName) {
    // TODO Auto-generated method stub
  }


}
