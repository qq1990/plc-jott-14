package src.parser;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Clarke Kennedy
public class FuncReturnNode implements JottTree {
    private Type type;

    public FuncReturnNode(Type type) {
        this.type = type;
    }

    @Override
    public String convertToJott() {
        return type.name();
    }

    @Override
    public String convertToJava(String className) {
        String s = "";
        switch (type.name()) {
            case "Integer":
                s = "int";
                break;
            case "Double":
                s = "double";
                break;
            case "String":
                s = "String";
                break;
            case "Boolean":
                s = "boolean";
                break;
            case "Void":
                s = "void";
                break;
        }
        return s;
    }

    @Override
    public String convertToC() {
        String s = "";
        switch (type.name()) {
            case "Integer":
                s = "int";
                break;
            case "Double":
                s = "double";
                break;
            case "String":
                s = "char[]";
                break;
            case "Boolean":
                s = "bool";
                break;
            case "Void":
                s = "void";
                break;
        }
        return s;
    }

    @Override
    public String convertToPython(int depth) {
        return "";
    }

    @Override
    public boolean validateTree() {
        return true;
    }
    
    public static FuncReturnNode parse(ArrayList<Token> tokens) throws SyntaxException{
        if (tokens.size() == 0) {
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in FuncReturnNode, expected type keyword.", tokens.get(0));
        }
        Token t = tokens.get(0);
        Type type = null;
        switch (t.getToken()) {
            case "Double":
                type = Type.Double;
                break;
            case "Integer":
                type = Type.Integer;
                break;
            case "String":
                type = Type.String;
                break;
            case "Boolean":
                type = Type.Boolean;
                break;
            case "Void":
                type = Type.Void;
                break;
            default:
                throw new SyntaxException("Syntax Error in FuncReturnNode, invalid type keyword.", t);
        }
        tokens.remove(0);
        return new FuncReturnNode(type);
    }

    public Type getType() {
        return type;
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
