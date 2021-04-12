package com.tristian.stacklanguage.commands.logic;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.label.Label;

/**
 * "Jump to" or call a labael.
 */
public class JumpCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.JMP;
    }

    @Override
    public Object run(String[] args) {
        Label l = Label.fromName(args[0]); // not a label? frick yo
        l.run();
        return null;

    }
}
