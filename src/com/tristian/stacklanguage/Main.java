package com.tristian.stacklanguage;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.file.StackFile;
import com.tristian.stacklanguage.file.StackFileInterpreter;
import com.tristian.stacklanguage.interpreter.JarOutStream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.jar.JarOutputStream;

public class Main {

    private static Main instance;

    private LStack LStack;

    public static void main(String[] args) {
        args = new String[]{
                "--interpret",
                "test.sasm"
        };
        JarOutStream.loadPaths();
        System.out.println(Arrays.toString(args));
        ;
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("--interpret")) {
                String fileName = args[1]; // should be
                StackFileInterpreter file = StackFileInterpreter.start();

                file.loadFromFile(new File(fileName));


                try {
                    File compiled = new JarOutStream().compile(file.create());
                    new JarOutStream().run(compiled);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        new Main().load();
    }

    public void load() {
        instance = this;
        this.LStack = LStack.setUpStack();
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

    public LStack getLStack() {
        return LStack;
    }
}
