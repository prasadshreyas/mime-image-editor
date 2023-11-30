package grime.view;

import grime.controller.GUIController;
import grime.model.RGBModel;
import grime.model.image.RGBImage;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;

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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
 * This class represents the Graphical User Interface view for the program.
 */
public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private JPanel buttonPanel1;
  private JPanel buttonPanel2;
  private JPanel buttonPanel3;

  private JPanel mainPanel;

  private JPanel imagePanel;

  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private static JLabel imageLabel;

 private static JScrollPane imageScrollPane;


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
    buttonPanel1 = new JPanel(new FlowLayout());
    buttonPanel2 = new JPanel(new FlowLayout());
    buttonPanel3 = new JPanel(new FlowLayout());


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
        lineArgs.add("image");
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
        lineArgs.add("image");
        lineArgs.add("red-image");

        GUIController.executeCommand(lineArgs);
    });
    buttonPanel2.add(redViewButton);

    //blue-component-view
    JButton blueViewButton = new JButton("View Blue Component");
    blueViewButton.setActionCommand("view-blue");
    blueViewButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("blue-component");
        lineArgs.add("image");
        lineArgs.add("blue-image");
        GUIController.executeCommand(lineArgs);
    });
    buttonPanel2.add(blueViewButton);

    //green-component-view
    JButton greenViewButton = new JButton("View Green Component");
    greenViewButton.setActionCommand("view-green");
    greenViewButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("green-component");
        lineArgs.add("image");
        lineArgs.add("green-image");
        GUIController.executeCommand(lineArgs);
    });
    buttonPanel2.add(greenViewButton);

    //Save
    JButton blurButton = new JButton("Blur");
    blurButton.setActionCommand("blur");
    blurButton.addActionListener(e -> {
        List<String> lineArgs = new ArrayList<>();
        lineArgs.add("blur");
        lineArgs.add("image");
        lineArgs.add("blur-image");
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

  }


  @Override
  public void display(String message) {
    JOptionPane.showMessageDialog(this, message);

  }

  public static void updateImage(BufferedImage image)
  {
    imageLabel.setIcon(new ImageIcon(image));
   // pack();
    imageScrollPane.setPreferredSize(new Dimension(100, 400));
  }
}
