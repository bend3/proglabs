import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import exceptions.DirectoryExistsException;
import exceptions.InvalidArgumentsException;
import exceptions.RenameException;
import exceptions.UnknownCommand;

public class ProcessCommands {
    private File wd;
    public ProcessCommands() {
        wd = new File(System.getProperty("user.dir"));
    }

    public String getDirectoryPath(){
        return wd.getAbsolutePath();
    }

    public void process(String[] command) throws UnknownCommand{
        switch (command[0]) {
            case "exit":
                exit(command);
                break;
            
            case "pwd":
                pwd(command);
                break;

            case "ls":
                ls(command);
                break;

            case "cd":
                try {
                    cd(command);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                break;

            case "rm":
                try {
                    rm(command);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                break;

            case "mkdir":
                try {
                    mkdir(command);
                } catch (InvalidArgumentsException e) {
                    System.out.println(e);
                } catch (DirectoryExistsException e) {
                    System.out.println(e);
                }
                break;

            case "mv":
                try {
                    mv(command);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                } catch (InvalidArgumentsException e) {
                    System.out.println(e);
                } catch (RenameException e) {
                    System.out.println(e);
                } 
                break;
            
            case "cp":
                try {
                    cp(command);
                } catch (InvalidArgumentsException e) {
                    System.out.println(e);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                break;

            case "cat":
                try {
                    cat(command);
                } catch (InvalidArgumentsException e) {
                    System.out.println(e);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                break;

            case "length":
                try {
                    length(command);
                } catch (InvalidArgumentsException e) {
                    System.out.println(e);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found");
                }
                break;
            default:
                throw new UnknownCommand();
        }
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

    private void mkdir(String[] command) throws InvalidArgumentsException , DirectoryExistsException {
        if (command.length <= 1) {
            throw new InvalidArgumentsException();
        }
        File file = new File(wd, command[1]);
        if(!file.mkdir()){
            throw new DirectoryExistsException();
        }
        
    }

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

    private File findFile(String string) throws FileNotFoundException {
        for (File file : wd.listFiles()) {
            if (file.getName().equals(string)) {
                return file;
            }
        }
        throw new FileNotFoundException();
    }

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

    protected void exit(String[] command) {
        System.out.println(wrapFirstLine("Exit") + "\n");
        System.exit(0);
    }

    private String wrap(String title, String string){
        int len = string.length();
        String res = wrapFirstLine(title) + "\n" + string + "\n" + wrapLastLine(len) + "\n";
        return res;
    }
    
    private String wrapFirstLine(String title){
        return "--------" + title + "--------";
    }
    
    private String wrapLastLine(int... fillLength){
        //TODO append fillLength '-' characters efficiently if possible currently it appends 4
        return "--------------------";
    }

}
