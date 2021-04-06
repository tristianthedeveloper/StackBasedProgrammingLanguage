package com.tristian.stacklanguage.var;

import java.util.List;

// work in progress
public class IntArray<T> extends Variable.MemoryEntry<List<Integer>> {


    private List<Integer> value;

    /**
     * @param name  The name of this variable.
     * @param value typeof String, will be dynamically converted with the type.
     * @param type  The type of this variable.
     */
    public IntArray(String name, List<Integer> value, Variable.DataType type) {
        super(name, value, type);
    }

    public List<Integer> getContents() {
        return value;
    }

}
