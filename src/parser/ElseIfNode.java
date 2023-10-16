package src.parser;

import java.util.ArrayList;

import src.provided.Token;
import src.provided.TokenType;

public class ElseIfNode implements BodyStmtNode {

    private ExprNode expr;
    private Body body;
    private ArrayList<ElseIfNode> elseiflist;
    private ElseNode else_node;
    
    public ElseIfNode(ExprNode expr, Body body) {
        this.expr = expr;
        this.body = body;
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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static ElseIfNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in IfNode");
        }
        tokens.remove(0);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode");
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);
        
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode");
        }
        tokens.remove(0);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode");
        }
        tokens.remove(0);

        Body body = Body.parse(tokens);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode");
        }
        tokens.remove(0);

        return new ElseIfNode(expr, body);
        
    }
}
