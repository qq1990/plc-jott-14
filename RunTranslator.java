import java.util.ArrayList;

import src.provided.JottParser;
import src.provided.JottTokenizer;
import src.provided.JottTree;
import src.provided.Token;

// Donald Burke + others
public class RunTranslator {

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Welcome to the Jott Translator\n");
        // System.out.println("Enter filename to begin:");
        // String filename = scanner.nextLine();
        
        // System.out.println("Which language would you like to translate to?:");
        // System.out.println("0: Jott\n" + "1: Python\n" + "2: C\n" + "3: Java");
        // int choice = scanner.nextInt();
        // while (choice > 3 || choice < 0) {
        //     System.out.println("Invalid language choice\n");
        //     System.out.println("Which language would you like to translate to?:");
        //     System.out.println("0: Jott\n" + "1: Python\n" + "2: C\n" + "3: Java");
        //     choice = scanner.nextInt();
        // }
        // scanner.close();
        if (args.length != 2) {
            System.out.println("Usage: java RunTranslator <filename> <language>");
            System.out.println("0: Jott\n" + "1: Python\n" + "2: C\n" + "3: Java");
            return;
        }
        String filename = args[0];
        int choice = Integer.parseInt(args[1]);
        if (choice > 3 || choice < 0) {
            System.out.println("Invalid language choice\n");
            System.out.println("Usage: java src/RunTranslator <filename> <language>");
            System.out.println("0: Jott\n" + "1: Python\n" + "2: C\n" + "3: Java");
            return;
        }
        translate(filename, choice);
    }

    public static void translate(String file, int langChoice) {
        ArrayList<Token> tokens = JottTokenizer.tokenize(file);
        if (tokens != null) {
            JottTree root = JottParser.parse(tokens);
            if (!(root == null)) {
                System.out.println("Translating " + file + " ...");

                if (langChoice == 0)
                    System.out.println(root.convertToJott());
                else if (langChoice == 1)
                    System.out.println(root.convertToPython());
                else if (langChoice == 2)
                    System.out.println(root.convertToC());
                else if (langChoice == 3)
                    System.out.println(root.convertToJava(""));     // empty param for now
            } else { System.out.println("Parse failed."); }
        }
    }
}
