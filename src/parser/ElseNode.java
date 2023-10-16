package src.parser;

import java.util.ArrayList;

import src.provided.Token;
import src.provided.TokenType;

public class ElseNode implements BodyStmtNode {

    private ExprNode expr;
    private Body body;
    
    public ElseNode(Body body) {
        if (body != null) {
            this.body = body;
        }
    }

    @Override
    public String convertToJott() {
        String s;
        if (body != null) {
            s.concat("else{"+body.convertToJott()+"}");
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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static ElseNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in ElseNode");
        }
        tokens.remove(0);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in ElseNode");
        }
        tokens.remove(0);

        Body body = Body.parse(tokens);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in ElseNode");
        }
        tokens.remove(0);

        return new ElseNode(body);
        
    }
}
