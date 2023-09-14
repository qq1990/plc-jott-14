package src.provided;

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author Donald Burke, Andrew Yansick
 **/

import java.util.ArrayList;

public class JottTokenizer {

  // The list of tokens that will be returned by the tokenizer
  public ArrayList<Token> tokens;

  // Constructor
  public JottTokenizer() {
    tokens = new ArrayList<Token>();
  }

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
     */
    public static ArrayList<Token> tokenize(String filename){
		return null;
	}
}