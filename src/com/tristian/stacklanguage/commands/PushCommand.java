//package com.tristian.stacklanguage.commands;
//
//import com.tristian.stacklanguage.Main;
//import com.tristian.stacklanguage.register.Register;
//import com.tristian.stacklanguage.var.Variable;
//
//import java.util.Arrays;
//import java.util.stream.Collectors;
//
//// TODO MAKE ESP COMMAND BEFORE YOU USE ME! (stack pointer)
//public class PushCommand implements ICommand {
//
//    @Override
//    public CommandParser.Commands getCommandIdentifier() {
//        return CommandParser.Commands.PUSH;
//    }
//
//
//    /**
//     * @param args What to push to the stack,
//     * @return
//     */
//    @Override
//    public Object run(String[] args) {
//
//        if (tryPushRegister(args)) return null;
//
//        if (args.length < 1) {
//            System.out.println("invalid arg length");
//            return null;
//        }
//        // lol clean me up daddy
//        String fixedArg = args[0].replace("0x", "");
//        Object parsed;
//        try {
//            parsed = Integer.parseInt(fixedArg);
//        } catch (Exception e) {
//            try {
//                parsed = Integer.parseInt(fixedArg, 16);
//            } catch (Exception $ex) {
//                parsed = Arrays.stream(args).collect(Collectors.joining(" "));
//            }
//        }
//        Main.getInstance().getLStack().push(parsed);
//        return null;
//    }
//
//    private boolean tryPushRegister(String[] args) {
//
//        if (args == null)
//            return false;
//
//        String[] fixedArgs = args.clone();
//        String joined = String.join(" ", args); // turn it back into spaced arguments.
//        String[] splitAtCommas = joined.split(","); // split at commas
//        if (splitAtCommas.length < 2)
//            return false; // assume no register
//        String register = splitAtCommas[0];
////        replaceVariableNames(splitAtCommas); // replace all the variable names that might be hidden inside of it.
//        // for strings
//        String value = String.join(" ", Arrays.copyOfRange(splitAtCommas, 1, splitAtCommas.length));
//        if (Register.fromName(register.replaceAll(" ", "")) == null) {
//            System.out.println("no register kekega");
//            return false;
//        }
//        value = replaceVarNameInString(value);
//        // the registers value is now value POGGERS
//        Register.fromName(register).push(value);
//        return true;
//    }
//}
