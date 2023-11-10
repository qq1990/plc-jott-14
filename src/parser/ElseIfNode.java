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
        String s = "elseif["+expr.convertToJott()+"]{"+body.convertToJott()+"}";
        return s;
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
        if(expr.validateTree() && body.validateTree()) {
            if(expr.getType() == Type.Boolean) {
                return true;
            }
        }
        throw new SemanticException("Semantic error: Invalid while", null);
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseIfNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseIfNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode, no token", null);
        }
        ExprNode expr = ExprNode.parse(tokens);
        
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseIfNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseIfNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in ElseIfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode, no token", null);
        }
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in elseif statement", tokens.get(0));
        }

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseIfNode, no token", tokens.get(0));
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
}
