package com.tristian.stacklanguage.commands.math;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.register.Register;
import com.tristian.stacklanguage.var.Variable;

public class LeftShiftCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.SHL;

    }

    @Override
    public Object run(String[] args) throws Exception {

        String[] split = String.join(" ", args).split(",");

        String firstArgument = split[0];
        String secondArgument = split[1];

        Register registerOne = Register.fromName(firstArgument);
        Register registerTwo = Register.fromName(secondArgument); // sloppity slop slop :kek

        Variable.MemoryEntry varOne = Variable.getEntryByName(firstArgument.replaceAll("[\\[\\]]", ""));



        int shiftBy = 0;

        // TODO CLEAN UP CLEAN UP EVERYBODY CLEAN UP THIS CODE SUCKS AND IS AN EMBARASSMENT TO HUMAN KIND!

        if (registerTwo == null) {
            try {
                shiftBy = Integer.parseInt(secondArgument); // assume lmao
            } catch (Exception e) {
            }
            ; // let the variable handle it.
        }

        if (registerOne == null || registerTwo == null) {
            // TODO make it so they can be interchangable
            Variable.MemoryEntry varTwo = Variable.getEntryByName(secondArgument.replaceAll("[\\[\\]]", ""));
            if (varOne == null && varTwo == null) {
                return null;
            } else if (varTwo == null) {
                // we good.
                try {
                    shiftBy = Integer.parseInt(secondArgument);
                    varOne.value = varOne.valueAsInt() << shiftBy; // yay!
                    return varOne.valueAsInt();
                } catch (Exception e) {
                }
                ;
            } else {
                assert varOne != null;
                varOne.value = varOne.valueAsInt() << varTwo.valueAsInt();
                return varOne.valueAsInt();
            }
        }
        if (registerOne == null && registerTwo == null) {
            return null;
        }
        if (registerTwo != null) {
            assert registerOne != null;
            registerOne.push(Integer.parseInt("" + registerOne.getStack()) << Integer.parseInt("" + registerTwo.getStack()));
            return Integer.parseInt("" + registerOne.getStack());
        }
        registerOne.push(Integer.parseInt("" + registerOne.getStack()) << shiftBy);
        return Integer.parseInt("" + registerOne.getStack());
    }
}
