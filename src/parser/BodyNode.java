package src.parser;

import java.util.ArrayList;
import java.util.Collections;

import src.provided.JottTree;
import src.provided.Token;
// import src.provided.TokenType;

// Quan
public class BodyNode implements JottTree {
    private ArrayList<BodyStmtNode> bodyStmts;
    private ReturnStmtNode returnStmt;

    public BodyNode(ArrayList<BodyStmtNode> bodyStmts, ReturnStmtNode returnStmt) {
        this.bodyStmts = bodyStmts;
        this.returnStmt = returnStmt;
    }

    @Override
    public String convertToJott() {
        StringBuilder jottCode = new StringBuilder();
        for (BodyStmtNode stmt : bodyStmts) {
            jottCode.append(stmt.convertToJott());
            if (stmt instanceof CallNode) {
                jottCode.append(";\n");
            }
        }
        if (returnStmt != null) {
            jottCode.append(returnStmt.convertToJott());
            jottCode.append("\n");
        }
        return jottCode.toString();
    }

    @Override
    public String convertToJava(String className) {
        

        StringBuilder javaCode = new StringBuilder();

        // Convert body statements to Java
        for (BodyStmtNode bodyStmt : bodyStmts) {
            javaCode.append(bodyStmt.convertToJava(className));
            if (bodyStmt instanceof CallNode) {
                javaCode.append(";");
            }
            javaCode.append("\n");
        }

        // Convert return statement to Java
        if (returnStmt != null) {
            javaCode.append(returnStmt.convertToJava(className));
            javaCode.append("\n");
        }

        return javaCode.toString();
    }

    @Override
    public String convertToC() {
        StringBuilder cCode = new StringBuilder();

        // Convert body statements to C
        for (BodyStmtNode bodyStmt : bodyStmts) {
            cCode.append(bodyStmt.convertToC());
            if (bodyStmt instanceof CallNode) {
                cCode.append(";");
            }
            cCode.append("\n");
        }

        // Convert return statement to C
        if (returnStmt != null) {
            cCode.append(returnStmt.convertToC());
            cCode.append("\n");
        }

        return cCode.toString();
    }


    @Override
    public String convertToPython(int indentLevel) {
        StringBuilder pythonCode = new StringBuilder();
  
        String indent = String.join("", Collections.nCopies(indentLevel, "\t"));

        for (BodyStmtNode stmt : bodyStmts) {
            pythonCode.append(indent);
            pythonCode.append(stmt.convertToPython(indentLevel));
            if (stmt instanceof CallNode) {
                pythonCode.append("\n");
            }
        }
        if (returnStmt != null) {
            pythonCode.append(indent);
            pythonCode.append(returnStmt.convertToPython(indentLevel));
            pythonCode.append("\n");
        }
        return pythonCode.toString();
    }

    @Override
    public boolean validateTree() throws SemanticException {
        Type type = null;
        for (BodyStmtNode bodyStmt : bodyStmts) {
            bodyStmt.validateTree();
            if (bodyStmt.getRetType() != null) {
                if (type == null) {
                    type = bodyStmt.getRetType();
                } else if (type != bodyStmt.getRetType()) {
                    throw new SemanticException("Semantic Error in BodyNode, trying to return multiple types", bodyStmt.getToken());
                }
            }
        }

        if (returnStmt != null) {
            returnStmt.validateTree();
            if (type != null && type != returnStmt.getRetType()) {
                throw new SemanticException("Semantic Error in BodyNode, trying to return multiple types", returnStmt.getToken());
            }
        }
        return true;
    }

    public Type getRetType() {
        // If one of the body statements is returnable, pass the type of the body statement.
        for (BodyStmtNode bodyStmt : bodyStmts) {
            if (bodyStmt.isReturnable()) {
                return bodyStmt.getRetType();
            }
        }

        // If no returnable body statement is found, ask the return node.
        if (returnStmt != null) {
            return returnStmt.getRetType();
        }

        // If there's no returnable body statement or return node, return null.
        return null;
    }

    public boolean isReturnable() {
        // True if one of the body statements is returnable
        for (BodyStmtNode bodyStmt : bodyStmts) {
            if (bodyStmt.isReturnable()) {
                return true;
            }
        }

        // Return node is returnable
        if (returnStmt != null) {
            return true;
        }

        return false;
    }

    public Token getToken() {
        if (returnStmt != null) {
            return returnStmt.getToken();
        }
        return null;
    }

    public static BodyNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        ArrayList<BodyStmtNode> bodyStmts = new ArrayList<>();
        ReturnStmtNode returnStmt = null;

        // Parse body statements
        while (!tokens.isEmpty()) {
            BodyStmtNode bodyStmt = BodyStmtNode.parse(tokens);
            if (bodyStmt != null) {
                bodyStmts.add(bodyStmt);
            } else {
                break;
            }
        }

        returnStmt = ReturnStmtNode.parse(tokens);
        return new BodyNode(bodyStmts, returnStmt);
    }
}
