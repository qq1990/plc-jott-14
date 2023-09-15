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
    
    /**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
    */
    public static ArrayList<Token> tokenize(String filename){
        ArrayList<Token> tokenlist = new ArrayList<Token>();
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
        System.out.println(data);
		return tokenlist;
	}

    public static void main(String[] args) {
        tokenize("testCases\\tokenizerTestCases\\phase1Example.jott");
    }
}