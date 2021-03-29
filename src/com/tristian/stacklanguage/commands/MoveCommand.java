package com.tristian.stacklanguage.commands;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.tristian.stacklanguage.Main;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MoveCommand implements ICommand {

    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.MOV;
    }


    @Override
    public void run(String[] args) {

        if (args.length < 1) {
            System.out.println(new String[]{"Args must have 1 input, a hexa decimal"});
            return;
        }
        String fixedArg = args[0].replace("0x", "");
        Object parsed;
        try {
            parsed = Integer.parseInt(fixedArg);
        }catch(Exception e) {
            try {
                parsed = Integer.parseInt(fixedArg, 16);
            }catch(Exception $ex) {
                parsed = Arrays.stream(args).collect(Collectors.joining(" "));
            }
        }
        Main.getInstance().getLStack().push(parsed);
    }
}
