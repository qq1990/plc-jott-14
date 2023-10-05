package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static OpNode parse(ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in OpNode");
            return null;
        }
        Token t = tokens.get(0);

        // Valid (left) operands - (no str/bool compare/equality?)
        ExprNode l;
        if (t.getTokenType() == TokenType.NUMBER) {
            l = NumNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.ID_KEYWORD &&
                !t.getToken().equals("True") && 
                !t.getToken().equals("False")) {
            l = IdNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.FC_HEADER) {
            l = CallNode.parse(tokens);
        } else {
            //throw new SyntaxException()
            System.err.println("Syntax Error in OpNode");
            return null;
        }

        // Optional type-checking
        return OpNode.parseTail(l, tokens);
    }

    public static OpNode parseTail(ExprNode l, ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in OpNode_Tail");
            return null;
        }
        Token o = tokens.get(0);

        // Optional type-checking
        if (o.getTokenType() != TokenType.MATH_OP && 
                o.getTokenType() != TokenType.REL_OP) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in OpNode_Tail");
            return null;
        }
        tokens.remove(0);
        // Pre-check or Handle error? Optional type-checking
        ExprNode r = ExprNode.parse(tokens);
        if (r == null) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in OpNode_Tail");
            return null;
        }
        return new OpNode(o, l, r);
    }
}
