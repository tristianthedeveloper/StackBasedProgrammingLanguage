package com.tristian.stacklanguage;

public class LStack {
    private java.util.Stack<Object> stack;


    public static LStack setUpStack() {

        LStack instance = new LStack();
        instance.stack = new java.util.Stack<>();
        return instance;
    }

    public Object pop() {
        return stack.pop();
    }


    public Object push(Object item) {
        return this.stack.push(item);
    }

    public Object removeAt(int item) {
        return this.stack.remove(0);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * @param index
     * @param item
     * @return the element previously at the specified position
     */
    public Object set(int index, Object item) {
        return this.stack.set(index, item);
    }


    public void print() {
        System.out.println((char) this.pop());
    }


    @SuppressWarnings("unchecked")
    public int xor(int index1, int index2) {
        this.set(index1, (int) this.at(index1) ^ (int) this.at(index2));
        return (int) this.at(index1) ^ (int) this.at(index2);
    }

    public Object at(int where) {
        return this.stack.get(where);
    }

    public java.util.Stack getBackingStack() {
        return this.stack;
    }
}
