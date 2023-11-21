package grime.view;

import java.awt.*;

import javax.swing.*;

/**
 * This class represents the Graphical User Interface view for the program.
 */
public class GUIView extends JFrame implements View {
  private static final long serialVersionUID = 1L;
  private JPanel buttonPanel;

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
      }
    });
    buttonPanel.add(loadButton);

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
  }


  @Override
  public void display(String message) {
    JOptionPane.showMessageDialog(this, message);

  }
}
