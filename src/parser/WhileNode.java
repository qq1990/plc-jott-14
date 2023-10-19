package src.parser;


// import src.provided.JottTokenizer;
import src.provided.Token;
import src.provided.TokenType;

import java.util.ArrayList;

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

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static WhileNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        tokens.remove(0);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);
        
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        tokens.remove(0);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        tokens.remove(0);

        BodyNode body = BodyNode.parse(tokens);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode");
        }
        tokens.remove(0);

        return new WhileNode(expr, body);
    }

    public static void main(String[] args) throws SyntaxException{
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("while", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("[", "test", 1, TokenType.L_BRACKET));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token(">", "test", 1, TokenType.REL_OP));
        tokens.add(new Token("1", "test", 1, TokenType.NUMBER));
        tokens.add(new Token("]", "test", 1, TokenType.R_BRACKET));
        tokens.add(new Token("{", "test", 1, TokenType.L_BRACE));
        tokens.add(new Token("Integer", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("=", "test", 1, TokenType.ASSIGN));
        tokens.add(new Token("2", "test", 1, TokenType.NUMBER));
        tokens.add(new Token(";", "test", 1, TokenType.SEMICOLON));
        tokens.add(new Token("}", "test", 1, TokenType.R_BRACE));
        WhileNode v = null;
        v = WhileNode.parse(tokens);
        System.out.println(v.convertToJott());
    }
    
    
}
