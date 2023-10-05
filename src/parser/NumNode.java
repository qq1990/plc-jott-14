package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

public class NumNode implements ExprNode {
    private Token sign, num_val;

    public NumNode(Token sn, Token num) {
        sign = sn;
        num_val = num;
    }

    @Override
    public String convertToJott() {
        if (sign != null) {
            return sign.getToken()+num_val.getToken();
        }
        return num_val.getToken();
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
    
    public static NumNode parse(ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in NumNode");
            return null;
        }

        Token t = tokens.get(0);
        if (t.getToken().equals("-")) {
            tokens.remove(0);
        } else {
            t = null;
        }
        if (tokens.get(0).getTokenType() != TokenType.NUMBER) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in NumNode");
            return null;
        }
        return new NumNode(t, tokens.remove(0));
    }
}
