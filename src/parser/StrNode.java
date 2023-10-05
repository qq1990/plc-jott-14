package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
    
    public static StrNode parse(ArrayList<Token> tokens) {
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.STRING) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in StrNode");
            return null;
        }
        return new StrNode(tokens.remove(0));
    }
}
