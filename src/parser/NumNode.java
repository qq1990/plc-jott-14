package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Thomas Ehlers
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
    public boolean validateTree() { //throws SemanticException {
        return true;
    }
    
    @Override
    public Type getType() {
        if (num_val.getToken().contains(".")) {
            return Type.Double;
        }
        return Type.Integer;
    }

    public boolean isZero() {
        if (getType() == Type.Integer) {
            return Integer.parseInt(num_val.getToken()) == 0;
        }
        return Double.parseDouble(num_val.getToken()) == 0.0;
    }
    
    public static NumNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            return null;
        }

        Token t = tokens.get(0);
        if (t.getToken().equals("-") || t.getToken().equals("+")) {
            tokens.remove(0);
        } else {
            t = null;
        }
        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax Error in NumNode, ran out of tokens", t);
        } else if (tokens.get(0).getTokenType() != TokenType.NUMBER) {
            throw new SyntaxException("Syntax Error in NumNode, invalid number", tokens.get(0));
        }
        return new NumNode(t, tokens.remove(0));
    }
}
