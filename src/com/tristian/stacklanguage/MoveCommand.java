package com.tristian.stacklanguage;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MoveCommand implements ICommand {

    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.MOV;
    }


    @Override
    public void run(String[] args) throws InvalidArgumentException {

        if (args.length < 1)
            throw new InvalidArgumentException(new String[] { "Args must have 1 input, a hexa decimal" });

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
        Main.getInstance().getStack().push(parsed);
    }
}
