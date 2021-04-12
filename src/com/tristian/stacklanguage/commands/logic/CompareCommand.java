package com.tristian.stacklanguage.commands.logic;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.register.Comparator;


/**
 * Pushes the values to the actual Comparator "register"
 *  TODO make all comparison commands implementations of something else.
 * @see Comparator
 */
public class CompareCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.COMPARE;
    }

    /**
     * @param args Argumments, i.e cmp ax,3
     * @return null
     */
    @Override
    public Object run(String[] args) {
        replaceVariableNames(args);
        replaceRegisterValues(args);

        String[] split = String.join(" ", args).split(",");
        replaceVariableNames(split);

        Comparator.push(split[0], split[1]);
        return null;
    }
}
