package src.parser;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Clarke Kennedy
public class FuncReturnNode implements JottTree {
    private Token type;

    public FuncReturnNode(Token type) {
        this.type = type;
    }

    @Override
    public String convertToJott() {
        return type.getToken();
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
    
    public static FuncReturnNode parse(ArrayList<Token> tokens) throws SyntaxException{
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in FuncReturnNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in FuncReturnNode", tokens.get(0));
        }
        Token t = tokens.get(0);
        if (!(t.getToken().equals("Double")
                || t.getToken().equals("Integer")
                || t.getToken().equals("Void")
                || t.getToken().equals("String")
                || t.getToken().equals("Boolean"))){
            throw new SyntaxException("Syntax Error in FuncReturnNode", t);
        }
        Token type = t;
        tokens.remove(0);
        return new FuncReturnNode(type);
    }

    public static void main(String[] args) throws SyntaxException{
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("Void", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        FuncReturnNode v = null;
        v = FuncReturnNode.parse(tokens);
        System.out.println(v.convertToJott());
    }
}
