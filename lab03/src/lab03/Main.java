package lab03;

import lab03.Beer.Beer;
import lab03.exceptions.UnknownCommandException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PQueue<Integer> s = new PQueue<Integer>();
        s.push(1); s.push(2); s.push(3); s.push(4);
        for (Integer i : s) {
            System.out.println(i);
        }
        Scanner scanner = new Scanner(System.in);
        mainLoop: while (true){
            try {
                Beer.processCommands(scanner.nextLine().split(" "));
            } catch (UnknownCommandException exception){
                System.out.println(exception);
            }
        }
    }
}
