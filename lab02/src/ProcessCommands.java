import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import exceptions.DirectoryExistsException;
import exceptions.InvalidArgumentsException;
import exceptions.RenameException;
import exceptions.UnknownCommand;

import javax.print.attribute.HashAttributeSet;

public class ProcessCommands {
    private HashMap<String, Command> commands;
    private File wd;
    public ProcessCommands() {
        wd = new File(System.getProperty("user.dir"));
        commands = new HashMap<>();
        commands.put("exit" , this::exit);
        commands.put("pwd" , this::pwd);
        commands.put("ls" , this::ls);
        commands.put("cd" , command -> {
            try {
                cd(command);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("rm" , command -> {
            try {
                rm(command);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("mkdir", command -> {
            try {
                mkdir(command);
            } catch (InvalidArgumentsException | DirectoryExistsException e) {
                System.out.println(e);
            }
        });
        commands.put("mv", command -> {
            try {
                mv(command);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (InvalidArgumentsException | RenameException e) {
                System.out.println(e);
            }
        });
        commands.put("cp", command -> {
            try {
                cp(command);
            } catch (InvalidArgumentsException e) {
                System.out.println(e);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("cat", command -> {
            try {
                cat(command);
            } catch (InvalidArgumentsException e) {
                System.out.println(e);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("length", command -> {
            try {
                length(command);
            } catch (InvalidArgumentsException e) {
                System.out.println(e);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("head", command -> {
            try {
                head(command);
            } catch (InvalidArgumentsException e) {
                System.out.println(e);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("tail", command -> {
            try {
                tail(command);
            } catch (InvalidArgumentsException e) {
                System.out.println(e);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        });
        commands.put("grep", command -> {
            try {
                grep(command);
            } catch (InvalidArgumentsException e) {
                System.out.println(e);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("Oops something went wrong");
            }
        });
    }

    public String getDirectoryPath(){
        return wd.getAbsolutePath();
    }

    public void process(String[] command) throws UnknownCommand{
        if (commands.get(command[0]) != null) {
            commands.get(command[0]).execute(command);
        } else {
            throw new UnknownCommand();
        }
//        switch (command[0]) {
//            case "exit":
//                exit(command);
//                break;
//
//            case "pwd":
//                pwd(command);
//                break;
//
//            case "ls":
//                ls(command);
//                break;
//
//            case "cd":
//                try {
//                    cd(command);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//
//            case "rm":
//                try {
//                    rm(command);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//
//            case "mkdir":
//                try {
//                    mkdir(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (DirectoryExistsException e) {
//                    System.out.println(e);
//                }
//                break;
//
//            case "mv":
//                try {
//                    mv(command);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (RenameException e) {
//                    System.out.println(e);
//                }
//                break;
//
//            case "cp":
//                try {
//                    cp(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//
//            case "cat":
//                try {
//                    cat(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//
//            case "length":
//                try {
//                    length(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//            case "head":
//                try {
//                    head(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//
//            case "tail":
//                try {
//                    tail(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                }
//                break;
//
//            case "grep":
//                try {
//                    grep(command);
//                } catch (InvalidArgumentsException e) {
//                    System.out.println(e);
//                } catch (FileNotFoundException e) {
//                    System.out.println("File not found");
//                } catch (IOException e) {
//                    System.out.println("Oops something went wrong");
//                }
//                break;
//
//            default:
//                throw new UnknownCommand();
//        }
    }


    /**
     * writes the lines that have matches form source to target file
     * @param command
     * <ul>
     * <li><b>'-p'</b>: optional, if given then pattern can be specified </li>
     * <li><b>&lt p &gt</b>: pattern, defaults to "", so every line is copied </li>
     * <li><b>'-i'</b>: input file </li>
     * <li><b>&lt i &gt</b>: name of input file </li>
     * <li><b>'-o'</b>: output file optional if not given the lines will be printed to console</li>
     * <li><b>&lt o &gt</b>: name of output file </li>
     * </ul>
     * @throws InvalidArgumentsException if '-i' is not given
     * @throws IOException
     */
    private void grep(String[] command) throws InvalidArgumentsException, IOException {
        String input = null;
        String output = null;
        String pattern = "";
        for (int i = 0; i < command.length; i++) {
            if ((i+1 < command.length) && command[i].equals("-i")) {
                i++;
                input = command[i];
            } else if ((i+1 < command.length) && command[i].equals("-o")) {
                i++;
                output = command[i];
            } else if ((i+1 < command.length) && command[i].equals("-p")) {
                i++;
                pattern = command[i];
            }
        }
        if (input == null) {
            throw new InvalidArgumentsException();
        }
        pattern = ".*" + pattern + ".*";
        File source = findFile(input);
        Scanner scanner = new Scanner(source);
        File target;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            target = findFile(output);
            try {
                fileWriter = new FileWriter(target);
                bufferedWriter = new BufferedWriter(fileWriter);
            } catch (IOException e) {
                scanner.close();
                throw new InvalidArgumentsException();
            }
        } catch (FileNotFoundException e) {
            target = null;
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.matches(pattern)) {
                if (target != null) {
                    bufferedWriter.write(line + "\n");
                } else{
                    System.out.println(line);
                }
            }
        }
        bufferedWriter.close();
        scanner.close();;

    }

    /**
     * writes the last n lines of the file
     * @param command
     * <ul>
     * <li><b>'-n'</b>: optional, if given then n can be specified </li>
     * <li><b>&lt n &gt</b>: the number of lines to print </li>
     * <li><b>&lt file &gt</b>: the name of the file, this should be the last argument</li>
     * </ul>
     * @throws InvalidArgumentsException if '-n' is given but it isnt a valid number or filename is not given
     * @throws FileNotFoundException if <b>command[1]</b> is not found in <b>wd</b> or it is unable to be opened for reading
     */
    private void tail(String[] command) throws InvalidArgumentsException, FileNotFoundException {
        int n = 10;
        if(command[1].equals("-n")){
            try {
                n = Integer.parseInt(command[2]);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                n = 10;
                throw new InvalidArgumentsException();
            }
        }
        File file = findFile(command[command.length-1]);
        Scanner scanner = new Scanner(file);
        LinkedList<String> strings = new LinkedList<String>();
        int counter = 0;
        while(scanner.hasNextLine()){
            strings.add(counter++ + ": " + scanner.nextLine());
            n--;
            if (n < 0) {
                strings.remove();
            }
        }
        for (String string : strings) {
            System.out.println(string);
        }
        scanner.close();
    }

    /**
     * writes the first n lines of the file
     * @param command
     * <ul>
     * <li><b>'-n'</b>: optional, if given then n can be specified </li>
     * <li><b>&lt n &gt</b>: the number of lines to print </li>
     * <li><b>&lt file &gt</b>: the name of the file, this should be the last argument</li>
     * </ul>
     * @throws InvalidArgumentsException if '-n' is given but it isnt a valid number or filename is not given
     * @throws FileNotFoundException if <b>command[1]</b> is not found in <b>wd</b> or it is unable to be opened for reading
     */
    private void head(String[] command) throws InvalidArgumentsException, FileNotFoundException {
        int n = 10;
        if(command[1].equals("-n")){
            try {
                n = Integer.parseInt(command[2]);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                n = 10;
                throw new InvalidArgumentsException();
            }
        }
        File file = findFile(command[command.length-1]);
        Scanner scanner = new Scanner(file);
        int counter = 0;
        while((n > 0) && scanner.hasNextLine()){
            System.out.println(counter++ + ": " +scanner.nextLine());
            n--;
        }
        scanner.close();
    }

    /**
     * writes the length of source file to console
     * @param command
     * <ul>
     * <li><b>command[1]</b> source file </li>
     * </ul>
     * @throws InvalidArgumentsException if <code>  command.length < 2  </code>
     * @throws FileNotFoundException if <b>command[1]</b> is not found in <b>wd</b> or it is unable to be opened for reading
     */
    private void length(String[] command) throws InvalidArgumentsException, FileNotFoundException{
        if (command.length < 2) {
            throw new InvalidArgumentsException();
        }
        File file = findFile(command[1]);
        System.out.println(wrap("Length of " + command[1], "" + file.length()));
    }

    /**
     * writes source file to console
     * @param command
     * <ul>
     * <li><b>command[1]</b> source file </li>
     * </ul>
     * @throws InvalidArgumentsException if <code>  command.length < 2  </code>
     * @throws FileNotFoundException if <b>command[1]</b> is not found in <b>wd</b> or it is unable to be opened for reading
     */
    private void cat(String[] command) throws InvalidArgumentsException, FileNotFoundException {
        if (command.length < 2) {
            throw new InvalidArgumentsException();
        }
        File source = findFile(command[1]);
        Scanner scanner = new Scanner(source);
        System.out.println(wrapFirstLine(source.getName()));
        int count = 0;
        while (scanner.hasNextLine()) {
            System.out.println(count++ + ": " +scanner.nextLine());
        }
        System.out.println(wrapLastLine(source.getName().length()));
        scanner.close();
    }

    /**
     * copies source file to target file
     * @param command 
     * <ul>
     * <li><b>command[1]</b> source file </li>
     * <li><b>command[2]</b> target file </li>
     * </ul>
     * @throws InvalidArgumentsException if <code>  command.length < 3  </code>
     * @throws FileNotFoundException if either <b>command[1]</b> or <b>command[2]</b> is not found in <b>wd</b> or they are unable to be opened for reading/writing
     */
    private void cp(String[] command) throws InvalidArgumentsException, FileNotFoundException{
        if (command.length < 3) {
            throw new InvalidArgumentsException();
        }
        File source = findFile(command[1]);
        File target = findFile(command[2]);
        FileInputStream fileInputStream = new FileInputStream(source);
        // BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        // BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        read: while (true) {
            int b;
            try {
                b = fileInputStream.read();
            } catch (IOException e) {
                System.out.println("Unknown Exception happened");
                break read;
            }
            if (b == -1) {
                break read;
            }
            try {
                fileOutputStream.write(b);
            } catch (IOException e) {
                System.out.println("Unknown Exception happened");
                break read;
            }
        }
        try {
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Well shit happens");
        }
        System.out.println(wrapFirstLine("Copy succesful"));
    }

    /**
     * im too lazy to write this
     */
    private void mv(String[] command) throws FileNotFoundException, InvalidArgumentsException, RenameException {
        if (command.length <= 2) {
            throw new InvalidArgumentsException();
        }
        File file = findFile(command[1]);
        if(!file.renameTo(new File(wd, command[2]))){
            throw new RenameException();
        }
        System.out.println(wrapFirstLine("Rename succesful"));
    }

    /**
     * im too lazy to write this
     */
    private void mkdir(String[] command) throws InvalidArgumentsException , DirectoryExistsException {
        if (command.length <= 1) {
            throw new InvalidArgumentsException();
        }
        File file = new File(wd, command[1]);
        if(!file.mkdir()){
            throw new DirectoryExistsException();
        }
        
    }

    /**
     * im too lazy to write this
     */
    private void rm(String[] command) throws FileNotFoundException {
        File deleteFile = null;
        if (command.length <= 1) {
            throw new FileNotFoundException();
        }
        try {
            deleteFile = findFile(command[1]);
        } catch (FileNotFoundException e) {
            throw e;
        }
        if (deleteFile == null) {
            throw new FileNotFoundException();
        }
        deleteFile.delete();
        System.out.println(wrapFirstLine("Delete succesful"));
    }

    /**
     * im too lazy to write this
     */
    private File findFile(String string) throws FileNotFoundException {
        for (File file : wd.listFiles()) {
            if (file.getName().equals(string)) {
                return file;
            }
        }
        throw new FileNotFoundException();
    }

    /**
     * im too lazy to write this
     */
    private void cd(String[] command) throws FileNotFoundException {
        File nextDir = null;
        if (command.length <= 1) {
            throw new FileNotFoundException();
        }
        if (command[1].equals("..")) {
            nextDir = wd.getParentFile();
        } else {
            for (File file : wd.listFiles()) {
                // System.out.println("filename: " + "|" +file.getName() + "|" + " commandFileName: "+ "|" + command[1] + "|");
                // boolean equals = file.getName().equals(command[1]);
                // System.out.println("equals: " + equals);
                if (file.getName().equals(command[1])) {
                    nextDir = file;
                }
            }
        }
        if (nextDir == null){
            throw new FileNotFoundException();
        }
        wd = nextDir;
    }

    /**
     * im too lazy to write this
     */
    private void ls(String[] command) {
        System.out.println(wrapFirstLine("List"));
        try {
            
            for (File file : wd.listFiles()) {
                System.out.print(file.getName());
                try {
                    String before = file.getName().length() < 8 ? "\t\t" : "\t";
                    if (command[1].equals("-l")) {
                        System.out.print(before + "| type: " +(file.isDirectory() ? "directory\t" : file.isFile() ? "file\t\t" : "unknown\t") + " | size: " + convert(file.length()));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //actually this doesnt matter :)
                }
                System.out.println();
            }
        } catch (NullPointerException e) {
            System.out.println("Unable to list files");
        }
        System.out.println(wrapLastLine(4)+ "\n");
    }

    /**
     * im too lazy to write this
     */
    private String convert(long totalSpace) {
        double steps = Math.pow(2, 10); // could be Math.pow(10, 3) its confusing
        if (totalSpace < Math.pow(steps, 1)) {
            return totalSpace + "B";
        } else if (totalSpace < Math.pow(steps, 2)) {
            return ( (double)totalSpace / Math.pow(steps, 1)) + "KB";
        } else if (totalSpace < Math.pow(steps, 3)) {
            return ((double)totalSpace / Math.pow(steps, 2)) + "MB";
        } else if (totalSpace < Math.pow(steps, 4)) {
            return ((double)totalSpace / Math.pow(steps, 3)) + "GB";
        } else if (totalSpace < Math.pow(steps, 5)) {
            return ((double)totalSpace / Math.pow(steps, 4)) + "TB";
        }
        return "very big";
    }

    /**
     * im too lazy to write this
     */
    protected void pwd(String[] command) {
        // try {
        //      startingDirectoryPath = System.getProperty("user.dir");
        // } catch (Exception e) {
        //     System.out.println(e);
        //     return;
        // }
        // File dir = new File(startingDirectoryPath);
        // System.out.println(startingDirectoryPath);
        // System.out.println("Number of files in directory: " + dir.listFiles().length + "\n");
        String path = "";
        try {
            path = wd.getCanonicalPath();
            path = wrap("Path", path);
        } catch (IOException e) {
            path = "Unable to get current path";
        }
        System.out.println(path);
    }

    /**
     * im too lazy to write this
     */
    protected void exit(String[] command) {
        System.out.println(wrapFirstLine("Exit") + "\n");
        System.exit(0);
    }

    /**
     * im too lazy to write this
     */
    private String wrap(String title, String string){
        int len = string.length();
        String res = wrapFirstLine(title) + "\n" + string + "\n" + wrapLastLine(len) + "\n";
        return res;
    }
    
    /**
     * im too lazy to write this
     */
    private String wrapFirstLine(String title){
        return "--------" + title + "--------";
    }
    
    /**
     * im too lazy to write this
     */
    private String wrapLastLine(int... fillLength){
        //TODO append fillLength '-' characters efficiently if possible currently it appends 4
        return "--------------------";
    }

}
