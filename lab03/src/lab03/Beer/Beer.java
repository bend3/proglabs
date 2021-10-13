package lab03.Beer;

import lab03.Command;
import lab03.exceptions.InvalidCommandException;
import lab03.exceptions.UnknownCommandException;


import java.io.*;
import java.util.*;

public class Beer implements Serializable, Comparable<Beer> {
    private static HashMap<String, Command> commands;
    private static HashMap<String, Comparator<Beer>> comparators;

    static {
        comparators = new HashMap<>();
        comparators.put("name" , (Comparator.comparing(Beer::getName)));
        comparators.put("style" , (Comparator.comparing(Beer::getStyle)));
        comparators.put("strength" , (Comparator.comparing(Beer::getStrength)));
    }

    static {
        commands = new HashMap<>();
        commands.put("exit", command -> System.exit(0));
        commands.put("add" , command -> {
            try {
                add(command);
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        });
        commands.put("list", Beer::list);
        commands.put("save" , command -> {
            try {
                saveList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        commands.put("load" , command -> {
            try{
                loadList();
            } catch (FileNotFoundException e){
                System.out.println("Unable to find saved beers");
            } catch (IOException e){
                e.printStackTrace();
            }
        });
        commands.put("search" , command -> {
            try {
                search(command);
            } catch (InvalidCommandException e){
                e.printStackTrace();
            }
        });
        commands.put("find" , command -> {
            try {
                find(command);
            } catch (InvalidCommandException e){
                e.printStackTrace();
            }
        });
        commands.put("delete" , command -> {
            try {
                delete(command);
            } catch (InvalidCommandException e){
                e.printStackTrace();
            }
        });

    }

    private static File storageDir = new File(System.getProperty("user.dir"));
    private static String storageName = "beers.ser";
    private String name;
    private String style;
    private double strength;

    public Beer(String name,String style, double strength){
        this.name = name;
        this.style = style;
        this.strength = strength;
    }

    public static ArrayList<Beer> beerList = new ArrayList<>();

    public static void processCommands(String[] command) throws UnknownCommandException{
        if (commands.containsKey(command[0])){
            if (commands.get(command[0]) == null){
                System.out.println("unimplemented");
                return;
            }
            commands.get(command[0]).execute(command);
        } else {
            throw new UnknownCommandException();
        }
//        switch (command[0]){
//            case "exit":
//                System.exit(0);
//            case "add":
//                try {
//                    add(command);
//                } catch (InvalidCommandException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case "list":
//                list(command);
//                break;
//            case "save":
//                try {
//                    saveList();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case  "load":
//                try{
//                    loadList();
//                } catch (FileNotFoundException e){
//                    System.out.println("Unable to find saved beers");
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
//                break;
//            case "search":
//                try {
//                    search(command);
//                } catch (InvalidCommandException e){
//                    e.printStackTrace();
//                }
//                break;
//            case "find":
//                try {
//                    find(command);
//                } catch (InvalidCommandException e){
//                    e.printStackTrace();
//                }
//                break;
//            case "delete":
//                try {
//                    delete(command);
//                } catch (InvalidCommandException e){
//                    e.printStackTrace();
//                }
//                break;
//
//            default:
//                throw new UnknownCommandException();
//        }
    }

    private static void delete(String[] command) throws InvalidCommandException {
        if (command.length< 2){
            throw  new InvalidCommandException();
        }
        Iterator<Beer> iterator = beerList.iterator();
        while (iterator.hasNext()){
           Beer beer = iterator.next();
           if (beer.name.equals(command[1])){
               iterator.remove();
           }
        }
    }

    private static void find(String[] command) throws InvalidCommandException {
        if(command.length<3){
            throw new InvalidCommandException();
        }
        String regex = ".*" + command[2] + ".*";
        for (Beer beer: beerList) {
            switch (command[1]){
                case "name":
                    if (beer.name.matches(regex)){
                        System.out.println(beer);
                    }
                    break;
                case "style":
                    if (beer.style.matches(regex)){
                        System.out.println(beer);
                    }
                    break;
                case "strength":
                    Double strength;
                    try {
                    strength = Double.parseDouble(command[2]);

                    }catch (NumberFormatException n){
                        throw new InvalidCommandException();
                    }
                    if (beer.strength == strength){
                        System.out.println(beer);
                    }
                    break;
                case "weaker":
                    try {
                        strength = Double.parseDouble(command[2]);

                    }catch (NumberFormatException n){
                        throw new InvalidCommandException();
                    }
                    if (beer.strength <= strength){
                        System.out.println(beer);
                    }
                    break;
            }
        }
    }

    private static void search(String[] command) throws InvalidCommandException {
        if(command.length<2){
            throw new InvalidCommandException();
        }
        for (Beer beer: beerList) {
            if (beer.name.equals(command[1])){
                System.out.println(beer);
            }
        }
    }

    private static void loadList() throws IOException {
        File source = null;
        for (File file: storageDir.listFiles()) {
//            System.out.println(file.getName());
            if (file.getName().equals(storageName)){
                source = file;
            }
        }
        if (source == null) {
            throw new FileNotFoundException();
        }
        try{
            FileInputStream fileIn = new FileInputStream(source);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Beer> list = (ArrayList<Beer>) objectIn.readObject();
            beerList = list;
            objectIn.close();
            fileIn.close();
            System.out.println("Successfully loaded beers");
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Unable to load beers");
        }
    }

    private static void saveList() throws IOException {
        try {
            File wd = new File(System.getProperty("user.dir"));
            File outputFile = new File(wd, storageName);
            outputFile.createNewFile();
            System.out.println("file created");
            FileOutputStream fileOut = new FileOutputStream(outputFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(beerList);
            objectOut.close();
            fileOut.close();
            System.out.println("Beers successfully saved");
        } catch (IOException e){
            System.out.println("Unable to save beers");
        }
    }

    private static void list(String[] command) {
        String[] args = command;
        ArrayList<Beer> tmpList = new ArrayList<>();
        for (Beer beer: beerList) {
            tmpList.add(beer);
        }
        tmpList.sort((o1, o2) -> {
            for (String cmd : command) {
                if (comparators.containsKey(cmd) && comparators.get(cmd).compare(o1, o2) != 0){
                    return comparators.get(cmd).compare(o1,o2);
                }
//                if (cmd.equals("name") && o1.getName().compareTo(o2.getName()) != 0){
//                    return comparators.get("name").compare(o1, o2);
//                }if (cmd.equals("style") && o1.getStyle().compareTo(o2.getStyle()) != 0){
//                    return o1.getStyle().compareTo(o2.getStyle());
//                }if (cmd.equals("strength") && Double.compare(o1.getStrength(), o2.getStrength()) != 0){
//                    return Double.compare(o1.getStrength(), o2.getStrength());
//                }
            }
            return 0;
        });
        for (Beer beer: tmpList) {
            System.out.println(beer);
        }
    }

    private static void add(String[] command) throws InvalidCommandException {
        if (command.length < 4){
            throw new InvalidCommandException();
        }
        String name = command[1];
        String style = command[2];
        double strength;
        try {
        strength = Double.parseDouble(command[3]);

        } catch (NumberFormatException e){
            throw new InvalidCommandException();
        }
        beerList.add(new Beer(name, style, strength));
        System.out.println("Beer successfully added");
    }

    public double getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public String getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", style='" + style + '\'' +
                ", strength=" + strength +
                '}';
    }

    @Override
    public int compareTo(Beer o) {
        return name.compareTo(o.getName());
    }
}
