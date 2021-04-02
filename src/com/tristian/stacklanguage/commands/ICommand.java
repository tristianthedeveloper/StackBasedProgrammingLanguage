package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.var.Variable;

public interface ICommand {

    CommandParser.Commands getCommandIdentifier();


    Object run(String[] args) throws Exception;

    default void replaceVariableNames(String[] args) {
        if (args == null)
            return;
        if (args.length < 1)
            return;
        String toSplit;
        int i;
        for (i = 0; i < args.length;
             i++) {
            try {
                toSplit = args[i];
                if (!(toSplit.matches(".+%.+%.+")))
                    continue;
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

    /**
     * @param s The string to parse
     * @return The parsed integer, or NULL if failed.
     */
    default Integer tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
