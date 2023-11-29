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

    imageComboBox = new JComboBox<String>();
    add(imageComboBox, BorderLayout.NORTH);

    JPanel imagePanel = new JPanel();
    add(imagePanel, BorderLayout.CENTER);
  }

  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
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
      default:
        throw new IllegalArgumentException("Invalid action command");
    }
  }

  @Override
  public void updateView(String viewType, Object data) {
    if ("list-images".equals(viewType) && data instanceof String[]) {
      updateComboBox((String[]) data);
    }
  }

  private void updateComboBox(String[] newImageList) {
    if (shouldUpdateComboBox(newImageList)) {
      imageComboBox.removeAllItems();
      for (String image : newImageList) {
        imageComboBox.addItem(image);
      }
      imageComboBox.revalidate();
      imageComboBox.repaint();
    }
  }

  private boolean shouldUpdateComboBox(String[] newImageList) {
    if (imageComboBox.getItemCount() != newImageList.length) {
      return true;
    }
    for (int i = 0; i < newImageList.length; i++) {
      if (!imageComboBox.getItemAt(i).equals(newImageList[i])) {
        return true;
      }
    }
    return false;
  }
  @Override
  public String getInput(String prompt) {
    return JOptionPane.showInputDialog(this, prompt);
  }
}
