package src.parser;

import java.util.ArrayList;

import src.provided.Token;
import src.provided.TokenType;

// Andrew Yansick
public class ElseIfNode implements BodyStmtNode {

    private ExprNode expr;
    private BodyNode body;
    
    public ElseIfNode(ExprNode expr, BodyNode body) {
        this.expr = expr;
        this.body = body;
    }

    public BodyNode getBody() {
        return body;
    }

    @Override
    public String convertToJott() {
        String s = " elseif ["+expr.convertToJott()+"] {\n"+body.convertToJott()+"}";
        return s;
    }

    @Override
    public String convertToJava(String className) {
        String str = " else if (" + expr.convertToJava(className) + ") {\n" + 
        body.convertToJava(className) + "}";
        return str;
    }

    @Override
    public String convertToC() {
        String str = " else if (" + expr.convertToC() + ") {\n" + 
        body.convertToC() + "}";
        return str;
    }

    @Override
    public String convertToPython(int depth) {
        String str = "elif " + expr.convertToPython(depth) + ":\n" + 
        body.convertToPython(depth+1);
        return str;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        if(expr.validateTree() && body.validateTree()) {
            if(expr.getType() == Type.Boolean) {
                return true;
            }
        }
        throw new SemanticException("Semantic error: Invalid while", null);
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0){
            return null;
        }
        
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }
        
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        ArrayList<Token> storage = tokens;
        ExprNode expr = ExprNode.parse(tokens);
        if(expr == null) {
            throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens", storage.get(storage.size()-1));
        }
        
        if (tokens.size() == 0){
            return null;
        }
        
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        ArrayList<Token> storage2 = tokens;
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);
        if(body == null) {
            throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens", storage2.get(storage2.size()-1));
        }
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in elseif statement", tokens.get(0));
        }

        if (tokens.size() == 0){
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        return new ElseIfNode(expr, body);
        
    }

    @Override
    public Type getRetType() {
        return body.getRetType();
    }

    @Override
    public boolean isReturnable() {
        if(body.getRetType() == Type.Void || body.getRetType() == null) {
            return false;
        }
        return true;
    }

    @Override
    public Token getToken() {
        return body.getToken();
    }
}
