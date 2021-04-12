package com.tristian.stacklanguage.var;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// work in progress
public class IntArray<T> extends Variable.MemoryEntry<List<Integer>> {


    public ArrayList<Integer> value = new ArrayList<>(Collections.singletonList(0)); // lets start with 0

    /**
     * @param name  The name of this variable.
     * @param value typeof String, will be dynamically converted with the type.
     * @param type  The type of this variable.
     */
    public IntArray(String name, List<Integer> value, Variable.DataType type) {
        super(name, value, type);
    }

    public List<Integer> getContents() {
        if (value.get(0) != 0)
            value.add(0, 0);
        return value;
    }

}
