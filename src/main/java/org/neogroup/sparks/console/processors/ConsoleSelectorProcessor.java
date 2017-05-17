
package org.neogroup.sparks.console.processors;

import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.SelectorProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Processor to choose between all console processors to execute a command
 */
@ProcessorComponent(commands = {ConsoleCommand.class})
public class ConsoleSelectorProcessor extends SelectorProcessor<ConsoleCommand, ConsoleProcessor> {

    private final Map<String, Class<? extends ConsoleProcessor>> processorsByCommandName;

    /**
     * Constructor of the console processor selector
     */
    public ConsoleSelectorProcessor() {
        this.processorsByCommandName = new HashMap<>();
    }

    /**
     * Register a console processor class
     * @param processorClass processor class
     * @return boolean
     */
    @Override
    protected boolean registerProcessorClass(Class<? extends ConsoleProcessor> processorClass) {
        boolean registered = false;
        ProcessCommands processCommands = processorClass.getAnnotation(ProcessCommands.class);
        if (processCommands != null) {
            String[] commands = processCommands.value();
            for (String command : commands) {
                processorsByCommandName.put(command, processorClass);
            }
            registered = true;
        }
        return registered;
    }

    /**
     * Get a console processor class from a command
     * @param command command entered
     * @return console processor class
     */
    @Override
    protected Class<? extends ConsoleProcessor> getProcessorClass(ConsoleCommand command) {
        return processorsByCommandName.get(command.getCommand().getName());
    }
}
