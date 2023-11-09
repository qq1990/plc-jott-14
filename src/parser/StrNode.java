package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Thomas Ehlers
public class StrNode implements ExprNode {
    private Token str_val;

    public StrNode(Token str) {
        str_val = str;
    }

    @Override
    public String convertToJott() {
        return str_val.getToken();
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() throws SemanticException {
        return true;
    }

    @Override
    public Type getType() {
        return Type.String;
    }
    
    public static StrNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in StrNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.STRING) {
            throw new SyntaxException("Syntax Error in StrNode", tokens.get(0));
        }
        return new StrNode(tokens.remove(0));
    }
}
