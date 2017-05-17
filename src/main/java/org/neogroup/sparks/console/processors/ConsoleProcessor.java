
package org.neogroup.sparks.console.processors;

import org.neogroup.sparks.console.Command;
import org.neogroup.sparks.console.Console;
import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.processors.Processor;
import org.neogroup.sparks.processors.ProcessorException;

/**
 * Processor for console commands
 */
public abstract class ConsoleProcessor extends Processor<ConsoleCommand, Object> {

    /**
     * Processes a command
     * @param command command to process
     * @return response
     * @throws ProcessorException
     */
    @Override
    public Object process(ConsoleCommand command) throws ProcessorException {
        processCommand (command.getConsole(), command.getCommand());
        return null;
    }

    /**
     * Processes a command
     * @param console console
     * @param command command to process
     */
    protected abstract void processCommand (Console console, Command command);
}
