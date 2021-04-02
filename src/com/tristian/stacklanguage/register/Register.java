package com.tristian.stacklanguage.register;

import com.sun.istack.internal.FinalArrayList;
import com.tristian.stacklanguage.LStack;
import kotlin.UByte;
import sun.awt.util.IdentityArrayList;

import java.util.List;

/**
 * Holds a set amount of bytes.
 */
public abstract class Register {

    private static List<Register> registers;


    /**
     * This register's identifier, prefixed by %
     */
    private String name;
    /***
     * This register's values.
     * you get one Object, honestly this should be a byte array,
     * might do that layer.
     */
    private Object stack;

    Register(String name) {
        this.name = name;
        this.stack = 0;
        registers.add(this);
    }


    /**
     * @param name The name of the register to try to find.
     * @return The register of name 'name'
     */
    public static Register fromName(String name) {
        return registers.stream().filter(e -> e.name.replaceAll("%", "").equals(name.replaceAll("%", ""))).findFirst().orElse(null);
    }

    /**
     * @return The value of this register.
     */
    public Object getStack() {
        try {
            return Integer.parseInt("" + stack);
        } catch (Exception ex) {
            return  stack;
        }
    }

        static {
            registers = new IdentityArrayList<>();
        }


        public void push (Object value){
            this.stack = value;
        }

    public String getName() {
        return this.name;
    }
}
