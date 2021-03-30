package com.tristian.stacklanguage.var;

import com.sun.deploy.security.ValidationState;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public class Variable {

    public static transient final List<MemoryEntry<?>> entries = new CopyOnWriteArrayList<>();

    public static final Pattern TYPE_REGEX = Pattern.compile("((int|str|double|float|long))?+\\s*+ [a-z]+([a-zA-Z0-9_]*+)?+ ");
    public static final Pattern VAR_NAME_REGEX = Pattern.compile("[a-z]+([a-zA-Z0-9_]*+)?+");  // alphanumeric variable name, must start with a letter, can have numbers


    /**
     * @param name The name of the variable, must contain %s, CASE SENSITIVE!
     * @return The {@MemoryEntry<T>} entry of the entries list.
     */
    public static MemoryEntry<?> getEntryByName(String name) {
        // case sensitive
        return !entries.isEmpty() ? entries.stream().filter(entry -> entry.name.equals(name)).findFirst().orElse(null) : null;
    }

    /**
     * @param <T> The type of variable.
     */
    public static class MemoryEntry<T> {

        public transient volatile T value;
        public transient final String name;

        public MemoryEntry(String name, T value) {
            this.name = name;
            this.value = value;
            System.out.println("memory entry created with name " + name + ", and val: " + value);
        }

        public int getEntryIndex() {
            return entries.indexOf(this);
        }


    }

    public static class VariableParser {


        // what the fuck?
        private static final transient Pattern p = Pattern.compile("(int|double|float|long)*+ [a-z]+([a-zA-Z0-9_]*+)?+ \\s*=\\s*([0-9]*)?+|str*+ [a-z]+([a-zA-Z0-9_]*+)?+ (\\s*=\\s*\"[a-zA-Z0-9_]\")");

        /**
         * @param string A string that matches the regular expression,(int|double|float|long)*+ [a-z]+([a-zA-Z0-9_]*+)?+ \s*=\s*([0-9]*)?+|str*+ [a-z]+([a-zA-Z0-9_]*+)?+ (\s*=\s*"[a-zA-Z0-9_]")
         *               i.e int a = 3
         * @return
         */

        public static MemoryEntry<?> tryParseVariable(String string) {

            boolean debug = false;
            if (string.matches(p.toString())) {
                // split at the equal sign, break it up into [datatype name (needs split), equals, value]
                String[] split = p.split("=");
                String datatype = split[0].split(" ")[0]; // break it up into two parts, [datatype, name]
                if (debug)
                    System.out.println(Arrays.toString(split));


            }
            return null;
        }


    }


}
