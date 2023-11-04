package src.parser;

import java.util.ArrayList;
import java.util.HashMap;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

// Donald Burke
public class FuncNode implements JottTree{
    IdNode funcName;
    FuncParamsNode funcParams;
    FuncReturnNode funcReturnType;
    BodyNode funcBody;
    public static HashMap<String, Type> varTable = new HashMap<>();
    
    
    public FuncNode(IdNode id, FuncParamsNode params, FuncReturnNode returnType, BodyNode body) {
        this.funcName = id;
        this.funcParams = params;
        this.funcReturnType = returnType;
        this.funcBody = body;
    }

    @Override
    public String convertToJott() {
        String out = "def ";
        out += this.funcName.convertToJott();
        out += "[";
        out += this.funcParams.convertToJott();
        out += "]:";
        out += this.funcReturnType.convertToJott();
        out += "{";
        out += this.funcBody.convertToJott();
        out += "}";
        return out;
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
    
    public static FuncNode parse(ArrayList<Token> tokens) throws SyntaxException {
        varTable = new HashMap<>();

        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax error in FuncNode");
        }
        if (!tokens.get(0).getToken().equals("def")) {
            throw new SyntaxException("Syntax error in FuncNode", tokens.get(0));
        }
        tokens.remove(0);

        IdNode func_name = IdNode.parse(tokens);

        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax error in FuncNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax error in FuncNode", tokens.get(0));
        }
        tokens.remove(0);

        FuncParamsNode fcp = FuncParamsNode.parse(tokens);

        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax error in FuncNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax error in FuncNode", tokens.get(0));
        }
        tokens.remove(0);
        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax error in FuncNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.COLON) {
            throw new SyntaxException("Syntax error in FuncNode", tokens.get(0));
        }
        tokens.remove(0);

        FuncReturnNode returnType = FuncReturnNode.parse(tokens);

        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax error in FuncNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax error in FuncNode", tokens.get(0));
        }
        tokens.remove(0);

        BodyNode body = BodyNode.parse(tokens);

        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax error in FuncNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax error in FuncNode", tokens.get(0));
        }
        tokens.remove(0);

        return new FuncNode(func_name, fcp, returnType, body);
    }

    public static void main(String[] args) throws SyntaxException {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("def", "test", 1, TokenType.ID_KEYWORD));
        FuncNode v = null;
        v = FuncNode.parse(tokens);
        System.out.println(v.convertToJott());
    }

}
