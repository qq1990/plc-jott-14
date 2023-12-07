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
        String str = "else {\n\t" + 
        body.convertToJava(className) + "\n}";
        return str;
    }

    @Override
    public String convertToC() {
        String str = "else {\n\t" + 
        body.convertToC() + "\n}";
        return str;
    }

    @Override
    public String convertToPython(int depth) {
        String str = "";
        for(int i = 0; i < depth; i++) {
            str = str + "\t";
        }
        str = str + "else:\n";
        
        str = str + body.convertToPython(depth+1);
        return str;        
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
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in ElseNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in ElseNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            return null;
        }

        ArrayList<Token> storage = tokens;
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);
        if(body == null) {
            throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens", storage.get(storage.size()-1));
        }
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in else statement", tokens.get(0));
        }

        if (tokens.size() == 0){
            return null;
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

    @Override
    public Token getToken() {
        return body.getToken();
    }
}
