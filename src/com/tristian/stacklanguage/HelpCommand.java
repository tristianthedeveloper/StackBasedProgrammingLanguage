package com.tristian.stacklanguage;

public class HelpCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.HELP; // it doesn't deserve one.
    }

    @Override
    public void run(String[] args) throws Exception {
        new PrintCommand().run(new String[] { "Good luck." });
    }
}
