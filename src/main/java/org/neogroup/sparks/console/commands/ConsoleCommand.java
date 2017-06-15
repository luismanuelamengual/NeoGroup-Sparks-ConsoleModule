
package org.neogroup.sparks.console.commands;

import org.neogroup.sparks.console.Command;
import org.neogroup.sparks.console.Console;

/**
 * Console command for the sparks framework
 */
public class ConsoleCommand extends org.neogroup.sparks.commands.Command {

    private final Console console;
    private final Command command;

    /**
     * Constructor for the command
     * @param console console
     * @param command command entered
     */
    public ConsoleCommand(Console console, Command command) {
        this.console = console;
        this.command = command;
    }

    /**
     * Get the current console
     * @return console
     */
    public Console getConsole() {
        return console;
    }

    /**
     * Get the current command entered
     * @return command entered
     */
    public Command getCommand() {
        return command;
    }
}
