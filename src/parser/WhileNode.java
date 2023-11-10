package src.parser;

import src.provided.Token;
import src.provided.TokenType;

import java.util.ArrayList;

// Andrew Yansick
public class WhileNode implements BodyStmtNode {

    private ExprNode expr;
    private BodyNode body;
    
    public WhileNode(ExprNode expression, BodyNode body) {
        this.expr = expression;
        this.body = body;
    }

    @Override
    public String convertToJott() {
        return "while["+expr.convertToJott()+"]{"+body.convertToJott()+"}";
        
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

    //cannot make new variable in while loop
    //check synbol table for the body
    //if symbol table changes or size changes, false
    //
    @Override
    public boolean validateTree() throws SemanticException {
        if(expr.validateTree() && body.validateTree()) {
            if(expr.getType() == Type.Boolean) {
                return true;
            }
        }
        throw new SemanticException("Semantic error: Invalid while statement", null);
    }

    public Type getRetType() {
        return null;
    }

    public static WhileNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0){
            return null;
        }

        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in WhileNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode, no L bracket", tokens.get(0));
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
            throw new SyntaxException("Syntax Error in WhileNode, no R bracket", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode, no L brace", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        int x = FuncNode.varTable.size();
        ArrayList<Token> storage2 = tokens;
        BodyNode body = BodyNode.parse(tokens);
        if(body == null) {
            throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens", storage2.get(storage2.size()-1));
        }
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in while loop", tokens.get(0));
        }

        if (tokens.size() == 0){
            return null;
        }

        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode, missing R brace", tokens.get(0));
        }
        tokens.remove(0);

        return new WhileNode(expr, body);
    }

    @Override
    public boolean isReturnable() {
        return false;
    }
}
