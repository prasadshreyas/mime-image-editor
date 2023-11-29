package grime.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 800;
  private JButton loadButton;
  private JButton saveButton;
  private JPanel buttonPanel;
  private JComboBox<String> dropDown;

  public GUIView() {
    super("My Application"); // Title for the window
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
    dropDown = new JComboBox<>(options); // Initialize the dropdown
    dropDown.setSelectedIndex(0);
    add(dropDown, BorderLayout.NORTH);
  }

  private void setupButtonPanel() {
    buttonPanel = new JPanel();
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

  public void addListener(String actionCommand, ActionListener listener) {
    switch (actionCommand) {
      case "load":
        loadButton.addActionListener(listener);
        break;
      case "save":
        saveButton.addActionListener(listener);
        break;
      default:
        addButton(actionCommand, listener);
        break;
    }
  }

  private void addButton(String label, ActionListener listener) {
    JButton button = new JButton(label);
    button.setActionCommand(label.toLowerCase());
    button.addActionListener(listener);
    buttonPanel.add(button);
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
}
