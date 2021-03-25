package com.tristian.stacklanguage;

public interface ICommand {

    CommandParser.Commands getCommandIdentifier();


    void run(String[] args) throws Exception;

}
