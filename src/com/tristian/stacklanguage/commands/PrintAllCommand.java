package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;

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
    public void run(String[] args) {

        while (!Main.getInstance().getLStack().isEmpty()) {
            new PrintCommand().run(null); // repeatedly pop and print from the stack until it's empty.
        }


    }
}
