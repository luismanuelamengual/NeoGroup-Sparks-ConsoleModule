
package org.neogroup.sparks.console.commands;

import org.neogroup.sparks.commands.Command;
import org.neogroup.sparks.console.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Console command for the sparks framework
 */
public class ConsoleCommand extends Command {

    private static final String SHORT_PARAMETER_PREFIX = "-";
    private static final String LONG_PARAMETER_PREFIX = "--";

    private final Console console;
    private final String name;
    private final List<String> parameters;
    private final Map<String,String> namedParameters;

    /**
     * Constructor for the command
     * @param console console
     * @param commandName command entered
     */
    public ConsoleCommand(Console console, String commandName) {
        this.console = console;
        List<String> tokens = parseCommand(commandName);
        name = tokens.get(0);
        parameters = new ArrayList<>();
        namedParameters = new HashMap<>();

        String token = null;
        String parameterName = null;
        String parameterValue = null;
        for (int i = 1; i < tokens.size(); i++) {
            token = tokens.get(i);
            if (token.startsWith(LONG_PARAMETER_PREFIX)) {
                parameterName = token.substring(LONG_PARAMETER_PREFIX.length());
                if (!parameterName.isEmpty()) {
                    parameterValue = null;
                    if (tokens.size() != i + 1) {
                        String nextToken = tokens.get(i + 1);
                        if (!nextToken.startsWith(LONG_PARAMETER_PREFIX) && !nextToken.startsWith(SHORT_PARAMETER_PREFIX)) {
                            parameterValue = nextToken;
                            namedParameters.put(parameterName, parameterValue);
                        }
                    }
                }
            }
            else if (token.startsWith(SHORT_PARAMETER_PREFIX)) {
                parameterName = token.substring(1,2);
                if (!parameterName.isEmpty()) {
                    parameterValue = null;
                    if (token.length() > 2) {
                        parameterValue = token.substring(2);
                        parameters.add(parameterValue);
                        namedParameters.put(parameterName, parameterValue);
                    }
                    else if (tokens.size() != i + 1) {
                        String nextToken = tokens.get(i + 1);
                        if (!nextToken.startsWith(LONG_PARAMETER_PREFIX) && !nextToken.startsWith(SHORT_PARAMETER_PREFIX)) {
                            parameterValue = nextToken;
                            namedParameters.put(parameterName, parameterValue);
                        }
                    }
                }
            }
            else {
                parameters.add(token);
            }
        }
    }

    /**
     * Get the current console
     * @return console
     */
    public Console getConsole() {
        return console;
    }

    /**
     * Get the name of the command
     * @return string
     */
    public String getName() {
        return name;
    }

    /**
     * Get the parameters of the command
     * @return list of parameters
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Get the parameters by name
     * @return map of parameters
     */
    public Map<String,String> getNamedParameters() {
        return namedParameters;
    }

    /**
     * Get a parameter value
     * @param parameterName name of parameter
     * @return value of parameter
     */
    public String getParameter (String parameterName) {
        return namedParameters.get(parameterName);
    }

    /**
     * Indicates if a parameter exists
     * @param parameterName name of parameter
     * @return boolean
     */
    public boolean hasParameter (String parameterName) {
        return namedParameters.containsKey(parameterName);
    }

    /**
     * Parses a command
     * @param command command to be parsed
     * @return list of tokens
     */
    private static List<String> parseCommand (final String command) {

        List<String> tokens = new ArrayList<String>(0);
        try {
            int tokenStart = -1;
            boolean inJoinedToken = false;
            char joinChar = 0;
            for (int index = 0; index < command.length(); index++) {
                char commandChar = command.charAt(index);
                if (commandChar == '\\') {
                    index++;
                }
                else {
                    if (!inJoinedToken) {
                        if (Character.isWhitespace(commandChar)) {
                            if (tokenStart != -1) {
                                tokens.add(command.substring(tokenStart, index).trim());
                                tokenStart = -1;
                            }
                        }
                        else if (commandChar == '"' || commandChar == '\'') {
                            if (tokenStart != -1) {
                                tokens.add(command.substring(tokenStart, index).trim());
                            }
                            tokenStart = index+1;
                            joinChar = commandChar;
                            inJoinedToken = true;
                        }
                        else if (tokenStart == -1) {
                            tokenStart = index;
                        }
                    }
                    else if (commandChar == joinChar) {
                        tokens.add(command.substring(tokenStart, index));
                        inJoinedToken = false;
                        tokenStart = -1;
                    }
                }
            }
            if (tokenStart != -1 && !inJoinedToken) {
                tokens.add(command.substring(tokenStart).trim());
            }
        }
        catch (Exception exception) {}
        return tokens;
    }
}
