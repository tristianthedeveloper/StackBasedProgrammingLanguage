package com.tristian.stacklanguage.commands.stdout;

import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;

/**
 * Pops off and prints all elements of the stack.
 * USAGE: lp
 */
public class PrintAllCommand implements ICommand {

    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.PRINT_ALL;
    }

    @Override
    public Object run(String[] args) {

        while (!Main.getInstance().getLStack().isEmpty()) {
            new PrintCommand().run(null); // repeatedly pop and print from the stack until it's empty.
        }

        return null;
    }
}
