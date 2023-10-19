package src.parser;

import java.util.ArrayList;

import src.provided.JottTokenizer;
import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

// Thomas Ehlers
public interface ExprNode extends JottTree {
    public static ExprNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax Error in ExprNode");
        }
        Token t = tokens.get(0);

        // Terminal Literals - str and bool (I don't like bool)
        if (t.getTokenType() == TokenType.STRING) {
            return StrNode.parse(tokens);
        } else if (t.getToken().equals("True") || t.getToken().equals("False")) {
            return IdNode.parse(tokens);
        }
        
        // Possible (left) operands - num, id, call (we could start type-checking)
        ExprNode l;
        if (t.getTokenType() == TokenType.NUMBER 
            || t.getToken().equals("-") || t.getToken().equals("+")) {
            l = NumNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.ID_KEYWORD) {
            l = IdNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.FC_HEADER) {
            l = CallNode.parse(tokens);
        } else {
            throw new SyntaxException("Syntax Error in ExprNode");
        }
        
        // Operations - check/try (we could start start type-checking)
        if (tokens.size() > 0 &&
                (tokens.get(0).getTokenType() == TokenType.MATH_OP || 
                tokens.get(0).getTokenType() == TokenType.REL_OP)) {
            return OpNode.parseTail(l, tokens);
        }
        return l;
    }

    public static void main(String[] args) {
        ArrayList<Token> tokens = JottTokenizer.tokenize("parserTestCases/custom_test.jott");
        printTokens(tokens);
        System.out.println("\n============================\n");
        try {
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ParamsNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
            System.out.println(ExprNode.parse(tokens).convertToJott());
        } catch(SyntaxException e) {
            System.err.println(e);
        }
        System.out.println("\n============================\n");
        printTokens(tokens);
    }

    public static void printTokens(ArrayList<Token> tokens) {
        for (Token t : tokens) {
            System.out.println(t.getLineNum()+": \'"+t.getToken()+
            "\' ["+(int) t.getToken().charAt(0)+"] ("+t.getTokenType()+")");
        }
    }
}
