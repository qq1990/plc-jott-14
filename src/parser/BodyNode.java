package src.parser;

import java.util.ArrayList;
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
                jottCode.append(";");
            }
        }
        if (returnStmt != null) {
            jottCode.append(returnStmt.convertToJott());
        }
        return jottCode.toString();
    }

    @Override
    public String convertToJava(String className) {
        // TODO: Implement Java code generation for BodyNode
        // You'll need to convert the body statements and return statement to Java code.
        // Make sure to handle both cases where the body statements are present or not.
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToC() {
        // TODO: Implement C code generation for BodyNode
        // You'll need to convert the body statements and return statement to C code.
        // Make sure to handle both cases where the body statements are present or not.
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    @Override
    public String convertToPython() {
        // TODO: Implement Python code generation for BodyNode
        // You'll need to convert the body statements and return statement to Python code.
        // Make sure to handle both cases where the body statements are present or not.
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
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
