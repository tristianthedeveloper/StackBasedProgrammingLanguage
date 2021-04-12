package com.tristian.stacklanguage.commands.math;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.var.Variable;

/**
 * add command, usage: add var,var2, or, add num,num2 will add both numbers together, will make add %register,%register once i make registers
 */
public class AdditionCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.ADD;
    }

    @SuppressWarnings("raw")
    @Override
    public Object run(String[] args) {
        float sum;

        String fixedArgs = String.join("", args);
        String[] split = fixedArgs.split(",");
        Integer one = tryParseInt(split[0].replaceAll(" ", "")),
                two = tryParseInt(split[1].replaceAll(" ", ""));
        if (one != null && two != null)
            return one + two; // lol pun

        String arg1 = split[0].replaceAll(" ", ""),
                arg2 = split[1].replaceAll(" ", "");

        Variable.MemoryEntry parsedArg1 = Variable.getEntryByName(arg1);
        Variable.MemoryEntry parsedArg2 = Variable.getEntryByName(arg2);

        if (parsedArg1 == null) {
            System.out.println("Invalid argument, variable " + arg1 + " cannot be NULL");
            return null;
        }
        if (parsedArg2 == null) {
            System.out.println("Invalid argument, variable " + arg2 + " cannot be NULL.");
            return null;
        }
        if (parsedArg1.type == Variable.DataType.FLOAT || parsedArg2.type == Variable.DataType.FLOAT) {
            return parsedArg1.valueAsFloat() + parsedArg2.valueAsFloat();
        }
        sum = parsedArg1.valueAsInt() + parsedArg2.valueAsInt();
        return sum;
    }

}
