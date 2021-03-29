package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;

public class PopCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
       return CommandParser.Commands.POP;
    }

    /**
     * Remove the last element from the register.
     */
    @Override
    public void run(String[] args) {

        if (args.length != 0) {
         System.out.println("args more, returning");
            return;
        }
        Main.getInstance().getLStack().pop();
    }
}
