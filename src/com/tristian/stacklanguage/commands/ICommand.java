package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.var.Variable;

public interface ICommand {

    CommandParser.Commands getCommandIdentifier();


    void run(String[] args) throws Exception;

    default void replaceVariableNames(String[] args) {
        if (args.length < 1)
            return;
        String toSplit;
        int i;
        for (i = 0, toSplit = args[i]; i < args.length;
             i++) {
            try {
                String[] split = toSplit.split("%"); // split the string at the expected variable start
                String varName = split[1].replaceAll("%", ""); // get the variable name in between %
                Variable.MemoryEntry<?> memoryEntry;
                // check if a memory entry exists, and if it does, assign it to memoryEntry, and replace the variable name inside of "toSplit" with the value.
                if ((memoryEntry = Variable.getEntryByName(varName)) != null) {
                    toSplit = toSplit.replace("%" + varName + "%", "" + memoryEntry.value);
                    args[i] = toSplit.trim();
                }
            } catch (Exception e) {
                // we're FinE
            }
        }
    }
}
