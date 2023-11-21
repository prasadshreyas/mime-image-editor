package mime.controller;

import java.util.List;

import mime.model.Model;
import mime.view.View;

public class GUIController extends AbstractController {

  public GUIController(Model model, View view) {
    super(model, view);
  }

  @Override
  protected List<String> readInput() {
    // Implementation for GUI input (e.g., button clicks, text fields)
    return null; // Placeholder
  }
}
