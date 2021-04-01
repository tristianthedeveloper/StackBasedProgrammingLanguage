package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.var.Variable;

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
     */
    @Override
    public void run(String[] args) {


        String fixedArgs = Arrays.stream(args).collect(Collectors.joining(""));

//        sure go ahead have as many spaces as you fricking want i dont care


        String[] splitAtCommas = fixedArgs.split(",");
        // xor %var1%,%var2%
        String once = splitAtCommas[0];
        String twice = splitAtCommas[1];
        Variable.MemoryEntry<?> var_one = Variable.getEntryByName(once.replaceAll("%", ""));
        Variable.MemoryEntry<?> var_two = Variable.getEntryByName(twice.replaceAll("%", ""));
        if ((var_one == null || var_two == null)) {
            System.out.println("Invalid xor execution: variable " + (var_one == null ? once : twice) + " cannot be NULL");
        } else {
            if (var_one.type != Variable.DataType.INT || var_two.type != Variable.DataType.INT) {
                System.out.println("Invalid xor execution: both types must be of type INT");
            }
            int val = (int) var_one.value;
            // why, java?
            var_one.value = val ^ (int) var_two.value;
            return;
        }
        int index1 = Integer.parseInt(once);
        int index2 = Integer.parseInt(twice);


        Main.getInstance().
                getLStack().
                xor(index1, index2);
    }
}
