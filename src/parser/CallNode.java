package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

public class CallNode implements ExprNode, BodyStmtNode {
    private IdNode func_name;
    private ParamsNode params;

    public CallNode(IdNode name, ParamsNode pars) {
        func_name = name;
        params = pars;
    }

    @Override
    public String convertToJott() {
        return "::"+func_name.convertToJott()+"["+params.convertToJott()+"]";
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
    
    public static CallNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.FC_HEADER) {
            throw new SyntaxException("Syntax Error in CallNode");
        }
        tokens.remove(0);
        IdNode name = IdNode.parse(tokens);
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in CallNode");
        }
        tokens.remove(0);
        ParamsNode pars = ParamsNode.parse(tokens);
        if (tokens.size() == 0 || tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in CallNode");
        }
        tokens.remove(0);
        return new CallNode(name, pars);
    }
}
