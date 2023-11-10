package src.parser;

import java.util.ArrayList;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

// Donald Burke
public class FuncParamsNode implements JottTree {
    ArrayList<IdNode> paramNames;
    ArrayList<Type> paramTypes;

    public FuncParamsNode(ArrayList<IdNode> id, ArrayList<Type> types) {
        this.paramNames = id;
        this.paramTypes = types;
    }

    @Override
    public String convertToJott() {
        String out = "";
        if (this.paramNames.size() > 0) {
            out += this.paramNames.get(0).convertToJott() + ":" + this.paramTypes.get(0).name();
            for (int i = 1; i < this.paramNames.size(); i++) {
                out += "," + this.paramNames.get(i).convertToJott() + ":" + this.paramTypes.get(i).name();
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
        for (IdNode name : this.paramNames) {
            String nameStr = name.getName();
            if (FuncNode.varTable.containsKey(nameStr))
                throw new SemanticException("Semantic Error in FuncParamsNode, param name already used.", name.getToken());
        }
        return true;
    }

    public static FuncParamsNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<IdNode> params = new ArrayList<IdNode>();
        ArrayList<Type> pTypes = new ArrayList<Type>();
        if (tokens.size() > 0 && tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            IdNode param = null;
            Token t = null;

            param = IdNode.parse(tokens);
            params.add(param);

            if (tokens.get(0).getTokenType() != TokenType.COLON)
                throw new SyntaxException("Syntax error in FuncParamsNode", tokens.get(0));
            tokens.remove(0);

            t = tokens.get(0);
            Type paramtype = null;

            switch (t.getToken()) {
                case "Double":
                    paramtype = Type.Double;
                    break;
                case "Integer":
                    paramtype = Type.Integer;
                    break;
                case "String":
                    paramtype = Type.String;
                    break;
                case "Boolean":
                    paramtype = Type.Boolean;
                    break;
                default:
                    throw new SyntaxException("Syntax Error in FuncParamsNode, invalid type keyword", t);
            }
            tokens.remove(0);
            pTypes.add(paramtype);

            while (tokens.size() != 0 && tokens.get(0).getTokenType() == TokenType.COMMA) {
                tokens.remove(0); // eat comma
                // repeat the stuff above the while loop
                param = IdNode.parse(tokens);
                params.add(param);

                if (tokens.get(0).getTokenType() != TokenType.COLON)
                    throw new SyntaxException("Syntax error in FuncParamsNode", tokens.get(0));
                tokens.remove(0);

                t = tokens.get(0);
                paramtype = null;

                switch (t.getToken()) {
                case "Double":
                    paramtype = Type.Double;
                    break;
                case "Integer":
                    paramtype = Type.Integer;
                    break;
                case "String":
                    paramtype = Type.String;
                    break;
                case "Boolean":
                    paramtype = Type.Boolean;
                    break;
                default:
                    throw new SyntaxException("Syntax Error in FuncParamsNode, invalid type keyword", t);
                }
                pTypes.add(paramtype);
                tokens.remove(0);
            }
        }

        return new FuncParamsNode(params, pTypes);
    }
    
}
