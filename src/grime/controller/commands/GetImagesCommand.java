package grime.controller.commands;

import java.util.List;

import grime.model.Model;

public class GetImagesCommand implements Command {
  private Model model;
  private List<String> images;

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
