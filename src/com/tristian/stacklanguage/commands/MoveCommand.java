package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.register.Register;
import com.tristian.stacklanguage.var.Variable;

import java.util.Arrays;
import java.util.Objects;
// TODO MAKE ESP COMMAND (stack pointer)
public class MoveCommand implements ICommand {

    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.MOV;
    }


    /**
     * @param args What to push to the stack,
     */
    @Override
    public Object run(String[] args) {

        if (tryPushRegister(args)) return null;

        if (args.length < 1) {
            System.out.println("invalid arg length");
            return null;
        }
        // lol clean me up daddy
        String fixedArg = args[0].replace("0x", "");
        Object parsed;
        try {
            parsed = Integer.parseInt(fixedArg);
        } catch (Exception e) {
            try {
                parsed = Integer.parseInt(fixedArg, 16);
            } catch (Exception $ex) {
                parsed = String.join(" ", args);
            }
        }
        Main.getInstance().getLStack().push(parsed);
        return null;
    }

    private boolean tryPushRegister(String[] args) {

        if (args == null)
            return false;

        replaceVariableNames(args);
        String[] fixedArgs = args.clone();
        replaceVariableNames(fixedArgs);
        String joined = String.join(" ", args); // turn it back into spaced arguments.
        String[] splitAtCommas = joined.split(","); // split at commas
        replaceVariableNames(splitAtCommas);
        if (splitAtCommas.length < 2)
            return false; // assume no register
        String register = splitAtCommas[0];
        replaceVariableNames(splitAtCommas); // replace all the variable names that might be hidden inside of it.
        // for strings
        String value = String.join(" ", Arrays.copyOfRange(splitAtCommas, 1, splitAtCommas.length));
        if (Register.fromName(register.replaceAll(" ", "")) == null) {
            System.out.println("no register kekega");
            return false;
        }
        value = replaceVarNameInString(value.replaceAll(" ", ""));
        if (Variable.getEntryByName(value) != null)
            value = "" + Objects.requireNonNull(Variable.getEntryByName(value)).value;
        // the registers value is now value POGGERS
        Register.fromName(register).push(value); // todo make this flat out replace it, and have push make space.
        return true;
    }
}
