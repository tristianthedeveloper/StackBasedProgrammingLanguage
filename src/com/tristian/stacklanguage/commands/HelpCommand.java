package com.tristian.stacklanguage.commands;

public class HelpCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.HELP; // it doesn't deserve one.
    }

    @Override
    public void run(String[] args) {
        new PrintCommand().run(new String[] { "Good luck." });
    }
}
