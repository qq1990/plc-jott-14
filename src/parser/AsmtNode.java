package src.parser;

// import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Clarke Kennedy
public class AsmtNode implements BodyStmtNode {
    private Type type;
    private IdNode name;
    private ExprNode expr;

    public AsmtNode(Type type, IdNode name, ExprNode expr) {
        this.type = type;
        this.name = name;
        this.expr = expr;
    }

    @Override
    public String convertToJott() {
        if (type == null) {
            return name.convertToJott() + " = " + expr.convertToJott() + ";";
        }
        return type.name() + " " + name.convertToJott() + " = " + expr.convertToJott() + ";";
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
        // TODO Type checking relies on the symbol table
        return name.validateTree() && expr.validateTree();
    }
    
    public static AsmtNode parse(ArrayList<Token> tokens) throws SyntaxException{
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in AsmtNode, reached end of file");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in AsmtNode, expected id or keyword", tokens.get(0));
        }
        Token t = tokens.get(0);

        Type type = null;
        // if ((t.getToken().equals("Double")
        //         || t.getToken().equals("Integer")
        //         || t.getToken().equals("String")
        //         || t.getToken().equals("Boolean"))){        
        //     type = t;
        //     tokens.remove(0);
        // }
        switch (t.getToken()) {
            case "Double":
                type = Type.Double;
                tokens.remove(0);
                break;
            case "Integer":
                type = Type.Integer;
                tokens.remove(0);
                break;
            case "String":
                type = Type.String;
                tokens.remove(0);
                break;
            case "Boolean":
                type = Type.Boolean;
                tokens.remove(0);
                break;
        }

        IdNode name = IdNode.parse(tokens);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in AsmtNode, reached end of file");
        }
        if (tokens.get(0).getTokenType() != TokenType.ASSIGN) {
            throw new SyntaxException("Syntax Error in AsmtNode, expected assignment operator", tokens.get(0));
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in AsmtNode, reached end of file");
        }
        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Syntax Error in AsmtNode, expected semicolon", tokens.get(0));
        }
        tokens.remove(0);

        return new AsmtNode(type, name, expr);
    }

    public static void main(String[] args) throws SyntaxException{
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("Integer", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("=", "test", 1, TokenType.ASSIGN));
        tokens.add(new Token("5", "test", 1, TokenType.NUMBER));
        tokens.add(new Token(";", "test", 1, TokenType.SEMICOLON));
        AsmtNode v = null;
        v = AsmtNode.parse(tokens);
        System.out.println(v.convertToJott());
    }
}
