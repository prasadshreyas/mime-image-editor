package grime.view;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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

  private JPanel mainPanel;

  private JPanel imagePanel;

  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private JLabel imageLabel;

 private JScrollPane imageScrollPane;


  /**
   * Constructs a GUIView object.
   */
  public GUIView() {

    super("GRIME"); // Title for the window
    initializeWindow();
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

   initializeButtonPanel();
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

    // Load
    JButton loadButton = new JButton("Load");
    loadButton.setActionCommand("load");
    loadButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        System.out.println(fileChooser.getSelectedFile().getPath());
        updateImage(fileChooser.getSelectedFile().getPath());
        
      }
    });
    buttonPanel.add(loadButton);

    //Save
    JButton saveButton = new JButton("Save");
    saveButton.setActionCommand("save");
    saveButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        System.out.println(fileChooser.getSelectedFile().getPath());
        //updateImage(fileChooser.getSelectedFile().getPath());
        File f = fileChooser.getSelectedFile();
        //CLI to save image
      }
    });
    buttonPanel.add(saveButton);

    // quit button
    commandButton = new JButton("Quit");
    commandButton.setActionCommand("quit");
    commandButton.addActionListener(e -> System.exit(0));
    buttonPanel.add(commandButton);

  }

  private void initializeListeners() {
    loadButton.addActionListener(e -> loadImage());
    saveButton.addActionListener(e -> saveImage());
  }

  public void saveImage() {
    // Implement save functionality here
    displayMessage("Save functionality not implemented yet.");
  }

private void initializeWindow() {
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imagePanel);

    String images = "res/Penguins.jpg";
    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(images));
    imageScrollPane.setPreferredSize(new Dimension(100, 400));
    imagePanel.add(imageScrollPane);


    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

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

  private void updateImage(String Filename)
  {
    imageLabel.setIcon(new ImageIcon(Filename));
    imageScrollPane.setPreferredSize(new Dimension(100, 400));
  }

}
