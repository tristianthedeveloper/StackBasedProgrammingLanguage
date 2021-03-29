package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ExclusiveOrCommand implements ICommand {

    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.XOR;
    }

    /**
     * USAGE: xor index1,index2
     * XoR's the bits of index1 by index 2
     *
     */
    @Override
    public void run(String[] args) {

        String fixedArgs = Arrays.stream(args).collect(Collectors.joining());
//        sure go ahead have as many spaces as you fricking want i dont care

        String[] splitAtCommas = fixedArgs.split(",");

        int index1 = Integer.parseInt(splitAtCommas[0]);
        int index2 = Integer.parseInt(splitAtCommas[1].trim());
        Main.getInstance().getLStack().xor(index1, index2);
    }
}
