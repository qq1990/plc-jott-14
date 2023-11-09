package src.parser;
import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Quan
public interface BodyStmtNode extends JottTree {
    Type getRetType();
    public static BodyStmtNode parse(ArrayList<Token> tokens) throws SyntaxException {
        if (tokens.size() == 0) {
            throw new SyntaxException("Syntax Error in BodyStmtNode");
        }
        Token t = tokens.get(0);
        if (t.getToken().equals("if")) {
            return IfNode.parse(tokens);
        } else if (t.getToken().equals("while")) {
            return WhileNode.parse(tokens);
        }
        else if (t.getToken().equals("return")) {
            return null;
        }
        else if (t.getTokenType() == TokenType.FC_HEADER){
            CallNode funcCall = CallNode.parse(tokens);
            if (tokens.get(0).getTokenType() == TokenType.SEMICOLON){
                tokens.remove(0);
                return funcCall;
            }
            else{
                throw new SyntaxException("Error parsing function call");
            }
        }
        else if (t.getTokenType() == TokenType.ID_KEYWORD) {
            return AsmtNode.parse(tokens);
            
        }
        else if ((t.getToken().equals("Double")
            || t.getToken().equals("Integer")
            || t.getToken().equals("String")
            || t.getToken().equals("Boolean"))){
            // Check for function calls or assignments
            if (tokens.size() >= 3 && tokens.get(2).getTokenType() == TokenType.SEMICOLON) {
                return VarDecNode.parse(tokens);
            } else {
                return AsmtNode.parse(tokens);
            }
        }
        else{
            return null;
        }
    }

    
}
