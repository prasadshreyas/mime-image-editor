package grime.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 800;
  private JMenuItem loadMenuItem; // Replaced JButton with JMenuItem
  private JMenuItem saveMenuItem; // Initialize JMenuItem

  private JComboBox<String> imageComboBox; // ComboBox for selecting the image to display


  public GUIView() {
    super("GRIME");
    setupWindow();
    setupComponents();
    makeVisible();
  }

  private void setupWindow() {
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  private void setupComponents() {
    // Removed button panel and load button

    // Add a drop-down menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    loadMenuItem = new JMenuItem("Load"); // Initialize JMenuItem
    fileMenu.add(loadMenuItem);

    saveMenuItem = new JMenuItem("Save"); // Initialize JMenuItem
    fileMenu.add(saveMenuItem);

    JMenu editMenu = new JMenu("Edit");

    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    setJMenuBar(menuBar);

    // Add a combo box for selecting the image to display
    imageComboBox = new JComboBox<String>();

    // Add the combo box to the window
    add(imageComboBox, BorderLayout.NORTH);

    // Add a panel for displaying the image
    JPanel imagePanel = new JPanel();

    // Add the panel to the window
    add(imagePanel, BorderLayout.CENTER);


  }


  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void makeVisible() {
    setVisible(true);
  }

  public void addListener(String actionCommand, ActionListener listener) {
    if (actionCommand.equals("load")) {
      loadMenuItem.addActionListener(listener); // Attach listener to menu item
    } else if (actionCommand.equals("save")) {
      saveMenuItem.addActionListener(listener); // Attach listener to menu item
    } else {
      throw new IllegalArgumentException("Invalid action command");
    }
  }

  @Override
  public String getInput(String prompt) {
    return JOptionPane.showInputDialog(this, prompt);
  }
}
