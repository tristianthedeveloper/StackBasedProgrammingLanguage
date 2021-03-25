package com.tristian.stacklanguage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PrintCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.PRINT;
    }

    /**
     * Remove the first element of the stack (if no argument is supplied) and prints its value.
     * OPTIONAL: argument 'c' will print it as a character value. (this will only print its char value of the value at this register is an integer)
     */
    @Override
    public void run(String[] args) throws Exception {
        boolean flag = args != null && args.length > 0 && args[0].equalsIgnoreCase("c");
        try {
            if (args != null && args.length > 0) {
                System.out.println(Integer.parseInt(args[0]));
                return;
            }
        } catch (Exception e) {

            if (flag) {
                // if its not the second variable, its not my fricking problem
                int value = Integer.parseInt(args[1]);
                System.out.println((char) value);
                return;
            }
            if (flag) {
                System.out.println((char)  Integer.parseInt(args[0]));
                return;
            }
            // eh screw it it's just a string
            System.out.println(Arrays.stream(args).skip(1).collect(Collectors.joining(" ")));
            return;
        }

        if (flag) {
            System.out.println((char) Main.getInstance().getStack().removeAt(0));
            return;
        }
        System.out.println(Main.getInstance().getStack().removeAt(0));
    }
}
