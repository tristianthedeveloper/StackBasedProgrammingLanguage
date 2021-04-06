
package com.tristian.stacklanguage.commands.math;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.register.Register;
import com.tristian.stacklanguage.var.Variable;

import java.util.List;

/**
 * Subtracts given register / variable by 1
 */
public class SubCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.SUB;
    }

    @Override
    public Object run(String[] args) throws Exception {
        Register register;
        if ((register = getRegisterFromArgsOrNull(args)) != null) {
            register.push((int) register.getStack() - 1); // dont care stay mad haha
            return register.getStack();
        } else {
            if (getVarsFromArgs(args).isEmpty()) {
                return null;
            }
            List<Variable.MemoryEntry<?>> entries = getVarsFromArgs(args);
            if (entries.get(0).type == Variable.DataType.FLOAT) // this is ridiculous, clean this crap up.
                entries.get(0).value = (entries.get(0).valueAsFloat()) - 1;
            else entries.get(0).value = (entries.get(0).valueAsInt()) - 1;

        }

        return null;
    }
}