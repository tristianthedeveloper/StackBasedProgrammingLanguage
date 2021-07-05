package com.tristian.stacklanguage.register;

import com.tristian.stacklanguage.var.Variable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ExtraAccumulator extends Register {

    private Stack<Integer> stack = new Stack<>();

    public ExtraAccumulator() {
        super("%eax");
    }

    @Override
    public void push(Object value) {
        // better be an int, dickhead
        if (Variable.getEntryByName("" + value) != null) {
            this.stack.push(Variable.getEntryByName("" + value).valueAsInt());
            return;
        }
        String stringed = "" + value;
        if (stringed.contains(",")) {
            this.stack.addAll(Arrays.stream(stringed.replaceAll("[\\[\\]]", "").replaceAll(" ", "").split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
            return;
        }
        this.stack.push(Integer.parseInt(("" + value).replaceAll(" ", "")));
        System.out.println(this.stack);
    }

    @Override
    public List<Integer> getStack() {
        return stack;
    }

    @Override
    public Object pop() {
        System.out.println(this.stack);
        return this.stack.pop();
    }
}
