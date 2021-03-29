package com.tristian.stacklanguage.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LoopCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.LOOP;
    }

    /**
     * Command triggered by La,n %commands to execute%, where a is the start number, n is the max.
     * a is optional, and can be omitted.
     * %n can be used as a variable placeholder for the number on the loop, currently the only steps are +1,
     * EXAMPLE: L5 mov %n
     */
    @Override
    public void run(String[] args) {


        int start = Integer.parseInt(args[0].replaceAll("[aA-Zz]", "").split(",")[0]);
        int amountOfTimes;
        try {
            amountOfTimes = Integer.parseInt(args[0].replaceAll("[aA-Zz]", "").split(",")[1]);
        } catch (Exception e) {
            amountOfTimes = start;
            start = 0;
        }


        String fixed = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));

        for (int i = start; i <= amountOfTimes; i++) {
            CommandParser.runCommand(fixed.replace("%n", "" + i));
        }
    }
}
