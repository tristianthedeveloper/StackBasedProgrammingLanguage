package com.tristian.stacklanguage.commands.section;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.commands.ICommand;
import com.tristian.stacklanguage.section.SectionStorage;

public class CallSectionCommand implements ICommand {
    @Override
    public CommandParser.Commands getCommandIdentifier() {
        return CommandParser.Commands.CALL;
    }

    @Override
    public Object run(String[] args) throws Exception {
        if (args == null)
            return null;
        if (args.length > 2) {
            // call section name
            if (args[1].equals("section")) {
                String sectionName = args[2];
                callSection(sectionName);
                return null;
            }
        }

        return null;
    }

    public void callSection(String sectionName) {
        // not my problem if something goes wrong.
        SectionStorage.getSectionByName(sectionName).execute();
    }
}
