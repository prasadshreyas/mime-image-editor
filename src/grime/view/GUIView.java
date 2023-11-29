package grime.view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 800;
  private JButton loadButton;

  public GUIView() {
    super("GRIME");
    setupWindow(); // Initialize the window properties
    setupComponents(); // Initialize the GUI components
    makeVisible();
  }

  private void setupWindow() {
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  private void setupComponents() {
    JPanel buttonPanel = new JPanel();
    loadButton = new JButton("Load");

    buttonPanel.add(loadButton);

    add(buttonPanel, BorderLayout.NORTH);
  }

  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  public void makeVisible() {
    setVisible(true);
  }

  public void addListener(String actionCommand, ActionListener listener) {
    if (actionCommand.equals("load")) {
      loadButton.addActionListener(listener);
    } else {
      throw new IllegalArgumentException("Invalid action command");
    }
  }

  @Override
  public String getInput(String prompt) {
    return JOptionPane.showInputDialog(this, prompt);
  }

}