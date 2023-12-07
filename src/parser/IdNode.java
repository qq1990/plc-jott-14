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
        return getName();
    }

    @Override
    public String convertToJava(String className) {
        if (getName().equals("True")) {
            return "true";
        } else if (getName().equals("False")) {
            return "false";
        } else if (getName().equals("elseif")) {
            return "else if";
        } else if (getName().equals("Double")) {
            return "double";
        } else if (getName().equals("Integer")) {
            return "int";
        } else if (getName().equals("Boolean")) {
            return "bool";
        } else if (getName().equals("Void")) {
            return "void";
        }
        return getName();
    }

    @Override
    public String convertToC() {
        if (getName().equals("True")) {
            return "true";
        } else if (getName().equals("False")) {
            return "false";
        } else if (getName().equals("elseif")) {
            return "else if";
        } else if (getName().equals("Double")) {
            return "double";
        } else if (getName().equals("Integer")) {
            return "int";
        } else if (getName().equals("Boolean")) {
            return "bool";
        } else if (getName().equals("Void")) {
            return "void";
        }
        return getName();
    }

    @Override
    public String convertToPython() {
        if (getName().equals("elseif")) {
            return "elif";
        } else if (getName().equals("Double") || getName().equals("Integer") 
                || getName().equals("Boolean") || getName().equals("Void") || getName().equals("String")) {
            return "";
        }
        return getName();
    }

    @Override
    public boolean validateTree() throws SemanticException {
        // Assume you are using the variable in ExprNode
        if (getName().equals("True") || getName().equals("False")) { return true; }
        if (!FuncNode.varTable.containsKey(getName())) { throw new SemanticException("Semantic Exception in IdNode, invalid id/keyword", id_name); }
        if (!FuncNode.varTable.get(getName()).initialized) { throw new SemanticException("Semantic Exception in IdNode, uninitialized id/keyword", id_name); }
        return true;
        /* Functionality needs to be implemented in their respective classes, listed below:
        Used on left side of VarDecNode - !FuncNode.varTable.containsKey && validateName (!"True" && !"False")
        Used on left side of AsmtNode - 
            +Dec/Init- !FuncNode.varTable.containsKey && validateName (!"True" && !"False")
            +Update- FuncNode.varTable.containsKey (validateName (!"True" && !"False"))
        Used in FuncDefNode - !ProgramNode.defTable.containsKey && startsW/LowerCase
        Used in FuncParams - !FuncNode.varTable.containsKey && validateName (!"True" && !"False")
        Used in CallNode - ProgramNode.defTable.containsKey (!"True" && !"False")
        Used in ExprNode - "True" || "False" || FuncNode.varTable.containsKey()
        */
    }

    public boolean validateName() throws SemanticException {
        if (getName().equals("True") || getName().equals("False") 
                || getName().equals("def") || getName().equals("return")
                || getName().equals("while") || getName().equals("if") 
                || getName().equals("elseif") || getName().equals("else")
                || getName().equals("Double") || getName().equals("Integer") 
                || getName().equals("String") || getName().equals("Boolean") || getName().equals("Void") ) {
            throw new SemanticException("Syntax Error in IdNode, id/keyword cannot use reserved keywords: "+id_name.getToken(), id_name);
        }

        if (getName().charAt(0) != getName().toLowerCase().charAt(0)) {
            throw new SemanticException("Syntax Error in IdNode, id/keyword must start with lowercase letter: "+id_name.getToken(), id_name);
        }
        return true;
    }

    @Override
    public Type getType() {
        // Assume you are using the variable in ExprNode
        if (getName().equals("True") || getName().equals("False")) {
            return Type.Boolean;
        } else if (FuncNode.varTable.containsKey(getName())) {
            return FuncNode.varTable.get(getName()).type;
        }
        return null;
    }

    public Token getToken() {
        return id_name;
    }

    public String getName() {
        return id_name.getToken();
    }
    
    public static IdNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            return null;
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in IdNode, not an id/keyword", tokens.get(0));
        }
        return new IdNode(tokens.remove(0));
    }
}
