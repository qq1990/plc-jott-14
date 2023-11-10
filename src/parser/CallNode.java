package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Thomas Ehlers
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
    public boolean validateTree() throws SemanticException {
        if (ProgramNode.defTable.containsKey(func_name.getName())) {
            if (!params.validateTree()) { throw new SemanticException("Semantic Exception in CallNode, invalid parameters"); }
            Type[] pTypes = ProgramNode.defTable.get(func_name.getName());
            Type[] aTypes = params.getTypes();
            if (aTypes.length == pTypes.length-1) {
                for (int i=0; i<aTypes.length; i++) {
                    if (aTypes[i] != pTypes[i]) {
                        throw new SemanticException("Semantic Exception in CallNode, parameter type mismatch");
                    }
                }
                return true;
            }
            throw new SemanticException("Semantic Exception in CallNode, parameter number mismatch");
        }
        throw new SemanticException("Semantic Exception in CallNode, could not find function");
    }

    @Override
    public Type getType() {
        if (ProgramNode.defTable.containsKey(func_name.getName())) {
            Type[] t = ProgramNode.defTable.get(func_name.getName());
            return t[t.length-1];
        }
        return null;
    }

    @Override
    public Type getRetType() {
        return null;
    }

    @Override
    public boolean isReturnable() {
        return false;
    }
    
    public static CallNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0){
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.FC_HEADER) {
            throw new SyntaxException("Syntax Error in CallNode", tokens.get(0));
        }
        tokens.remove(0);
        IdNode name = IdNode.parse(tokens);
        if (tokens.size() == 0){
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in CallNode", tokens.get(0));
        }
        tokens.remove(0);
        ParamsNode pars = ParamsNode.parse(tokens);
        if (tokens.size() == 0){
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in CallNode", tokens.get(0));
        }
        tokens.remove(0);
        return new CallNode(name, pars);
    }
}
