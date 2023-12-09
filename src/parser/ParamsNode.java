package src.parser;

import java.util.ArrayList;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

// Thomas Ehlers
public class ParamsNode implements JottTree {
    private ArrayList<ExprNode> params;
    private Type[] types;

    public ParamsNode(ArrayList<ExprNode> pars) {
        params = pars;
        types = null;
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
        String out = "";
        if (params.size() > 0) {
            out = params.get(0).convertToJava(className);
            for (int i=1; i<params.size(); i++) {
                out += ","+params.get(i).convertToJava(className);
            }
        }
        return out;
    }

    @Override
    public String convertToC() {
        String out = "";
        if (params.size() > 0) {
            out = params.get(0).convertToC();
            for (int i=1; i<params.size(); i++) {
                out += ","+params.get(i).convertToC();
            }
        }
        return out;
    }

    @Override
    public String convertToPython(int depth) {
        String out = "";
        if (params.size() > 0) {
            out = params.get(0).convertToPython(depth);
            for (int i=1; i<params.size(); i++) {
                out += ","+params.get(i).convertToPython(depth);
            }
        }
        return out;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        for (ExprNode p : params) {
            p.validateTree();
        }
        types = new Type[params.size()];
        for (int i=0; i<params.size(); i++) {
            types[i] = params.get(i).getType();
        }
        return true;
    }

    public Type[] getTypes() {
        return types;
    }
    
    public static ParamsNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<ExprNode> pars = new ArrayList<ExprNode>();
        if (tokens.size() != 0 && tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            ExprNode p = ExprNode.parse(tokens);
            pars.add(p);
            while (tokens.size() != 0 && tokens.get(0).getTokenType() == TokenType.COMMA) {
                Token t = tokens.remove(0);
                if (tokens.size() == 0) { throw new SyntaxException("Syntax Error in ParamsNode, ran out of tokens", t); }
                p = ExprNode.parse(tokens);
                pars.add(p);
            }
        }
        return new ParamsNode(pars);
    }
}
