package com.tristian.stacklanguage;

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
     * @throws Exception
     */
    @Override
    public void run(String[] args) throws Exception {

        String fixedArgs = Arrays.stream(args).collect(Collectors.joining());
//        sure go ahead have as many spaces as you fricking want i dont care

        String[] splitAtCommas = fixedArgs.split(",");

        int index1 = Integer.parseInt(splitAtCommas[0]);
        int index2 = Integer.parseInt(splitAtCommas[1].trim());
        Main.getInstance().getStack().xor(index1, index2);
    }
}
