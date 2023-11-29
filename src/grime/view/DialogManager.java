package grime.view;

import java.awt.*;

import javax.swing.*;

public class DialogManager {
  public void showMessage(Component parentComponent, String message) {
    JOptionPane.showMessageDialog(parentComponent, message);
  }

  public String getInput(Component parentComponent, String prompt) {
    return JOptionPane.showInputDialog(parentComponent, prompt);
  }
}
