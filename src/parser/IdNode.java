package src.parser;

import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Thomas Ehlers
public class IdNode implements ExprNode {
    private Token id_name;

    public IdNode(Token name) {
        id_name = name;
    }

    @Override
    public String convertToJott() {
        return id_name.getToken();
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
        return true;
        /* Functionality needs to be implemented in their respective classes, listed below:
        Used on left side of VarDecNode - !"True" && !"False" && !FuncNode.varTable.containsKey()
        Used on left side of AsmtNode - !"True" && !"False" && FuncNode.varTable.containsKey()
        Used in FuncDefNode - !"True" && !"False" && !ProgramNode.defTable.containsKey()
        Used in FuncParams - !"True" && !"False" && !FuncNode.varTable.containsKey()
        Used in CallNode - (!"True" && !"False") && ProgramNode.defTable.containsKey()
        Used in ParamsNode - "True" || "False" || FuncNode.varTable.containsKey()
        Used in ExprNode - "True" || "False" || FuncNode.varTable.containsKey()
        */
    }

    @Override
    public Type getType() {
        // Assume you are using the variable in ParamsNode/ExprNode
        if (getName().equals("True") || getName().equals("False")) {
            return Type.Boolean;
        } else if (FuncNode.varTable.containsKey(getName())) {
            return FuncNode.varTable.get(getName());
        }
        return null;
    }

    public String getName() {
        return id_name.getToken();
    }
    
    public static IdNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IdNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in IdNode", tokens.get(0));
        }
        return new IdNode(tokens.remove(0));
    }
}
