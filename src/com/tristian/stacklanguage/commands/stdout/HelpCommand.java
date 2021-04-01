package com.tristian.stacklanguage.commands.stdout;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;

public class HelpCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.HELP; // it doesn't deserve one.
    }

    @Override
    public Object run(String[] args) {
        new PrintCommand().run(new String[] { "Good luck." });
        return null;
    }
}
