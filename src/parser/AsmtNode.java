package src.parser;

// import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

public class AsmtNode implements BodyStmtNode {
    private Token type;
    private IdNode name;
    private ExprNode expr;

    public AsmtNode(Token type, IdNode name, ExprNode expr) {
        this.type = type;
        this.name = name;
        this.expr = expr;
    }

    @Override
    public String convertToJott() {
        if (type == null) {
            return name.convertToJott() + " = " + expr.convertToJott() + ";";
        }
        return type.getToken() + " " + name.convertToJott() + " = " + expr.convertToJott() + ";";
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
    
    public static AsmtNode parse(ArrayList<Token> tokens) throws SyntaxException{
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in AsmtNode");
        }
        Token t = tokens.get(0);

        Token type = null;
        if ((t.getToken().equals("Double")
                || t.getToken().equals("Integer")
                || t.getToken().equals("String")
                || t.getToken().equals("Boolean"))){        
            type = t;
            tokens.remove(0);
        }

        IdNode name = IdNode.parse(tokens);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            throw new SyntaxException("Syntax Error in AsmtNode");
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Syntax Error in AsmtNode");
        }
        tokens.remove(0);

        return new AsmtNode(type, name, expr);
    }

    public static void main(String[] args) throws SyntaxException{
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("Double", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("=", "test", 1, TokenType.ASSIGN));
        tokens.add(new Token("1", "test", 1, TokenType.NUMBER));
        tokens.add(new Token(";", "test", 1, TokenType.SEMICOLON));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        AsmtNode v = null;
        v = AsmtNode.parse(tokens);
        System.out.println(v.convertToJott());
    }
}
