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
        throw new SemanticException("Semantic error: Invalid while statement");
    }

    public Type getRetType() {
        return null;
    }

    public static WhileNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in WhileNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode", tokens.get(0));
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);
        
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode", tokens.get(0));
        }
        tokens.remove(0);

        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in while loop");
        }

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode", tokens.get(0));
        }
        tokens.remove(0);

        return new WhileNode(expr, body);
    }

    @Override
    public boolean isReturnable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isReturnable'");
    }
}
