package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.var.Variable;

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
    public void run(String[] args) {
        replaceVariableNames(args);
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
            // eh screw it it's just a string
            System.out.println(String.join(" ", args));
            return;
        }
        if (flag) {
            System.out.println((char) Main.getInstance().getLStack().removeAt(0));
            return;
        }
        System.out.println(Main.getInstance().getLStack().removeAt(0));
    }

}
