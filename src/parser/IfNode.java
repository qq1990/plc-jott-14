package src.parser;

import java.util.ArrayList;

import src.provided.Token;
import src.provided.TokenType;
// Andrew Yansick
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

    public Type getRetType() {
        if(body.getRetType() != null) {
            Type type = body.getRetType();
            if(else_node != null && else_node.getRetType() != null) {
                if(else_node.getRetType() == type) {
                    for(int i = 0; i < elseiflist.size(); i++) {
                        if(elseiflist.get(i).getRetType() != type) {
                            return null; // return types don't match
                        }
                    }
                    return type; // checked if, else, and any elif
                }
                return null; // else type != if type
            }
            return type; // else has no ret type
        }

        if(else_node != null && else_node.getRetType() != null) {
            return else_node.getRetType();
        }
        return null;
    }

    @Override
    public boolean validateTree() throws SemanticException {
        if(expr.validateTree() && body.validateTree()) {
            if(expr.getType() == Type.Boolean) {
                for(int i = 0; i < elseiflist.size(); i++) {
                    if(elseiflist.get(i).validateTree() == false) {
                        throw new SemanticException("Semantic Error: Invalid if statement", null);
                    }
                }
                if(else_node.validateTree()) {
                    return true;
                }
            }
        }
        throw new SemanticException("Semantic error: Invalid while statement", null);
    }

    public static IfNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        ArrayList<ElseIfNode> nodes = new ArrayList<>();

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.ID_KEYWORD) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        ExprNode expr = ExprNode.parse(tokens);
        
        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);
        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in if statement", tokens.get(0));
        }

        if (tokens.size() == 0){
            throw new SyntaxException("Syntax Error in IfNode", null);
        }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode", tokens.get(0));
        }
        tokens.remove(0);

        ArrayList<ElseIfNode> elseiflist = new ArrayList<>();
        while(tokens.size() > 0 && tokens.get(0).getToken().equals("elseif")) {
            elseiflist.add(ElseIfNode.parse(tokens));
        }
        ElseNode elsenode;
        if(tokens.size() > 0 && tokens.get(0).getToken().equals("else")) {
            elsenode = ElseNode.parse(tokens);
        } else {
            elsenode = null;
        }

        return new IfNode(expr, body, nodes, elsenode);
        
    }

    @Override
    public boolean isReturnable() {
        if(else_node != null) {
            if(else_node.getBody().getRetType() == null || else_node.getBody().getRetType() == Type.Void) {
                return false;
            }

            else if(body.getRetType() == null || body.getRetType() == Type.Void) {
                return false;
            }

            for(int i = 0; i < elseiflist.size(); i++) {
                if(elseiflist.get(i).getBody().getRetType()== null || elseiflist.get(i).getBody().getRetType() == Type.Void) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
}
