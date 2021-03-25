package com.tristian.stacklanguage;

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
    public void run(String[] args) throws Exception {

        while (!Main.getInstance().getStack().isEmpty()) {
            new PrintCommand().run(null);
        }


    }
}
