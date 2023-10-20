package src.parser;

import java.util.ArrayList;

import src.provided.Token;
import src.provided.TokenType;

// Andrew
public class IfNode implements BodyStmtNode {

    private ExprNode expr;
    private BodyNode body;
    private ArrayList<ElseIfNode> elseiflist;
    private ElseNode else_node;
    
    public IfNode(ExprNode expr, BodyNode body, ArrayList<ElseIfNode> elseiflist, ElseNode else_node) {
        this.expr = expr;
        this.body = body;
        this.elseiflist = elseiflist;
        this.else_node = else_node;
    }

    @Override
    public String convertToJott() {
        String s = "if["+expr.convertToJott()+"]{"+body.convertToJott()+"}";

        for(ElseIfNode node : elseiflist) {
            s = s.concat(node.convertToJott());
        }

        s = s.concat(else_node.convertToJott());

        return s;
        
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

    public static IfNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<ElseIfNode> nodes = new ArrayList<>();

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        ExprNode expr = ExprNode.parse(tokens);
        
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        BodyNode body = BodyNode.parse(tokens);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode");
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        ArrayList<ElseIfNode> elseiflist = new ArrayList<>();
        while(tokens.size() > 0 && tokens.get(0).getToken().equals("elseif")) {
            elseiflist.add(ElseIfNode.parse(tokens));
        }

        ElseNode elsenode = ElseNode.parse(tokens);

        return new IfNode(expr, body, nodes, elsenode);
        
    }
    
}
