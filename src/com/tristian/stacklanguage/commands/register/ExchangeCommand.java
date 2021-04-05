package com.tristian.stacklanguage.commands.register;

import com.sun.beans.TypeResolver;
import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.register.Register;
import com.tristian.stacklanguage.var.Variable;

/**
 * Swaps the value of two registers / variables.
 */
public class ExchangeCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.XCHG;
    }

    @Override
    public Object run(String[] args) throws Exception {
        String joined = String.join(" ", args);
        String[] commaSplit = joined.split(",");
        String swap_one = commaSplit[0].trim();
        String swap_two = commaSplit[1].trim();
        if (Variable.getEntryByName(swap_one) != null) {
            if (Variable.getEntryByName(swap_two) == null) {
                System.out.println(swap_one + " is a variable, but " + swap_two + " is a null. Quit.");
                return null;
            }
            Variable.MemoryEntry mem1 = Variable.getEntryByName(swap_one);
            Variable.MemoryEntry mem2 = Variable.getEntryByName(swap_two);
            Object tempEntry = mem1.value;
            mem1.value = mem2.value;
            mem2.value = tempEntry;
        } else if (Register.fromName(swap_one) != null) {
            if (Register.fromName(swap_two) == null) {
                System.out.println(swap_two + " is a register, but " + swap_two + " is a null. Quit.");
                return null;
            }
            Register r1 = Register.fromName(swap_one);
            Register r2 = Register.fromName(swap_two);
            if (r1.getStack() == null || r2.getStack() == null) {
                System.out.println("null stack: " + r1.getName() + " register: " + r1.getStack() + ", " + r2.getName() + " register: " + r2.getStack()
                        + " Quit.");
                return null;
            }
            Object temp = r1.getStack();
            r1.push(r2.getStack());
            r2.push(temp);

        }
        System.out.println("invalid / null arguments, Quit.");

        return null;
    }
}
