package com.tristian.stacklanguage.interpreter;

import com.tristian.stacklanguage.LStack;
import com.tristian.stacklanguage.Main;
import com.tristian.stacklanguage.commands.CommandParser;

import javax.swing.*;
import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.jar.*;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;

public class JarOutStream {

    /**
     * Paths to all the language's commands.
     * SETUP:
     * Key: Absolute path of the file
     * Value: package path that we want it in.
     */
    private static Map<String, String> paths = new WeakHashMap<String, String>();

    Manifest manifest = new Manifest();

    public static void loadPaths() {
        // loop through all the files in the directory.
//        File directory = new File(new File(CommandParser.class.getResource("CommandParser.class").getPath()).getParentFile().getParent().replace("file:", "").replace("!", ""));
//        System.out.println(directory
//        );

//        String packageName = CommandParser.class.getPackage().getName();
//        System.out.println(paths);
//        paths.put(LStack.class.getResource("LStack.class").getPath(), LStack.class.getPackage().getName().replaceAll("\\.+", "/"));
    }


    public void run(File source) throws IOException {
        long time = System.nanoTime();
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "com.tristian.stacklanguage.Main");
        JarOutputStream target = new JarOutputStream(new FileOutputStream("./output.jar"), manifest);
        add(source, target, "com/tristian/stacklanguage");
        loopThroughAllFiles(new File(Main.class.getResource("Main.class").getFile()).getParentFile().getParentFile(), target);
        paths.forEach((realPath, classPath) -> {
            try {
                add(new File(realPath), target, classPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        target.close();
        System.out.println("FINISHED! in " + (System.nanoTime() - time));
    }

    private void loopThroughAllFiles(File parentFile, JarOutputStream target) throws IOException {
        if (!(parentFile.isDirectory()))
            add(parentFile, target,
                    Main.class.getPackage().getName().split("/")[0]);
        else
            for (File f : parentFile.listFiles()) {
                if (f.isDirectory()) {
                    loopThroughAllFiles(f, target);
                    continue;
                }
                if (f.getPath().endsWith(".class") && !f.getName().equals("Main.class")) {
                    String mainCP = Main.class.getPackage().getName().replaceAll("\\.", "\\\\\\\\");
                    System.out.println(mainCP);
                    String path = f.getPath();
                    String newPath = path.split(mainCP)[1];
                    String newnewPath = mainCP.replaceAll("\\\\\\\\", "\\\\") + newPath;
                    System.out.println("newnewpath: " + newnewPath.replaceAll("\\\\", "/").replaceFirst(f.getName(), ""));
                    String myLifeSucks = newnewPath.replaceAll("\\\\", "/").replaceFirst(f.getName(), "");
                    add(f, target, myLifeSucks.substring(0, myLifeSucks.length() - 1));
                    continue;
                }
                String mainCP = Main.class.getPackage().getName().replaceAll("\\.", "\\\\\\\\");
                String path = f.getPath();
                String newPath = path.split(mainCP)[1];
                String newnewPath = mainCP.replaceAll("\\\\\\\\", "\\\\") + newPath;
                System.out.println("newnewpath: " + newnewPath.replaceAll("\\\\", "/").replaceFirst(f.getName(), ""));
                add(f, target, newnewPath.replaceAll("\\\\", "/").replaceFirst(f.getName(), ""));
            }

    }

    private void add(File source, JarOutputStream target, String customPathOptional) throws IOException {
        BufferedInputStream in = null;
        try {
            if (source.isDirectory()) {
                String name = source.getPath().replace("\\", "/");
                if (!name.isEmpty()) {
                    if (!name.endsWith("/"))
                        name += "/";
                    JarEntry entry = new JarEntry(name);
                    entry.setTime(source.lastModified());
                    target.putNextEntry(entry);

                    target.closeEntry();
                }
                for (File nestedFile : source.listFiles()) {
                    add(nestedFile, target, !customPathOptional.equals("") ? customPathOptional : nestedFile.getPath());
                    System.out.println(nestedFile + " is directory");
                }
                return;
            }


            if (customPathOptional.contains("$")) {
                customPathOptional = customPathOptional.substring(0, customPathOptional.lastIndexOf("/"));
            }
            JarEntry entry = new JarEntry(
                    !customPathOptional.equals("") ? customPathOptional.replaceAll("banana", "") + "/" + source.getName() : source.getName());
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null)
                in.close();
        }
    }

    /**
     * @param source A source file.
     * @return The .class
     */

    public File compile(File source) {
        long time = System.nanoTime();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits1 =
                fileManager.getJavaFileObjects(source);
        compiler.getTask(null, fileManager, null, null, null, compilationUnits1).call();
        System.out.println("Compiled src file in: " + (System.nanoTime() - time));
        return new File("./" + source.getName().replaceAll("\\.(.*)", ".class"));
    }


}