package com.tristian.stacklanguage.register;

import com.tristian.stacklanguage.var.Variable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtraAccumulator extends Register {

    private List<Integer> stack = new LinkedList<>();

    public ExtraAccumulator() {
        super("%eax");
    }

    @Override
    public void push(Object value) {
        // better be an int, dickhead
        if (Variable.getEntryByName("" + value) != null) {
            this.stack.add(Variable.getEntryByName("" + value).valueAsInt());
            return;
        }
        String stringed = "" + value;
        if (stringed.contains(","))
        {
            this.stack.addAll(Arrays.stream(stringed.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
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
