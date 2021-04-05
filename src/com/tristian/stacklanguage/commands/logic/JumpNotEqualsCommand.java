package com.tristian.stacklanguage.commands.logic;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.label.Label;
import com.tristian.stacklanguage.register.Comparator;

public class JumpNotEqualsCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.JumpNotEquals;
    }

    @Override
    public Object run(String[] args) throws Exception {

        if (Comparator.values.length == 0 || Comparator.values[0] == null || Comparator.values[1] == null) {
            System.out.println("nothing to compare. Quit."); // passive aggressive 'screw you you did something wrong'
        }
        ;
        String label = args[0]; // should be JNE labelName.
        if (Label.fromName(label) == null)
            System.out.println("Label " + label + " does not exist, quit.");
        if (!(Comparator.values[0] == Comparator.values[1] || Comparator.values[0].equals(Comparator.values[1]))) {
            System.out.println("not equal");
            Label.fromName(label).run(); // WOO!
            return null;
        }


        return null;
    }
}