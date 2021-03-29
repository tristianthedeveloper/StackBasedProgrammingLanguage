package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.commands.CommandParser;

public interface ICommand {

    CommandParser.Commands getCommandIdentifier();


    void run(String[] args) throws Exception;

}
