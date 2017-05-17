
package org.neogroup.sparks.console;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.Module;
import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.console.processors.ConsoleSelectorProcessor;
import org.neogroup.sparks.processors.ProcessorNotFoundException;

/**
 * Module for the console
 */
public class ConsoleModule extends Module {

    private final LocalConsoleHandler consoleHandler;

    /**
     * Constructor for the console module
     * @param application application
     */
    public ConsoleModule(Application application) {
        super(application);
        consoleHandler = new LocalConsoleHandler();
        registerProcessor(ConsoleSelectorProcessor.class);
    }

    /**
     * Starts the console module
     */
    @Override
    protected void onStart() {
        if (!consoleHandler.isRunning()) {
            Thread consoleThread = new Thread(consoleHandler);
            consoleThread.start();
        }
    }

    /**
     * Stops the console module
     */
    @Override
    protected void onStop() {
        if (consoleHandler.isRunning()) {
            consoleHandler.close();
        }
    }

    /**
     * Method executed when a command was not found
     * @param console console
     * @param command command entered
     */
    protected void onCommandNotFound (Console console, Command command) {
        console.println("Command \"" + command + "\" not found !!");
    }

    /**
     * Method executed when a command throws an exception
     * @param console console
     * @param command command entered
     * @param throwable exception thrown
     */
    protected void onCommandError (Console console, Command command, Throwable throwable) {
        console.println("Error: " + throwable.getMessage());
    }

    /**
     * Local console handler
     */
    public class LocalConsoleHandler extends ConsoleHandler {

        public LocalConsoleHandler() {
            super(new Console(System.in, System.out));
        }

        @Override
        protected void onCommandEntered(Console console, Command command) {

            ConsoleCommand consoleCommand = new ConsoleCommand(console, command);
            try {
                processCommand(consoleCommand);
            }
            catch (ProcessorNotFoundException exception) {
                onCommandNotFound(console, command);
            }
            catch (Throwable throwable) {
                onCommandError(console, command, throwable);
            }
        }
    }
}
