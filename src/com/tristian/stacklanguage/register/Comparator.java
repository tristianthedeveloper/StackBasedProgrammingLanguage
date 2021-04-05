package com.tristian.stacklanguage.register;

/**
 * This is a register, but it's not a regular one, it's a comparison register.
 */
public class Comparator{

    /**
     * The storage of this register.
     */
    public static Object[] values = new Object[2];


    /**
     *
     * @param registerOne A register or a variable or just anything comparible
     * @param registerTwo A register or a variable or just anything comparible.
     */
    public static void push(Object registerOne, Object registerTwo) {
        values[0] = registerOne;
        values[1] = registerTwo;
    }


}
