package src.parser;

import src.provided.Token;
import src.provided.TokenType;

import java.util.ArrayList;

// Andrew Yansick
public class WhileNode implements BodyStmtNode {

    private ExprNode expr;
    private BodyNode body;
    
    public WhileNode(ExprNode expression, BodyNode body) {
        this.expr = expression;
        this.body = body;
    }

    @Override
    public String convertToJott() {
        return "while["+expr.convertToJott()+"]{\n"+body.convertToJott()+"}";
        
    }

    @Override
    public String convertToJava(String className) {
        String str = "while (" + expr.convertToJava(className) + ") {\n\t" + 
        body.convertToJava(className) + "\n}";
        return str;
    }

    @Override
    public String convertToC() {
        String str = "while (" + expr.convertToC() + ") {\n\t" + 
        body.convertToC() + "\n}";
        return str;
    }

    @Override
    public String convertToPython(int depth) {
        String str = "";
        for(int i = 0; i < depth+1; i++) {
            str = str + "\t";
        }
        str = str + "while " + expr.convertToPython(depth) + ":\n";
        for(int i = 0; i < depth+2; i++) {
            str = str + "\t";
        }
        str = str + body.convertToPython(depth+1);
        return str;
    }

    //cannot make new variable in while loop
    //check synbol table for the body
    //if symbol table changes or size changes, false
    //
    @Override
    public boolean validateTree() throws SemanticException {
        expr.validateTree(); 
        body.validateTree();
        if(expr.getType() == Type.Boolean) {
            return true;
        }
        throw new SemanticException("Semantic error in WhileNode, condition must be a boolean expression.", null);
    }

    public Type getRetType() {
        return body.getRetType();
    }

    public static WhileNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0) {
            return null;
        }

        if (!tokens.get(0).getToken().equals("while")) {
            throw new SyntaxException("Syntax Error in WhileNode, expected while keyword.", tokens.get(0));
        }
        Token t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens before '['.", t); }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode, expected '['.", tokens.get(0));
        }
        t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens before condition.", t); }
        ExprNode expr = ExprNode.parse(tokens);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens before ']'.", t); }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in WhileNode, expected ']'.", tokens.get(0));
        }
        t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens before '{'.", t); }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode, expected '{'.", tokens.get(0));
        }
        t = tokens.remove(0);
        
        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens before body.", t); }
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in WhileNode, ran out of tokens before '}'.", t); }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in WhileNode, expected '}'.", tokens.get(0));
        }
        tokens.remove(0);

        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in while loop", t);
        }

        return new WhileNode(expr, body);
    }

    @Override
    public boolean isReturnable() {
        return false;
    }

    @Override
    public Token getToken() {
        return body.getToken();
    }
}
