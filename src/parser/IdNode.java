package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Thomas Ehlers
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
        return (getName().equals("True") || getName().equals("False")) || FuncNode.varTable.containsKey(getName()); 
    }

    @Override
    public Type getType() {
        if (validateTree()) {
            if (getName().equals("True") || getName().equals("False")) {
                return Type.Boolean;
            }
            return FuncNode.varTable.get(getName());
        }
        return null;
    }

    public String getName() {
        return id_name.getToken();
    }
    
    public static IdNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IdNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in IdNode", tokens.get(0));
        }
        return new IdNode(tokens.remove(0));
    }
}
