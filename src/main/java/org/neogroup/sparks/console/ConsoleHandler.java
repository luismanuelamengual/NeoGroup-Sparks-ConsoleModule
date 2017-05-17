
package org.neogroup.sparks.console;

/**
 * Handler for the console
 */
public abstract class ConsoleHandler implements Runnable {

    private final Console console;
    private String prompt;
    private boolean running;

    /**
     * Constructor for the handler
     * @param console console
     */
    public ConsoleHandler(Console console) {
        this.console = console;
        this.prompt = "$";
        this.running = false;
    }

    /**
     * Get the prompt for the console
     * @return string
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Set the prompt for the console
     * @param prompt prompt
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Get the console assigned to this handler
     * @return console
     */
    public Console getConsole() {
        return console;
    }

    /**
     * Closes the handler
     */
    public void close () {
        running = false;
        console.close();
    }

    /**
     * Checks if the handler is running or not
     * @return boolean
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Starts the console handler
     */
    @Override
    public void run() {
        running = true;
        while (running && !console.isClosed()) {
            console.write(prompt);
            console.write(" ");
            console.flush();
            String command = console.readLine();
            try { onCommandEntered (console, new Command(command)); } catch (Exception ex) {}
        }
        running = false;
    }

    /**
     * Method that is executed each time a command is entered
     * @param console console
     * @param command command entered
     */
    protected abstract void onCommandEntered (Console console, Command command);
}
