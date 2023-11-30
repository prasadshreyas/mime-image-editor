package grime.view;

import grime.controller.GUIController;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.List;
import java.util.ArrayList;
/**
 * This class represents the Graphical User Interface view for the program.
 */
public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private JPanel buttonPanel1 = new JPanel(new FlowLayout());
  private JPanel buttonPanel2 = new JPanel(new FlowLayout());
  private JPanel buttonPanel3 = new JPanel(new FlowLayout());

  private JPanel mainPanel;

  private JPanel imagePanel;

  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private static JLabel imageLabel;

  private static JLabel imageLabel1;

 private static JScrollPane imageScrollPane;
 private static JScrollPane imageScrollPane1;

 String previous_file = "";
    /**
   * Constructs a GUIView object and initializes it to display the GUI.
   */
  public GUIView() {
    super("GRIME");
    initializeWindow();
    initializeButtonPanel();
    setVisible(true);
  }


  private void initializeButtonPanel() {
    final JButton commandButton;


      // Load
    JButton loadButton = new JButton("Load");
    loadButton.setActionCommand("load");
    loadButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        System.out.println(fileChooser.getSelectedFile().getPath());
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("load");
        lineArgs.add(fileChooser.getSelectedFile().getPath());
        lineArgs.add("image");
          previous_file = "image";
        GUIController.executeCommand(lineArgs);
//        GUIController.executeCommand(Collections.singletonList("load something D:\\Bluepen\\L10\\MIME\\Koala.jpg"));
       // BufferedImage Model = RGBModel.getImage();
        //updateImage(fileChooser.getSelectedFile().getPath());

      }
    });
    buttonPanel1.add(loadButton);

    //Save
    JButton saveButton = new JButton("Save");
    saveButton.setActionCommand("save");
    saveButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        System.out.println(fileChooser.getSelectedFile().getPath());
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("save");
        lineArgs.add(fileChooser.getSelectedFile().getPath());
        lineArgs.add(previous_file);
        GUIController.executeCommand(lineArgs);
        //CLI to save image
      }
    });
    buttonPanel1.add(saveButton);

    //red-component-view
    JButton redViewButton = new JButton("View Red Component");
    redViewButton.setActionCommand("view-red");
    redViewButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("red-component");
        lineArgs.add(previous_file);
        lineArgs.add("red-image");
        previous_file = "red-image";
        GUIController.executeCommand(lineArgs);
    });
    buttonPanel2.add(redViewButton);

    //blue-component-view
    JButton blueViewButton = new JButton("View Blue Component");
    blueViewButton.setActionCommand("view-blue");
    blueViewButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("blue-component");
        lineArgs.add(previous_file);
        lineArgs.add("blue-image");
        previous_file = "blue-image";
        GUIController.executeCommand(lineArgs);
    });
    buttonPanel2.add(blueViewButton);

    //green-component-view
    JButton greenViewButton = new JButton("View Green Component");
    greenViewButton.setActionCommand("view-green");
    greenViewButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("green-component");
        lineArgs.add(previous_file);
        lineArgs.add("green-image");
        previous_file = "green-image";
        GUIController.executeCommand(lineArgs);
    });
    buttonPanel2.add(greenViewButton);

    //Blur
    JButton blurButton = new JButton("Blur");
    blurButton.setActionCommand("blur");
    blurButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("blur");
        lineArgs.add(previous_file);
        lineArgs.add("blur-image");
        previous_file = "blur-image";
        GUIController.executeCommand(lineArgs);


    });
    buttonPanel2.add(blurButton);

    // quit button
    commandButton = new JButton("Quit");
    commandButton.setActionCommand("quit");
    commandButton.addActionListener(e -> System.exit(0));
    buttonPanel3.add(commandButton);

    //

    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    add(buttonPanel1);
    add(buttonPanel2);
    add(buttonPanel3, BorderLayout.SOUTH);
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

    String images = "";
    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(images));
    imageScrollPane.setPreferredSize(new Dimension(100, 400));
    imagePanel.add(imageScrollPane);

    JPanel imagePanel1 = new JPanel();
    imageLabel1 = new JLabel();
    imageScrollPane1 = new JScrollPane(imageLabel1);
    imageLabel1.setIcon(new ImageIcon(images));
    imageScrollPane1.setPreferredSize(new Dimension(100, 400));
    imagePanel.add(imageScrollPane1);

    mainPanel.add(imagePanel1);




      //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

  }


  @Override
  public void display(String message) {
    JOptionPane.showMessageDialog(this, message);

  }

    public static void updateHistogram(BufferedImage image)
    {
        imageLabel1.setIcon(new ImageIcon(image));
    }

  public static void updateImage(BufferedImage image)
  {
    imageLabel.setIcon(new ImageIcon(image));
   // pack();
    //imageScrollPane.setPreferredSize(new Dimension(100, 400));
  }
}
