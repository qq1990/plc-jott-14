package src.parser;

import java.util.ArrayList;

import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;

// Donald Burke
public class FuncParamsNode implements JottTree {
    private ArrayList<IdNode> paramNames;
    private ArrayList<Type> paramTypes;

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
                out += ", " + this.paramNames.get(i).convertToJott() + ":" + this.paramTypes.get(i).name();
            }
        }
        return out;
    }

    @Override
    public String convertToJava(String className) {
        String out = "(";
        for (int i = 0; i < this.paramNames.size() - 1; i++) {
            out += this.paramTypes.get(i).toString() + this.paramNames.get(i).convertToJava(className) + ",";
        }
        out += this.paramTypes.get(this.paramNames.size() - 1).toString() 
                + this.paramNames.get(this.paramNames.size() - 1).convertToJava(className) + ")";
        return out;
    }

    @Override
    public String convertToC() {
        String out = "(";
        if (this.paramNames.isEmpty())
            out += "void)";
        else {
            // loop to check all but last argument type and translate
            for (int i = 0; i < this.paramNames.size() - 1; i++) {
                String thisType = this.paramTypes.get(i).name();
                if (thisType.equals("Void"))
                    out += "void " + this.paramNames.get(i).convertToC() + ",";
                else if (thisType.equals("Boolean"))
                    out += "bool " + this.paramNames.get(i).convertToC() + ",";
                else if (thisType.equals("Integer"))
                    out += "int" + this.paramNames.get(i).convertToC() + ",";
                else if (thisType.equals("String"))
                    out += "char[] " + this.paramNames.get(i).convertToC() + ",";
                else if (thisType.equals("Double"))
                    out += "double " + this.paramNames.get(i).convertToC() + ",";
            }
            // check last argument type and translate
            String thisType = this.paramTypes.get(this.paramNames.size() - 1).name();
                if (thisType.equals("Void"))
                    out += "void " + this.paramNames.get(this.paramNames.size() - 1).convertToC() + ")";
                else if (thisType.equals("Boolean"))
                    out += "bool " + this.paramNames.get(this.paramNames.size() - 1).convertToC() + ")";
                else if (thisType.equals("Integer"))
                    out += "int" + this.paramNames.get(this.paramNames.size() - 1).convertToC() + ")";
                else if (thisType.equals("String"))
                    out += "char[] " + this.paramNames.get(this.paramNames.size() - 1).convertToC() + ")";
                else if (thisType.equals("Double"))
                    out += "double " + this.paramNames.get(this.paramNames.size() - 1).convertToC() + ")";
        }
        return out;
    }

    @Override
    public String convertToPython(int depth) {
        String out = "(";
        for (int i = 0; i < this.paramNames.size() - 1; i++) {
            out += this.paramNames.get(i).convertToPython(depth + 1) + ",";
        }
        out += this.paramNames.get(this.paramNames.size() - 1).convertToPython(depth + 1) + ")";
        return out;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        for (int i = 0; i < paramNames.size(); i++) {
            IdNode name = paramNames.get(i);
            name.validateName();
            if (FuncNode.varTable.containsKey(name.getName())) { throw new SemanticException("Semantic Error in FuncParamsNode, param name already used.", name.getToken()); }
            FuncNode.varTable.put(name.getName(), new VarInfo(paramTypes.get(i), true));
        }
        return true;
    }

    public static FuncParamsNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<IdNode> params = new ArrayList<IdNode>();
        ArrayList<Type> pTypes = new ArrayList<Type>();
        if (tokens.size() > 0 && tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            IdNode param = IdNode.parse(tokens);
            params.add(param);
            if (tokens.size() == 0) { throw new SyntaxException("Syntax error in FuncParamsNode, ran out of tokens before ':'.", param.getToken()); }

            if (tokens.get(0).getTokenType() != TokenType.COLON) {
                throw new SyntaxException("Syntax error in FuncParamsNode, expected ':'.", tokens.get(0));
            }
            tokens.remove(0);

            if (tokens.size() == 0) { throw new SyntaxException("Syntax error in FuncParamsNode, ran out of tokens before type.", param.getToken()); }
            Token t = tokens.get(0);
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
                    throw new SyntaxException("Syntax Error in FuncParamsNode, invalid type keyword.", t);
            }
            tokens.remove(0);
            pTypes.add(paramtype);

            while (tokens.size() != 0 && tokens.get(0).getTokenType() == TokenType.COMMA) {
                t = tokens.remove(0); // eat comma
                if (tokens.size() == 0) { throw new SyntaxException("Syntax error in FuncParamsNode, ran out of tokens before parameter name.", t); }
                // repeat the stuff above the while loop
                param = IdNode.parse(tokens);
                params.add(param);
                if (tokens.size() == 0) { throw new SyntaxException("Syntax error in FuncParamsNode, ran out of tokens", param.getToken()); }

                if (tokens.get(0).getTokenType() != TokenType.COLON) {
                    throw new SyntaxException("Syntax error in FuncParamsNode, expected ':'.", tokens.get(0));
                }
                tokens.remove(0);

                if (tokens.size() == 0) { throw new SyntaxException("Syntax error in FuncParamsNode, ran out of tokens before type.", param.getToken()); }
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
                    throw new SyntaxException("Syntax Error in FuncParamsNode, invalid type keyword.", t);
                }
                pTypes.add(paramtype);
                tokens.remove(0);
            }
        }

        return new FuncParamsNode(params, pTypes);
    }
    
    public ArrayList<Type> getParamTypes() {
        return paramTypes;
    }
}
