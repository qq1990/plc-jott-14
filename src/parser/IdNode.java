package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

public class IdNode implements ExprNode {
    private Token id_name;

    public IdNode(Token name) {
        id_name = name;
    }

    @Override
    public String convertToJott() {
        return id_name.getToken();
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
    
    public static IdNode parse(ArrayList<Token> tokens) {
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in IdNode");
            return null;
        }
        return new IdNode(tokens.remove(0));
    }
}
