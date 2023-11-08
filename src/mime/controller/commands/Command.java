package mime.controller.commands;

public interface Command {
  void execute(String[] args) throws Exception;
}