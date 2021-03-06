package com.tristian.stacklanguage.var;

import com.tristian.stacklanguage.commands.CommandParser;
import com.tristian.stacklanguage.util.GeneralUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public final class Variable {

    public static transient List<MemoryEntry<?>> entries = new CopyOnWriteArrayList<>();

    public static final Pattern TYPE_REGEX = Pattern.compile("((int|str|double|float|long))?+\\s*+ [a-z]+([a-zA-Z0-9_]*+)?+ ");
    public static final Pattern VAR_NAME_REGEX = Pattern.compile("[a-z]+([a-zA-Z0-9_]*+)?+");  // alphanumeric variable name, must start with a letter, can have numbers


    /**
     * @param name The name of the variable, must contain %s, CASE SENSITIVE!
     * @return The {@MemoryEntry<T>} entry of the entries list.
     */
    public static MemoryEntry<?> getEntryByName(String name) {
        // case sensitive
        return !entries.isEmpty() ? entries.stream().filter(entry -> entry.name.replaceAll("[\\[\\]]", "").matches(name.replaceAll(" ", ""))).findFirst().orElse(null) : null;
    }

    /**
     * @param <T> The type of variable.
     */
    public static class MemoryEntry<T> {

        public transient Object value;
        public transient final String name;
        public transient final DataType type;

        /**
         * @param name  The name of this variable.
         * @param value typeof String, will be dynamically converted with the type.
         * @param type  The type of this variable.
         */
        public MemoryEntry(String name, T value, DataType type) {
            this.name = name;
            switch (type) {
                case STR: {
                    this.value = value;
                    break;
                }
                case FLOAT:
                    this.value = Float.parseFloat(("" + value).replaceAll(" ", ""));
                    break;
                case INT:
                    this.value = Integer.parseInt(("" + value).replaceAll(" ", ""));
                default:
                    this.value = value;
                    break;
            }
            this.type = type;
        }


        public int getEntryIndex() {
            return entries.indexOf(this);
        }


        /**
         * Only use this if you're certain this memory entry is of type INT or FLOAT!
         *
         * @return This number cast to a float value.
         */
        public Float valueAsFloat() {
            return (float) this.value;
        }

        /**
         * Only use this if you're certain this memory entry is of type INT or FLOAT!
         *
         * @return This number cast to a int value.
         */
        public int valueAsInt() {
            return (int) Integer.parseInt(("" + this.value).replaceAll(" ", ""));
        }


        @Override
        public String toString() {
            return "MemoryEntry{" +
                    "value=" + value +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    public enum DataType {

        INT,
        STR,
        FLOAT,
        ARRAY,
        AUTO // TODO :)
        ;

        DataType() {

        }

        public static DataType parseFromName(String name) {
            return Arrays.stream(values()).filter(e -> e.name().equalsIgnoreCase(name.toUpperCase())).findFirst().orElse(null);
        }

        public static DataType parseFromVal(Object val) {
            String toString = "" + val;

            // assume no classes will be used on the command line (lol)
            if (toString.matches("[0-9]*+")) {
                if (toString.matches("\\.")) {
                    return DataType.FLOAT;
                }
                return DataType.INT;
            }
            return DataType.STR;

        }


    }

    public static class VariableParser {


        // what the fuck?
        private static final transient Pattern p = Pattern.compile("(int|double|float)*+ [a-z]+([a-zA-Z0-9_]*+)?+ \\s*=\\s*(..*+)?+|str*+ [a-z]+([a-zA-Z0-9_]*+)?+ (\\s*=\\s*\"[a-zA-Z0-9_]+\")");

        /**
         * @param string A string that matches the regular expression,(int|double|float|long)*+ [a-z]+([a-zA-Z0-9_]*+)?+ \s*=\s*([0-9]*)?+|str*+ [a-z]+([a-zA-Z0-9_]*+)?+ (\s*=\s*"[a-zA-Z0-9_]")
         *               i.e int a = 3
         */
        public static MemoryEntry<?> tryAddVariable(String string) {

            boolean debug = false;
            if (string.matches(p.toString())) {
                // split at the equal sign, break it up into [datatype name (needs split), equals, value]
                String[] split = string.split("=");
                GeneralUtil.replaceRegisterValues(split);
                String[] nameAndDataType = split[0].split(" ");

                String datatype = nameAndDataType[0],
                        name = nameAndDataType[1];
                String value = split[1];

                CommandParser.Commands command;
                if ((command = CommandParser.Commands.valueOfThing(value.trim().split(" ")[0])) != null) {
                    try {
                        value = "" + command.commandClass.newInstance().run(
                                Arrays.copyOfRange(value.split(" "), 2, value.split(" ").length));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                value = value.replaceAll("[\\[\\]]", "");
                if (!validateName(name))
                    return null;

                DataType type = DataType.parseFromName(datatype); // break it up into two parts, [datatype, name]

                MemoryEntry<?> memEntry = null;
                if (!(value.contains(","))) memEntry = new MemoryEntry<>(name, value, type);

                if (value.contains(",")) {
                    List<Integer> ints = Arrays.stream(value.split(",")).map(e -> e.replaceAll(" ", "")).map(Integer::parseInt).collect(Collectors.toList());
                    memEntry = new IntArray<List<Integer>>(name, ints, DataType.ARRAY);
                }
                if (getEntryByName(name) != null) {
                    Objects.requireNonNull(getEntryByName(name)).value = value;
                    return getEntryByName(name);
                }
                if (debug) {
                    System.out.println(Arrays.toString(split));
                    System.out.println(type);
                    System.out.println(name);
                }

                entries.add(memEntry);
                return memEntry;
            }
            return null;
        }

        private static boolean validateName(String name) {
            if (DataType.parseFromName(name) != null) {
                System.out.println("Invalid variable name: \"" + name + "\"");
                return false;
            }
            return true;
        }

        public static void main(String[] args) {
            tryAddVariable("str a = \"test\"");
        }

    }
}

