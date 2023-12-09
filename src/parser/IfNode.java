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
        String s = "if ["+expr.convertToJott()+"] {\n"+body.convertToJott()+"}";

        for(ElseIfNode node : elseiflist) {
            s = s.concat(node.convertToJott());
        }

        if(else_node != null) s = s.concat(else_node.convertToJott());

        return s;
        
    }

    @Override
    public String convertToJava(String className) {
        String str = "if (" + expr.convertToJava(className) + ") {\n" + 
        body.convertToJava(className) + "}";
        
        for(int i = 0; i < elseiflist.size(); i++) {
            str = str + elseiflist.get(i).convertToJava(className);
        }
        if(else_node != null) {
            str = str + else_node.convertToJava(className);
        }
        
        return str;
    }

    @Override
    public String convertToC() {
        String str = "if (" + expr.convertToC() + ") {\n" + 
        body.convertToC() + "}";

        for(int i = 0; i < elseiflist.size(); i++) {
            str = str + elseiflist.get(i).convertToC();
        }
        if(else_node != null) {
            str = str + else_node.convertToC();
        }

        return str;
    }

    @Override
    public String convertToPython(int depth) {
        String str = "if " + expr.convertToPython(depth) + ":\n";
        str = str + body.convertToPython(depth+1);
        for(int i = 0; i < elseiflist.size(); i++) {
            for(int j = 0; j < depth; j++) {
                str = str + "\t";
            }
            str = str + elseiflist.get(i).convertToPython(depth);
        }
        if(else_node != null) {
            for(int j = 0; j < depth; j++) {
                str = str + "\t";
            }
            str = str + else_node.convertToC();
        }

        return str.substring(0,str.length()-1);
    }

    public Type getRetType() {
        if(body.getRetType() != null) { 
            Type type = body.getRetType();
            for(int i = 0; i < elseiflist.size(); i++) {
                if(elseiflist.get(i).getRetType() != type && elseiflist.get(i).getRetType() != null) {
                    return null; // return types don't match
                }
            }
            if(else_node != null && else_node.getRetType() != null) {
                if(else_node.getRetType() == type) {
                    return type; // checked if, else, and any elif
                }
                return null; // else type != if type
            }
            return type; // else has no ret type
        }
        if(elseiflist.size() > 0) {
            Type check = elseiflist.get(0).getRetType();
            for(int i = 1; i < elseiflist.size(); i++) {
                if(elseiflist.get(i).getRetType() != check) {
                    return null; // return types don't match
                }
            }
            if(else_node != null && else_node.getRetType() != null) {
                if(else_node.getRetType() != check) {
                    return null; // return types don't match between elif and else
                }
            }
            return check; // checked if, else, and any elif
        }
        if(else_node != null && else_node.getRetType() != null) {
            return else_node.getRetType(); // if had no return type but else does
        }
        return null; // no return type
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
                if(else_node != null) {
                    else_node.validateTree();
                }
            }
        }
        // throw new SemanticException("Semantic error: Invalid while statement", null);
        return true;
    }

    public static IfNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0) {
            return null;
        }
        
        ArrayList<ElseIfNode> nodes = new ArrayList<>();
        if (!tokens.get(0).getToken().equals("if")) {
            throw new SyntaxException("Syntax Error in IfNode, expected if keyword.", tokens.get(0));
        }
        Token t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in IfNode, ran out of tokens before '['.", t); }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode, expected '['.", tokens.get(0));
        }
        t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in IfNode, ran out of tokens before condition.", t); }
        ExprNode expr = ExprNode.parse(tokens);
        if(expr == null) {
            throw new SyntaxException("Syntax Error in IfNode, ran out of tokens", t);
        }
        
        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in IfNode, ran out of tokens before ']'.", t); }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACKET) {
            throw new SyntaxException("Syntax Error in IfNode, expected ']'.", tokens.get(0));
        }
        t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in IfNode, ran out of tokens before '{'.", t); }
        if (tokens.get(0).getTokenType() != TokenType.L_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode, expected '{'.", tokens.get(0));
        }
        t = tokens.remove(0);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in IfNode, ran out of tokens before body.", t); }
        int x = FuncNode.varTable.size();
        BodyNode body = BodyNode.parse(tokens);

        if (tokens.size() == 0){ throw new SyntaxException("Syntax Error in IfNode, ran out of tokens before '}'.", t); }
        if (tokens.get(0).getTokenType() != TokenType.R_BRACE) {
            throw new SyntaxException("Syntax Error in IfNode, expected '}'.", tokens.get(0));
        }
        tokens.remove(0);

        if(FuncNode.varTable.size() > x) {
            throw new SemanticException("Semantic error: New variable declared in if statement", t);
        }

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
            if(else_node.getRetType() == null || else_node.getRetType() == Type.Void) {
                return false;
            }

            else if(body.getRetType() == null || body.getRetType() == Type.Void) {
                return false;
            }

            for(int i = 0; i < elseiflist.size(); i++) {
                if(elseiflist.get(i).getRetType()== null || elseiflist.get(i).getRetType() == Type.Void) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Token getToken() {
        return body.getToken();
    }
    
}
