
package org.neogroup.sparks.console.processors;

import org.neogroup.sparks.console.commands.ConsoleCommand;
import org.neogroup.sparks.processors.ProcessorComponent;
import org.neogroup.sparks.processors.SelectorProcessor;

import java.util.HashMap;
import java.util.Map;

@ProcessorComponent(commands = {ConsoleCommand.class})
public class ConsoleSelectorProcessor extends SelectorProcessor<ConsoleCommand, ConsoleProcessor> {

    private final Map<String, Class<? extends ConsoleProcessor>> processorsByCommandName;

    public ConsoleSelectorProcessor() {
        this.processorsByCommandName = new HashMap<>();
    }

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

    @Override
    protected Class<? extends ConsoleProcessor> getProcessorClass(ConsoleCommand command) {
        return processorsByCommandName.get(command.getCommand().getName());
    }
}
