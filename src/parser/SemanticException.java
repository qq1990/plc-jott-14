package src.parser;

import src.provided.Token;

// Clarke Kennedy
// Copy of SyntaxException.java by Thomas Ehlers
public class SemanticException extends Exception {
    public SemanticException(String message, Token token) {
        super(message + "\n" + token.getFilename() + ":" + token.getLineNum() + ":" + token.getToken());
    }
    public SemanticException(String message) {
        super(message);
    }
}