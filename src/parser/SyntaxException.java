package src.parser;

import src.provided.Token;

// Thomas Ehlers
public class SyntaxException extends Exception {
    public SyntaxException(String message, Token token) {
        super(message + "\n" + token.getFilename() + ":" + token.getLineNum() + ":" + token.getToken());
    }
    public SyntaxException(String message) {
        super(message);
    }
}
