package grime.controller;

import grime.model.Model;
import grime.view.View;

public class GUIController implements Controller {
  private final Model model;
  private final View view;

  public GUIController(Model model, View view) {
    this.model = model;
    this.view = view;

  }

  @Override
  public void run() {

  }

  // Implement other methods for different actions as needed
}
