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
    public boolean validateTree() throws SemanticException {
        //if (right.getClass() == OpNode.class) { throw new SemanticException("Semantic Error in OpNode, cannot chain operations"); }
        if (!left.validateTree()) { throw new SemanticException("Semantic Error in OpNode, invalid left child"); }
        if (!right.validateTree()) { throw new SemanticException("Semantic Error in OpNode, invalid right child"); }
        if (left.getType() == null) { throw new SemanticException("Semantic Error in OpNode, invalidly typed left operand"); }
        if (right.getType() == null) { throw new SemanticException("Semantic Error in OpNode, invalidly typed right operand"); }
        if (!(left.getType() == Type.Integer || left.getType() == Type.Double)) { throw new SemanticException("Semantic Error in OpNode, left operand has invalid type for operation"); }
        if (!(right.getType() == Type.Integer || right.getType() == Type.Double)) { throw new SemanticException("Semantic Error in OpNode, right operand has invalid type for operation"); }
        if (left.getType() != right.getType()) { throw new SemanticException("Semantic Error in OpNode, type mismatch"); }
        if (op.getToken().equals("/") && ((NumNode) right).isZero()) { throw new SemanticException("Semantic Error in OpNode, divide by 0"); }
        return true;
    }

    @Override
    public Type getType() {
        String o = op.getToken();
        if (left.getType() != null && left.getType() == right.getType()) {
            if (o.equals("+") || o.equals("-") || o.equals("*") || o.equals("/") || o.equals("^")) {
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
        if (r.getClass() == OpNode.class) { throw new SyntaxException("Syntax Error in OpNode, cannot chain operations"); }
        return new OpNode(o, l, r);
    }
}
