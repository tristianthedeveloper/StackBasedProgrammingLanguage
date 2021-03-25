package com.tristian.stacklanguage;

import java.util.Arrays;
import java.util.regex.Pattern;

public class CommandParser {


    public static void runCommand(String input) {


        String[] args = input.split(" ");


        Commands command;
        if ((command = Commands.valueOfThing(args[0])) != null) {
            try {
                // screw it!
                if (args.length > 0 && !command.special) {
                    command.commandClass.newInstance().run(Arrays.copyOfRange(args, 1, args.length));
                    return;
                } else if (args.length > 0 && command.special) {
                    command.commandClass.newInstance().run(args);
                    return;
                }
                command.commandClass.newInstance().run(Arrays.copyOfRange(args, 0, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    enum Commands {

        MOV("mov", MoveCommand.class),
        POP("pop", PopCommand.class),
        PRINT("+", PrintCommand.class),
        PRINT_ALL("lp", PrintAllCommand.class),
//        i fricking hate regular expressions
        LOOP("L[\\-\\d*|\\d*\\(,\\d*)|]+", LoopCommand.class, true),
        XOR("xor", ExclusiveOrCommand.class),
        HELP("help", HelpCommand.class);

        String identifier;
        Class<? extends ICommand> commandClass;
        boolean special;

        /**
         * @param identifier   Can use regex
         * @param commandClass
         */
        Commands(String identifier, Class<? extends ICommand> commandClass, boolean special) {
            this.identifier = identifier;
            this.commandClass = commandClass;
            this.special = special;
            // special basically means the full entry will be included into the args, unlike the others when it's just the actual arguments

        }

        /**
         * @param identifier   Can use regex
         * @param commandClass
         */
        Commands(String identifier, Class<? extends ICommand> commandClass) {
            this.identifier = identifier;
            this.commandClass = commandClass;
        }


        public static Commands valueOfThing(String test) {

            for (Commands cmd : values()) {
                if (!cmd.identifier.equalsIgnoreCase(test)) {
                    try {
                        if (Pattern.compile(cmd.identifier).matcher(test).matches()) {
                            return cmd;
                        }
                    } catch (Exception ignored) {};
                }
                if (cmd.identifier.equalsIgnoreCase(test)) {
                    return cmd;
                }
            }
            return null;
        }

    }

}
