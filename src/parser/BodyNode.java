package src.parser;

import java.util.ArrayList;
import src.provided.JottTree;
import src.provided.Token;
// import src.provided.TokenType;

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
    public boolean validateTree() {
        // TODO: Implement tree validation for BodyNode
        // You can check if the body statements and return statement are valid here.
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }

    public static BodyNode parse(ArrayList<Token> tokens) throws SyntaxException {
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
