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
        //if (right.getClass() == OpNode.class) { throw new SemanticException("Semantic Error in OpNode, cannot chain operations", op); }
        left.validateTree();
        right.validateTree();
        if (!(left.getType() == Type.Integer || left.getType() == Type.Double)) { throw new SemanticException("Semantic Error in OpNode, left operand has invalid type for operation: "+left.getType().name(), op); }
        if (!(right.getType() == Type.Integer || right.getType() == Type.Double)) { throw new SemanticException("Semantic Error in OpNode, right operand has invalid type for operation: "+right.getType().name(), op); }
        if (left.getType() != right.getType()) { throw new SemanticException("Semantic Error in OpNode, type mismatch: "+left.getType().name()+op.getToken()+right.getType().name(), op); }
        if (op.getToken().equals("/") && ((NumNode) right).isZero()) { throw new SemanticException("Semantic Error in OpNode, divide by 0: "+left.getType().name()+op.getToken()+right.getType().name(), op); }
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
            return null;
        }
        Token t = tokens.get(0);

        // Valid (left) operands - num, id (not bool), call
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
        
        if (tokens.size() == 0) { throw new SyntaxException("Syntax Error in OpNode, ran out of tokens", t); }
        return OpNode.parseTail(l, tokens);
    }

    public static OpNode parseTail(ExprNode l, ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            throw null;
        }
        Token o = tokens.get(0);

        if (o.getTokenType() != TokenType.MATH_OP && 
                o.getTokenType() != TokenType.REL_OP) {
            throw new SyntaxException("Syntax Error in OpNode_Tail, invalid operation", o);
        }
        tokens.remove(0);
        if (tokens.size() == 0) { throw new SyntaxException("Syntax Error in OpNode_Tail, ran out of tokens", o); }
        ExprNode r = ExprNode.parse(tokens);
        if (r.getClass() == OpNode.class) { throw new SyntaxException("Syntax Error in OpNode_Tail, cannot chain operations", o); }
        return new OpNode(o, l, r);
    }
}
