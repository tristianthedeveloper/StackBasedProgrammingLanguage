package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MoveCommand implements ICommand {

    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.MOV;
    }


    /**
     *
     * @param args What to push to the stack,
     * @return
     */
    @Override
    public Object run(String[] args) {

        if (args.length < 1) {
            System.out.println(new String[]{"Args must have 1 input, a hexa decimal"});
            return null;
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
        return null;
    }
}
