package com.tristian.stacklanguage.commands;

import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.register.Register;
import com.tristian.stacklanguage.var.IntArray;
import com.tristian.stacklanguage.var.Variable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class PopCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.POP;
    }

    /**
     * Remove the last element from the register.
     * OPTIONA: pop (REGISTER / VARIABLE, pops the value from this stack onto  a variable / register)
     *
     * @return nothin
     */
    @Override
    public Object run(String[] args) {

        System.out.println(Arrays.toString(args));
        Register register = getRegisterFromArgsOrNull(args);

        if (register != null) {
            if (register.getStack() instanceof List || register.getStack() instanceof Array)
                return register.pop() != null ? register.pop() : 0;

        }
        Variable.MemoryEntry var = getVarsFromArgs(args).get(0);

        if (var instanceof IntArray) {
            return ((List<Integer>) var.value).remove((((List<Integer>) var.value).size() - 1));
        }
        Object obj = Main.getInstance().getLStack().pop();

        return null;
    }
}
