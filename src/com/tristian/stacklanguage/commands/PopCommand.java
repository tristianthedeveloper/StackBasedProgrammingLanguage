package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;

public class PopCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.POP;
    }

    /**
     * Remove the last element from the register.
     * OPTIONA: pop (REGISTER / VARIABLE, pops the value from this stack onto  a variable / register)
     *
     * @return nothin
     */
    @Override
    public Object run(String[] args) {

        Object obj = Main.getInstance().getLStack().pop();

        return null;
    }
}
