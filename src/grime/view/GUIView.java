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
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represents the Graphical User Interface view for the program.
 */
public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private JPanel buttonPanel;

  private JPanel mainPanel;

  private JPanel imagePanel;

  private JScrollPane mainScrollPane;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;

  private JLabel imageLabel;

 private JScrollPane imageScrollPane;


  /**
   * Constructs a GUIView object and initializes it to display the GUI.
   */
  public GUIView() {
    super();

    initializeWindow();


   initializeButtonPanel();


    setVisible(true);
  }

  private void initializeButtonPanel() {
    final JButton commandButton;
    buttonPanel = new JPanel();


    // Load
    JButton loadButton = new JButton("Load");
    loadButton.setActionCommand("load");
    loadButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int returnValue = fileChooser.showOpenDialog(null);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        System.out.println(fileChooser.getSelectedFile().getPath());
        updateImage(fileChooser.getSelectedFile().getPath());
        //
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

    //



    add(buttonPanel, BorderLayout.SOUTH);
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

  private void updateImage(String Filename)
  {
    imageLabel.setIcon(new ImageIcon(Filename));
    imageScrollPane.setPreferredSize(new Dimension(100, 400));
  }

}
