package grime.controller.commands;

import java.util.List;

import grime.model.Model;

/**
 * This class represents the command to get the list of images in the model.
 */
public class GetImagesCommand implements Command {
  private Model model;
  private List<String> images;

  /**
   * Constructs a GetImagesCommand object.
   *
   * @param model the model to be used by this command.
   */
  public GetImagesCommand(Model model) {
    this.model = model;
  }

  @Override
  public void execute(String[] args) throws Exception {
    if (args.length != 1) {
      throw new Exception("Invalid number of arguments.");
    }

    images = model.getImages();

  }
}
