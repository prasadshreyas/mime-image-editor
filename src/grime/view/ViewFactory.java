package grime.view;

import grime.utils.Config;

public class ViewFactory {

  /**
   * Creates and returns a View object based on the provided configuration or context.
   *
   * @param config The application configuration that determines the type of view to create.
   * @return A concrete implementation of the View interface.
   */
  public static View createView(Config config) {
    if (config.isGraphicalMode()) {
      return new GUIView();
    } else {
      return new CommandLineView();
    }
  }
}
