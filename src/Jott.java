package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import src.provided.JottParser;
import src.provided.JottTokenizer;
import src.provided.JottTree;
import src.provided.Token;

// Donald Burke + others
public class Jott {
    private static enum Language {
        jott, python, c, java
    }

    public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Jott Translator\n");
        System.out.println("Enter filename to begin:");
        String filename = scanner.nextLine();
        
        System.out.println("Which language would you like to translate to?:");
        System.out.println("0: Jott\n" + "1: Python\n" + "2: C\n" + "3: Java");
        int choice = scanner.nextInt();
        while (choice > 3 || choice < 0) {
            System.out.println("Invalid language choice\n");
            System.out.println("Which language would you like to translate to?:");
            System.out.println("0: Jott\n" + "1: Python\n" + "2: C\n" + "3: Java");
            choice = scanner.nextInt();
        }
        scanner.close();
        */
        if (args.length != 3) {
            System.out.println("Usage: java Jott <input file> <output file> <language>");
            return;
        }
        String input = args[0];
        String output = args[1];
        int choice = Language.valueOf(args[2].toLowerCase()).ordinal();
        if (choice > 3 || choice < 0) {
            System.out.println("Invalid language choice\n");
            System.out.println("Usage: java Jott <input file> <output file> <language>");
            return;
        }
        translate(input, output, choice);
    }

    public static void translate(String input, String output, int langChoice) {
        ArrayList<Token> tokens = JottTokenizer.tokenize(input);
        if (tokens != null) {
            JottTree root = JottParser.parse(tokens);
            if (!(root == null)) {
                System.out.println("Translating " + input + " ...");
                try {
                    FileWriter writer = new FileWriter(output);
                    if (langChoice == 0) {
                        writer.write(root.convertToJott());
                    }
                    else if (langChoice == 1) {
                        writer.write(root.convertToPython(0));
                    }
                    else if (langChoice == 2) {
                        writer.write(root.convertToC());
                    }
                    else if (langChoice == 3) {
                        int lastSlash = output.lastIndexOf(File.separatorChar);
                        String className = output.substring(lastSlash+1, output.lastIndexOf("."));
                        //System.out.println(root.convertToJava(className));
                        writer.write(root.convertToJava(className));
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error writing to given output filename.");
                    System.out.println(e.getMessage());
                    e.getStackTrace();
                }
            }
        }
    }
}
