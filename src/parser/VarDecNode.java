package src.parser;

// import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Clarke Kennedy
public class VarDecNode implements BodyStmtNode {
    private Token type;
    private IdNode name;

    public VarDecNode(Token type, IdNode name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String convertToJott() {
        return type.getToken() + " " + name.convertToJott() + ";";
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
    
    public static VarDecNode parse(ArrayList<Token> tokens) throws SyntaxException{
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in VarDecNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in VarDecNode", tokens.get(0));
        }
        Token t = tokens.get(0);
        if (!(t.getToken().equals("Double")
                || t.getToken().equals("Integer")
                || t.getToken().equals("String")
                || t.getToken().equals("Boolean"))){
            throw new SyntaxException("Syntax Error in VarDecNode", t);
        }
        Token type = t;
        tokens.remove(0);

        IdNode name = IdNode.parse(tokens);
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in VarDecNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Syntax Error in VarDecNode", tokens.get(0));
        }
        tokens.remove(0);
        return new VarDecNode(type, name);
    }

}
