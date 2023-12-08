package src.parser;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;
import java.util.Collections;

// Quan
public class ReturnStmtNode implements JottTree {
    private ExprNode expr;
    private Token rToken;

    public ReturnStmtNode(ExprNode expr, Token rToken) {
        this.expr = expr;
        this.rToken = rToken;
    }

    @Override
    public String convertToJott() {
        return "return " + expr.convertToJott() + ";";
    }

    @Override
    public String convertToJava(String className) {
        // Convert the expression to Java code
        return "return " + expr.convertToJava(className) + ";";
    }

    @Override
    public String convertToC() {
        // Convert the expression to C code
        return "return " + expr.convertToC() + ";";
    }

    @Override
    public String convertToPython(int indentLevel) {
        // Convert the expression to Python code
        StringBuilder pythonCode = new StringBuilder();
        String indent = String.join("", Collections.nCopies(indentLevel, "\t"));
        pythonCode.append(indent);
        pythonCode.append("return ");
        pythonCode.append(expr.convertToPython(indentLevel));
        return pythonCode.toString();
    }

    @Override
    public boolean validateTree() throws SemanticException {
        // If there is expression, validate
        if (expr != null) {
            return expr.validateTree();
        }
        // else it's empty, always legit
        else{
            return true;
        }
    }

    public Type getRetType() {
        // If the return statement is not the empty string, pass the type of the ExprNode; else, return null.
        return (expr != null) ? expr.getType() : null;
    }

    public Token getToken() {
        return rToken;
    }

    public static ReturnStmtNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            return null;
        }
        Token t = tokens.get(0);
        if (t.getToken().equals("return")) {
            tokens.remove(0); // Remove "return" keyword
            if (tokens.size() == 0) { throw new SyntaxException("Syntax Error in ReturnStmtNode, ran out of tokens", t); }
            ExprNode expr = ExprNode.parse(tokens);
            if (tokens.size() == 0) { throw new SyntaxException("Syntax Error in ReturnStmtNode, ran out of tokens", t); } 
            if (tokens.get(0).getTokenType() == TokenType.SEMICOLON) {
                tokens.remove(0); // Remove the semicolon
                return new ReturnStmtNode(expr, t);
            } else {
                throw new SyntaxException("Syntax Error in ReturnStmtNode, missing semicolon", t);
            }
        }
        return null; // Return null if "return" keyword is not found
    }
}
