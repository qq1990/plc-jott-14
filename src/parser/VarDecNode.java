package src.parser;

// import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Clarke Kennedy
public class VarDecNode implements BodyStmtNode {
    private Type type;
    private IdNode name;

    public VarDecNode(Type type, IdNode name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String convertToJott() {
        return type.name() + " " + name.convertToJott() + ";";
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
    public boolean validateTree() throws SemanticException{
        if (FuncNode.varTable.containsKey(name.convertToJott())) {
            throw new SemanticException("Semantic Error in VarDecNode, variable already declared: " + name.convertToJott());
        }
        FuncNode.varTable.put(name.convertToJott(), type);
        return name.validateTree();
    }
    
    public static VarDecNode parse(ArrayList<Token> tokens) throws SyntaxException{
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in VarDecNode, reached end of file");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in VarDecNode, expected type keyword", tokens.get(0));
        }
        Token t = tokens.get(0);
        Type type = null;
        switch (t.getToken()) {
            case "Double":
                type = Type.Double;
                break;
            case "Integer":
                type = Type.Integer;
                break;
            case "String":
                type = Type.String;
                break;
            case "Boolean":
                type = Type.Boolean;
                break;
            default:
                throw new SyntaxException("Syntax Error in VarDecNode, invalid type keyword", t);
        }
        tokens.remove(0);

        IdNode name = IdNode.parse(tokens);
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in VarDecNode, reached end of file");
        }
        if (tokens.get(0).getTokenType() != TokenType.SEMICOLON) {
            throw new SyntaxException("Syntax Error in VarDecNode, expected semicolon", tokens.get(0));
        }
        tokens.remove(0);

        VarDecNode varDec = new VarDecNode(type, name);
        FuncNode.varTable.put(varDec.name.convertToJott(), varDec.type);

        return varDec;
    }

    public static void main(String[] args) throws SyntaxException{
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("Integer", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token("x", "test", 1, TokenType.ID_KEYWORD));
        tokens.add(new Token(";", "test", 1, TokenType.SEMICOLON));
        VarDecNode v = null;
        v = VarDecNode.parse(tokens);
        System.out.println(v.convertToJott());
    }
}
