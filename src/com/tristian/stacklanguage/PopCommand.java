package com.tristian.stacklanguage;

public class PopCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
       return CommandParser.Commands.POP;
    }

    /**
     * Remove the last element from the register.
     */
    @Override
    public void run(String[] args) throws Exception {

        if (args.length != 0) {
         System.out.println("args more, returning");
            return;
        }
        Main.getInstance().getStack().pop();
    }
}
