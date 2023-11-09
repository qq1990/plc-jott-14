package src.parser;

import java.util.ArrayList;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import src.parser.SemanticException;

// Thomas Ehlers
public class ParamsNode implements JottTree {
    private ArrayList<ExprNode> params;

    public ParamsNode(ArrayList<ExprNode> pars) {
        params = pars;
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (params.size() > 0) {
            out = params.get(0).convertToJott();
            for (int i=1; i<params.size(); i++) {
                out += ","+params.get(i).convertToJott();
            }
        }
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
    public boolean validateTree() throws SemanticException {
        for (ExprNode p : params) {
            if (!p.validateTree()) {
                return false;
            }
        }
        return true;
    }

    public Type[] getTypes() {
        Type[] types = new Type[params.size()];
        for (int i=0; i<params.size(); i++) {
            types[i] = params.get(i).getType();
        }
        return types;
    }
    
    public static ParamsNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<ExprNode> pars = new ArrayList<ExprNode>();
        if (tokens.size() != 0) {
            ExprNode p = null;
            try {
                p = ExprNode.parse(tokens);
            } catch (SyntaxException e) {}
            if (p != null) {
                pars.add(p);
                while (tokens.size() != 0 && tokens.get(0).getTokenType() == TokenType.COMMA) {
                    tokens.remove(0);
                    p = ExprNode.parse(tokens);
                    if (p == null) {
                        throw new SyntaxException("Syntax Error in ParamsNode", tokens.get(0));
                    }
                    pars.add(p);
                }
            }
        }
        return new ParamsNode(pars);
    }
}
