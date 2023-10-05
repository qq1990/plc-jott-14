package src.parser;

import java.util.ArrayList;
import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

public interface ExprNode extends JottTree {
    public static ExprNode parse(ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            //throw new SyntaxException()
            System.err.println("Syntax Error in ExprNode");
            return null;
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
        if (t.getTokenType() == TokenType.NUMBER) {
            l = NumNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.ID_KEYWORD) {
            l = IdNode.parse(tokens);
        } else if (t.getTokenType() == TokenType.FC_HEADER) {
            l = CallNode.parse(tokens);
        } else {
            //throw new SyntaxException()
            System.err.println("Syntax Error in ExprNode");
            return null;
        }
        
        // Operations - check/try (we could start start type-checking)
        if (tokens.size() > 0 &&
                (tokens.get(0).getTokenType() == TokenType.MATH_OP || 
                tokens.get(0).getTokenType() == TokenType.REL_OP)) {
            return OpNode.parseTail(l, tokens);
        }
        return l;
    }
}
