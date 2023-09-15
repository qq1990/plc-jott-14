package src.provided;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Donald Burke, Thomas Ehlers
 **/
public class JottTokenizer {
    //private static final String DIGITS = "0123456789";
    //private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //private static final String OTHER = " ";

    /**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
        // Read file into a String
        String data = null;
        try {
            Scanner myReader = new Scanner(new File(filename));
            myReader.useDelimiter("\\Z");
            data = myReader.next();
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File "+filename+" could not be located/opened.");
            //e.printStackTrace();
        }

        // Tokenize the String
        ArrayList<Token> tokens = new ArrayList<Token>();
        String buffer;
        int i = 0;  // Index of the next character to read in
        int line = 1;
        while (i < data.length() && i != -1) {
            buffer = String.valueOf(data.charAt(i));
            i++;
            if (buffer.equals(" ") || buffer.equals("\r")) {
                buffer = ""; // Does nothing
            } else if (buffer.equals("#")) {
                while (i < data.length() && (data.charAt(i) != '\n')) {
                    i++;
                }
            } else if (buffer.equals("\n")) {
                line++;
            } else if (buffer.equals(",")) {
                tokens.add(new Token(buffer, filename, line, TokenType.COMMA));
            } else if (buffer.equals("]")) {
                tokens.add(new Token(buffer, filename, line, TokenType.R_BRACKET));
            } else if (buffer.equals("[")) {
                tokens.add(new Token(buffer, filename, line, TokenType.L_BRACKET));
            } else if (buffer.equals("}")) {
                tokens.add(new Token(buffer, filename, line, TokenType.R_BRACE));
            } else if (buffer.equals("{")) {
                tokens.add(new Token(buffer, filename, line, TokenType.L_BRACE));
            } else if (buffer.equals(";")) {
                tokens.add(new Token(buffer, filename, line, TokenType.SEMICOLON));
            } else if (buffer.equals(":")) {
                if (i < data.length() && data.charAt(i) == ':') {
                    buffer += data.charAt(i);
                    i++;
                    tokens.add(new Token(buffer, filename, line, TokenType.FC_HEADER));
                } else {
                    tokens.add(new Token(buffer, filename, line, TokenType.COLON));
                }
            } else if (buffer.equals("+") || buffer.equals("-") || buffer.equals("*") || buffer.equals("/")) {
                tokens.add(new Token(buffer, filename, line, TokenType.MATH_OP));
            } else if (buffer.equals("=") || buffer.equals("!") || buffer.equals("<") || buffer.equals(">")) {
                if (i < data.length() && data.charAt(i) == '=') {
                    buffer += data.charAt(i);
                    i++;
                    tokens.add(new Token(buffer, filename, line, TokenType.REL_OP));
                } else if (buffer.equals("=")) {
                    tokens.add(new Token(buffer, filename, line, TokenType.ASSIGN));
                } else if (buffer.equals("!")) {
                    if (i < data.length()) {
                        System.err.println("Error on line "+line+": '!' expected '=', got '"+data.charAt(i)+"' (ASCII="+(int)data.charAt(i)+").");
                    } else {
                        System.err.println("Error on line "+line+": '!' expected '=', reached end of file.");
                    }
                    i = -1;
                } else {
                    tokens.add(new Token(buffer, filename, line, TokenType.REL_OP));
                }
            } else if (Character.isDigit(buffer.charAt(0))) {
                while (i < data.length() && Character.isDigit(data.charAt(i))) {
                    buffer += data.charAt(i);
                    i++;
                }
                if (i < data.length() && data.charAt(i)=='.') {
                    buffer += data.charAt(i);
                    i++;
                }
                while (i < data.length() && Character.isDigit(data.charAt(i))) {
                    buffer += data.charAt(i);
                    i++;
                }
                tokens.add(new Token(buffer, filename, line, TokenType.NUMBER));
            } else if (buffer.equals(".")) {
                if (i < data.length() && Character.isDigit(data.charAt(i))) {
                    buffer += data.charAt(i);
                    i++;
                    while (i < data.length() && Character.isDigit(data.charAt(i))) {
                        buffer += data.charAt(i);
                        i++;
                    }
                    tokens.add(new Token(buffer, filename, line, TokenType.NUMBER));
                } else {
                    if (i >= data.length()) {
                        System.err.println("Error on line "+line+": '.' expected a digit, reached end of file.");
                    } else {
                        System.err.println("Error on line "+line+": '.' expected a digit, got '"+data.charAt(i)+"' (ASCII="+(int)data.charAt(i)+").");
                    }
                    i = -1;
                }
            } else if (buffer.equals("\"")) {
                while (i < data.length() && (Character.isDigit(data.charAt(i)) || Character.isLetter(data.charAt(i)) || data.charAt(i) == ' ')) {
                    buffer += data.charAt(i);
                    i++;
                }
                if (i >= data.length()) {
                    System.err.println("Error on line "+line+": String expected '\"', reached end of file.");
                    i = -1;
                } else if (data.charAt(i)=='\"') {
                    buffer += data.charAt(i);
                    i++;
                    tokens.add(new Token(buffer, filename, line, TokenType.STRING));
                } else {
                    System.err.println("Error on line "+line+": String expected '\"', got '"+data.charAt(i)+"' (ASCII="+(int)data.charAt(i)+").");
                    i = -1;
                }
            } else if (Character.isLetter(buffer.charAt(0))) {
                while (i < data.length() && (Character.isDigit(data.charAt(i)) || Character.isLetter(data.charAt(i)))) {
                    buffer += data.charAt(i);
                    i++;
                }
                tokens.add(new Token(buffer, filename, line, TokenType.ID_KEYWORD));
            } else {
                System.err.println("Error on line "+line+": Unknown character, '"+buffer+"' (ASCII="+(int)buffer.charAt(0)+").");
                i = -1;
            }
        }
        // Testing Code
        System.out.println(data); 
		return tokens;
	}

    public static void main(String[] args) {
        for (Token t: tokenize("testCases\\tokenizerTestCases\\strings.jott")) {
            System.out.println(t.getTokenType()+": ["+t.getToken()+"] ("+t.getFilename()+" "+t.getLineNum()+")");
        }
    }
}