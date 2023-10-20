package src.parser;

import java.util.ArrayList;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

public class FuncParamsNode implements JottTree {
    ArrayList<IdNode> paramNames;
    ArrayList<Token> paramTypes;

    public FuncParamsNode(ArrayList<IdNode> id, ArrayList<Token> types) {
        this.paramNames = id;
        this.paramTypes = types;
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (this.paramNames.size() > 0) {
            out += this.paramNames.get(0) + ":" + this.paramTypes.get(0);
            for (int i = 1; i < this.paramNames.size(); i++) {
                out += "," + this.paramNames.get(i) + ":" + this.paramTypes.get(i);
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
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static FuncParamsNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<IdNode> params = new ArrayList<IdNode>();
        ArrayList<Token> pTypes = new ArrayList<Token>();
        if (tokens.size() > 0) {
            IdNode param = null;
            Token t = null;

            param = IdNode.parse(tokens);
            params.add(param);

            if (tokens.get(0).getTokenType() != TokenType.COLON)
                throw new SyntaxException("Syntax error in FuncParamsNode");
            tokens.remove(0);

            t = tokens.get(0);
            Token paramtype = null;

            if (t.getToken().equals("Double")
                    || t.getToken().equals("Integer")
                    || t.getToken().equals("String")
                    || t.getToken().equals("Boolean")) {
                paramtype = t;
                tokens.remove(0);
            }
            pTypes.add(paramtype);

            while (tokens.size() != 0 && tokens.get(0).getTokenType() == TokenType.COMMA) {
                tokens.remove(0); // eat comma
                // repeat the stuff above the while loop
                param = IdNode.parse(tokens);
                params.add(param);

                if (tokens.get(0).getTokenType() != TokenType.COLON)
                    throw new SyntaxException("Syntax error in FuncParamsNode");
                tokens.remove(0);

                t = tokens.get(0);
                paramtype = null;

                if (t.getToken().equals("Double")
                        || t.getToken().equals("Integer")
                        || t.getToken().equals("String")
                        || t.getToken().equals("Boolean")) {
                    paramtype = t;
                    tokens.remove(0);
                }
                pTypes.add(paramtype);
            }
        }

        return new FuncParamsNode(params, pTypes);
    }
    
}
