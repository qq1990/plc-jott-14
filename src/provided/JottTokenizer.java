package src.provided;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Donald Burke, Thomas Ehlers, Quan Quy, Clarke Kennedy, Andrew Yansick
 **/
public class JottTokenizer {
    /**
     * Helper function which displays errors with a common format.
     * @param message Error message to be printed
     * @param fileName Filename of file causing error
     * @param lineNumber Line number at which error was raised
     */
    public static void printErrorMsg(String message, String fileName, int lineNumber) {
        System.err.println("Syntax Error:\n"+message+"\n"+fileName+":"+lineNumber);
    }

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
            System.out.println("File "+filename+
                " could not be located/opened.");
            //e.printStackTrace();
            return null;
        }

        // Tokenize the String
        ArrayList<Token> tokens = new ArrayList<Token>();
        String buffer;
        int i = 0;  // Index of the next character to read in
        int line = 1;
        while (i < data.length() && i != -1) {
            buffer = String.valueOf(data.charAt(i));
            i++;
            // Whitespace Handler
            if (buffer.equals(" ") || buffer.equals("\r")) {
                buffer = ""; // Does nothing
            } 
            // Comment Handler
            else if (buffer.equals("#")) {
                while (i < data.length() && (data.charAt(i) != '\n')) {
                    i++;
                }
            } 
            // Newline Handler
            else if (buffer.equals("\n")) {
                line++;
            } 
            // Comma, Rbracket, Lbracket, Rbrace, Lbrace, and Semicolon Handler
            else if (buffer.equals(",")) {
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
            } 
            // Colon Handler (also checks for double colon representing function call)
            else if (buffer.equals(":")) {
                if (i < data.length() && data.charAt(i) == ':') {
                    buffer += data.charAt(i);
                    i++;
                    tokens.add(new Token(buffer, filename, line, TokenType.FC_HEADER));
                } else {
                    tokens.add(new Token(buffer, filename, line, TokenType.COLON));
                }
            } 
            // Math Operation Handler
            else if (buffer.equals("+") || buffer.equals("-") || 
                    buffer.equals("*") || buffer.equals("/") ||
                    buffer.equals("^")) {
                tokens.add(new Token(buffer, filename, line, TokenType.MATH_OP));
            } 
            // Relational Operator and Assignment Handler
            else if (buffer.equals("=") || buffer.equals("!") || 
                    buffer.equals("<") || buffer.equals(">")) {
                if (i < data.length() && data.charAt(i) == '=') {
                    buffer += data.charAt(i);
                    i++;
                    tokens.add(new Token(buffer, filename, line, TokenType.REL_OP));
                } else if (buffer.equals("=")) {
                    tokens.add(new Token(buffer, filename, line, TokenType.ASSIGN));
                } else if (buffer.equals("!")) {
                    if (i < data.length()) {
                        printErrorMsg("'!' expected '=', got '"+data.charAt(i)+
                                "' (ASCII="+(int)data.charAt(i)+")", filename, line);
                    } else {
                        printErrorMsg("'!' expected '=', reached end of file", filename, line);
                    }
                    i = -1;
                } else {
                    tokens.add(new Token(buffer, filename, line, TokenType.REL_OP));
                }
            } 
            // Number Handler
                // Numbers starting with a digit
            else if (Character.isDigit(buffer.charAt(0))) {
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
            } 
                // Numbers starting with a decimal
            else if (buffer.equals(".")) {
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
                        printErrorMsg("'.' expected a digit, reached end of file", filename, line);
                    } else {
                        printErrorMsg("'.' expected a digit, got '"+data.charAt(i)+
                                "' (ASCII="+(int)data.charAt(i)+")", filename, line);
                    }
                    i = -1;
                }
            } 
            // String Handler
            else if (buffer.equals("\"")) {
                while (i < data.length() && (Character.isDigit(data.charAt(i)) || 
                        Character.isLetter(data.charAt(i)) || data.charAt(i) == ' ')) {
                    buffer += data.charAt(i);
                    i++;
                }
                if (i >= data.length()) {
                    printErrorMsg("String expected '\"', reached end of file",
                            filename, line);
                    i = -1;
                } else if (data.charAt(i)=='\"') {
                    buffer += data.charAt(i);
                    i++;
                    tokens.add(new Token(buffer, filename, line, TokenType.STRING));
                } else {
                    printErrorMsg("System Error:\nString expected '\"', got '"+data.charAt(i)+
                            "' (ASCII="+(int)data.charAt(i)+")", filename, line);
                    i = -1;
                }
            } 
            // Name/Id Handler
            else if (Character.isLetter(buffer.charAt(0))) {
                while (i < data.length() && (Character.isDigit(data.charAt(i)) || Character.isLetter(data.charAt(i)))) {
                    buffer += data.charAt(i);
                    i++;
                }
                tokens.add(new Token(buffer, filename, line, TokenType.ID_KEYWORD));
            } 
            // Unknown handler
            else {
                printErrorMsg("Unknown character, '"+buffer+"' (ASCII="+
                        (int)buffer.charAt(0)+")", filename, line);
                i = -1;
            }
        }
        if (i == -1) {
            tokens = null;
        }
		return tokens;
	}
}
