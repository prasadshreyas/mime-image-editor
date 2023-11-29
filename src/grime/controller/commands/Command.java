package grime.controller.commands;

/**
 * This interface represents a command that can be executed on the model.
 */
public interface Command {

  /**
   * Executes the command on the model.
   * @param args Arguments for the command.
   * @throws Exception If the command cannot be executed.
   */
  void execute(String[] args) throws Exception;


}