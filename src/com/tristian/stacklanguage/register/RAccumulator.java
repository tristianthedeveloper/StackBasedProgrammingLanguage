package com.tristian.stacklanguage.register;

import com.tristian.stacklanguage.var.Variable;

import java.util.LinkedList;
import java.util.List;

public class RAccumulator extends Register {

    private List<Integer> stack = new LinkedList<>();

    public RAccumulator() {
        super("%rax");
    }

    @Override
    public void push(Object value) {
        // better be an int, dickhead
        if (Variable.getEntryByName("" + value) != null) {
            this.stack.add(Variable.getEntryByName("" + value).valueAsInt());
            return;
        }
        this.stack.add(Integer.parseInt(("" + value).replaceAll(" ", "")));
    }

    @Override
    public List<Integer> getStack() {
        return stack;
    }

    @Override
    public Object pop() {
        return this.stack.remove(this.stack.size() - 1);
    }
}
