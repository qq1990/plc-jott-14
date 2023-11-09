package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Thomas Ehlers
public class OpNode implements ExprNode {
    private Token op;
    private ExprNode left, right;

    public OpNode(Token o, ExprNode l, ExprNode r) {
        op = o;
        left = l;
        right = r;
    }

    @Override
    public String convertToJott() {
        return left.convertToJott()+op.getToken()+right.convertToJott();
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
        return (left.getType() != null && left.getType() == right.getType()) && 
            ( //(op.getToken().equals("!=") || op.getToken().equals("==")) ||
            (left.getType().equals(Type.Double) || left.getType().equals(Type.Integer)));
    }

    @Override
    public Type getType() {
        String o = op.getToken();
        if (validateTree()) {
            if (o.equals("+") || o.equals("-") || o.equals("*") 
                    || o.equals("/") || o.equals("^")) {
                return left.getType();
            } else { // if (o.equals("==") || o.equals("!=") || o.equals("<") || o.equals(">") || o.equals("<=") || o.equals(">=")) {
                return Type.Boolean;
            }
        }
        return null;
    }

    public static OpNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax Error in OpNode");
        }
        Token t = tokens.get(0);

        // Valid (left) operands - (no str/bool compare/equality?)
        ExprNode l;
        if (t.getTokenType() == TokenType.NUMBER || t.getToken().equals("-")) {
            l = NumNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.ID_KEYWORD &&
                !t.getToken().equals("True") && 
                !t.getToken().equals("False")) {
            l = IdNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.FC_HEADER) {
            l = CallNode.parse(tokens);
        } else {
            throw new SyntaxException("Syntax Error in OpNode", t);
        }

        return OpNode.parseTail(l, tokens);
    }

    public static OpNode parseTail(ExprNode l, ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax Error in OpNode_Tail");
        }
        Token o = tokens.get(0);

        if (o.getTokenType() != TokenType.MATH_OP && 
                o.getTokenType() != TokenType.REL_OP) {
            throw new SyntaxException("Syntax Error in OpNode_Tail", o);
        }
        tokens.remove(0);
        ExprNode r = ExprNode.parse(tokens);
        return new OpNode(o, l, r);
    }
}
