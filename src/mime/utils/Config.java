package mime.utils;

public class Config {
  private boolean graphicalMode = false;
  private boolean textMode = false;
  private String filePath = null;

  public boolean isGraphicalMode() {
    return graphicalMode;
  }

  public void setGraphicalMode(boolean graphicalMode) {
    this.graphicalMode = graphicalMode;
  }

  public boolean isTextMode() {
    return textMode;
  }

  public void setTextMode(boolean textMode) {
    this.textMode = textMode;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}

