package com.tristian.stacklanguage.register;

import com.sun.istack.internal.FinalArrayList;
import com.tristian.stacklanguage.LStack;
import sun.awt.util.IdentityArrayList;

import java.util.List;

public abstract class Register {

    private static List<Register> registers;


    /**
     * This register's identifier, prefixed by %
     */
    private String name;
    /***
     * This register's stack.
     */
    private LStack stack;

    Register(String name) {
        this.name = name;
        this.stack = LStack.setUpStack();
        registers.add(this);
        System.out.println("registered register named " + name);
    }


    /**
     * @param name The name of the register to try to find.
     * @return The register of name 'name'
     */
    public static Register fromName(String name) {
        return registers.stream().filter(e -> e.name.replaceAll("%", "").equals(name)).findFirst().orElse(null);
    }

    public LStack getStack() {
        return this.stack;
    }

    static {
        registers = new IdentityArrayList<>();
    }


}
