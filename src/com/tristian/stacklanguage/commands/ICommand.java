package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.register.Register;
import com.tristian.stacklanguage.var.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                if (Register.fromName(toSplit) != null)
                    continue;
                String[] split = toSplit.split("\\["); // split the string at the expected variable start
                String varName = split[1].replaceFirst("[\\[\\]]", "").replaceAll(" ", ""); // get the variable name in between %
                Variable.MemoryEntry<?> memoryEntry;
                // check if a memory entry exists, and if it does, assign it to memoryEntry, and replace the variable name inside of "toSplit" with the value.
                if ((memoryEntry = Variable.getEntryByName(varName)) != null) {
                    // frick arrays
                    toSplit = toSplit.replace("[" + varName + "]", "" + memoryEntry.value);
                    args[i] = toSplit.trim();
                }

            } catch (Exception e) {
                // we're FinE
            }
        }
    }

    default Register getRegisterFromArgsOrNull(String[] args) {

        if (args == null)
            return null;

        for (String s : args) {
            if (Register.fromName(s.replaceAll(" ", "")) != null) {
                return Register.fromName(s.replaceAll(" ", "")); // check it straight up
            }
        }

        String joined = String.join(" ", args);
        String[] split = joined.split(",");
        for (String s : split) { // check from a comma split string
            if (Register.fromName(s.replaceAll(" ", "")) != null)
                return Register.fromName(s.replaceAll(" ", ""));
        }

        return null;

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

    default List<Variable.MemoryEntry<?>> getVarsFromArgs(String[] args) {
        if (args == null)
            return null;
        if (args.length < 1)
            return null;
        List<Variable.MemoryEntry<?>> entries = new ArrayList<>();
        String toSplit;
        int i;
        for (i = 0; i < args.length;
             i++) {
            try {
                if ((Variable.getEntryByName(args[i])) != null) {
                    entries.add(Variable.getEntryByName(args[i]));
                }
                toSplit = args[i];
                String[] split = toSplit.split("\\["); // split the string at the expected variable start
                String varName = split[1].replaceAll("[\\[\\]]", ""); // get the variable name in between %
                Variable.MemoryEntry<?> memoryEntry;
                // check if a memory entry exists, and if it does, assign it to memoryEntry, and replace the variable name inside of "toSplit" with the value.
                if ((memoryEntry = Variable.getEntryByName(varName)) != null) {
                    entries.add(memoryEntry);
                }
            } catch (Exception e) {
                continue;
                // we're FinE
            }
        }
        return entries;
    }

    default void replaceRegisterValues(String[] args) {
        if (args == null)
            return;
        if (getRegisterFromArgsOrNull(args) == null)
            return;
        for (int i = 0; i < args.length; i++) {
            try {
                args[i] = args[i].replace(getRegisterFromArgsOrNull(args).getName(), "" + getRegisterFromArgsOrNull(args).getStack());
            } catch (Exception e) {
                break;
            }
        }
    }

    // this works if its literally only the name of the variable inside the string lol what
    default String replaceVarNameInString(String string) {
        if (Variable.getEntryByName(string.replaceAll("[\\[\\]]", "")) == null)
            return string;
        return string.replaceAll(string, "" + Variable.getEntryByName(string.replaceAll("[\\[\\]]", "")).value);
    }
}
