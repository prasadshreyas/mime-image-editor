package grime.utils;

/**
 * This class represents the configuration for the program.
 */
public class Config {
  private boolean graphicalMode = false;
  private boolean commandLineMode = false;
  private boolean fileMode = false;
  private String filePath = null;

  /**
   * Checks if the graphical mode is set.
   *
   * @return true if the graphical mode is set, false otherwise.
   */
  public boolean isGraphicalMode() {
    return graphicalMode;
  }

  public void setGraphicalMode(boolean graphicalMode) {
    this.graphicalMode = graphicalMode;
  }

  /**
   * Checks if the command line mode is set.
   *
   * @return true if the command line mode is set, false otherwise.
   */
  public boolean isCommandLineMode() {
    return commandLineMode;
  }

  /**
   * Sets the command line mode.
   *
   * @param commandLineMode true if the command line mode is set, false otherwise.
   */
  public void setCommandLineMode(boolean commandLineMode) {
    this.commandLineMode = commandLineMode;
  }

  /**
   * Gets the file path.
   *
   * @return the file path.
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Sets the file path.
   *
   * @param filePath the file path.
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Checks if the file mode is set.
   *
   * @return true if the file mode is set, false otherwise.
   */
  public boolean isFileMode() {
    return fileMode;
  }

  /**
   * Sets the file mode.
   *
   * @param fileMode true if the file mode is set, false otherwise.
   */
  public void setFileMode(boolean fileMode) {
    this.fileMode = fileMode;
  }

  /**
   * Checks if the configuration is valid.
   *
   * @return true if the configuration is valid, false otherwise.
   */
  public boolean isValid() {
    return (graphicalMode || commandLineMode) && (fileMode || filePath == null);
  }


  @Override
  public String toString() {
    return "Config [graphicalMode=" + graphicalMode + ", commandLineMode=" + commandLineMode + ", fileMode=" + fileMode
            + ", filePath=" + filePath + "]";
  }

}

