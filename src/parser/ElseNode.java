package src.parser;

import java.util.ArrayList;

import src.provided.Token;
import src.provided.TokenType;

// Andrew Yansick
public class ElseNode implements BodyStmtNode {

    private BodyNode body;
    
    public ElseNode(BodyNode body) {
        if (body != null) {
            this.body = body;
        }
    }

    public BodyNode getBody() {
        return body;
    }

    @Override
    public String convertToJott() {
        String s = "";
        if (body != null) {
            s = s.concat("else{"+body.convertToJott()+"}");
        }
        
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
        if(body.validateTree()) {
            return true;
        }
        throw new SemanticException("Semantic error: Invalid else statement", null);
    }

    public static ElseNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in ElseNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in ElseNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode, no token", null);
        }
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in else statement", tokens.get(0));
        }

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in ElseNode, no token", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in ElseNode", tokens.get(0));
        }
        tokens.remove(0);

        return new ElseNode(body);
        
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
