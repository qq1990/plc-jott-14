package src.parser;
import src.provided.JottTree;
import src.provided.Token;
import src.provided.TokenType;
import java.util.ArrayList;

// Quan
public interface BodyStmtNode extends JottTree {
    Type getRetType();
    boolean isReturnable();
    public static BodyStmtNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        if (tokens.size() == 0) {
            return null;
        }
        Token t = tokens.get(0);
        if (t.getToken().equals("if")) {
            IfNode ifNode = IfNode.parse(tokens);
            if (ifNode == null){
                throw new SyntaxException("Error parsing If node", t);
            }
            return ifNode;
            // return IfNode.parse(tokens);
        } else if (t.getToken().equals("while")) {
            WhileNode whileNode = WhileNode.parse(tokens);
            if (whileNode == null){
                throw new SyntaxException("Error parsing While node", t);
            }
            return whileNode;
            // return WhileNode.parse(tokens);
        }
        else if (t.getToken().equals("return")) {
            return null;
        }
        else if (t.getTokenType() == TokenType.FC_HEADER){
            CallNode funcCall = CallNode.parse(tokens);
            ///
            if (funcCall == null){
                throw new SyntaxException("Error parsing Func call", t);
            }
            ///
            if (tokens.get(0).getTokenType() == TokenType.SEMICOLON){
                tokens.remove(0);
                return funcCall;
            }
            else{
                throw new SyntaxException("Missing semicolon in function call", tokens.get(0));
            }
        }
        else if (t.getTokenType() == TokenType.ID_KEYWORD) {
            AsmtNode asmtNode = AsmtNode.parse(tokens);
            if (asmtNode == null){
                throw new SyntaxException("Error parsing Asmt Node", t);
            }
            return asmtNode;
            // return AsmtNode.parse(tokens);
        }
        else if ((t.getToken().equals("Double")
            || t.getToken().equals("Integer")
            || t.getToken().equals("String")
            || t.getToken().equals("Boolean"))){
            // Check for function calls or assignments
            if (tokens.size() >= 3 && tokens.get(2).getTokenType() == TokenType.SEMICOLON) {
                VarDecNode varDecNode = VarDecNode.parse(tokens);
                if(varDecNode == null){
                    throw new SyntaxException("Error parsing Var Dec", t);
                }
                return varDecNode;
                // return VarDecNode.parse(tokens);
            } else {
                AsmtNode asmtNode = AsmtNode.parse(tokens);
                if (asmtNode == null){
                    throw new SyntaxException("Error parsing Asmt Node", t);
                }
                return asmtNode;
                // return AsmtNode.parse(tokens);
            }
        }
        else{
            return null;
        }
    }

    
}
