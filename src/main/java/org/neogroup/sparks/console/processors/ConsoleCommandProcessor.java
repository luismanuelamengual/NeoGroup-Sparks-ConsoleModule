
package org.neogroup.sparks.console.processors;

import org.neogroup.sparks.Application;
import org.neogroup.sparks.Module;
import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.processors.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Processor to choose between all console processors to execute a command
 */
@ProcessorCommands({ConsoleCommand.class})
public class ConsoleCommandProcessor extends CommandProcessor<ConsoleCommand> {

    private final Map<String, ConsoleEntry> consoleCache;

    /**
     * Constructor of the console processor selector
     */
    public ConsoleCommandProcessor() {
        this.consoleCache = new HashMap<>();
    }

    /**
     * Registers all crud processors
     */
    @Override
    public void start() {

        //Retrieve all processors visible from this module/application
        Set<Processor> registeredProcessors = new HashSet<>();
        registeredProcessors.addAll(getApplicationContext().getRegisteredProcessors());
        if (getApplicationContext() instanceof Module) {
            Module module = (Module)getApplicationContext();
            registeredProcessors.addAll(module.getApplication().getRegisteredProcessors());
        }
        else if (getApplicationContext() instanceof Application) {
            Application application = (Application)getApplicationContext();
            for (Module module : application.getModules()) {
                registeredProcessors.addAll(module.getRegisteredProcessors());
            }
        }

        //Register console processors
        for (Processor processor : registeredProcessors) {
            if (processor instanceof ConsoleProcessor) {
                ConsoleProcessor consoleProcessor = (ConsoleProcessor)processor;
                for (Method consoleMethod : consoleProcessor.getClass().getDeclaredMethods()) {
                    org.neogroup.sparks.console.ConsoleCommand consoleCommand = consoleMethod.getAnnotation(org.neogroup.sparks.console.ConsoleCommand.class);
                    for (String commandName : consoleCommand.value()) {
                        consoleCache.put(commandName, new ConsoleEntry(consoleProcessor, consoleMethod));
                    }
                }
            }
        }
    }

    /**
     * Processes a console command
     * @param command command to process
     * @return no response
     * @throws ProcessorException
     */
    @Override
    public Object process(ConsoleCommand command) throws ProcessorException {

        ConsoleEntry consoleEntry = consoleCache.get(command.getName());
        if (consoleEntry == null) {
            throw new ProcessorNotFoundException("Console processor not found for command \"" + command.getName() + "\"");
        }

        try {
            consoleEntry.getProcessorMethod().invoke(consoleEntry.getProcessor(), command.getConsole(), command);
        }
        catch (Throwable e) {
            throw new ProcessorException("Error processing command \"" + command.getName() + "\" !!");
        }
        return null;
    }

    /**
     * Console entry for console processor methods
     */
    private class ConsoleEntry {
        private final ConsoleProcessor processor;
        private final Method processorMethod;

        public ConsoleEntry(ConsoleProcessor processor, Method processorMethod) {
            this.processor = processor;
            this.processorMethod = processorMethod;
        }

        public ConsoleProcessor getProcessor() {
            return processor;
        }

        public Method getProcessorMethod() {
            return processorMethod;
        }
    }
}
