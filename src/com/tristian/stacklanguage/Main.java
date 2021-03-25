package com.tristian.stacklanguage;

import java.util.Scanner;

public class Main {

    private static Main instance;

    private Stack stack;

    public static void main(String[] args) {
        new Main().load();
    }

    public void load() {
        instance = this;
        this.stack = Stack.setUpStack();
        init();

    }

    private void init() {

        Scanner mainStdin = new Scanner(System.in);

        String next;
        while (!(next = mainStdin.nextLine()).equalsIgnoreCase("exit()")) {
            CommandParser.runCommand(next);
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public Stack getStack() {
        return stack;
    }
}
