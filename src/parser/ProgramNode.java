package src.parser;

import java.util.ArrayList;

import src.provided.JottTree;
import src.provided.Token;
// import src.provided.TokenType;

// Clarke Kennedy
public class ProgramNode implements JottTree {

    private ArrayList<FuncNode> funcDefNodes;
    
    public ProgramNode(ArrayList<FuncNode> funcDefNodes) {
        this.funcDefNodes = funcDefNodes;
    }

    @Override
    public String convertToJott() {
        String s = "";

        for(FuncNode node : funcDefNodes) {
            s.concat(node.convertToJott());
        }

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

    public static ProgramNode parse(ArrayList<Token> tokens) throws SyntaxException {
        ArrayList<FuncNode> funcDefNodes = new ArrayList<>();
        while(tokens.size() > 0) {
            if(!tokens.get(0).getToken().equals("def")){
                throw new SyntaxException("Syntax Error in ProgramNode", tokens.get(0));
            }
            funcDefNodes.add(FuncNode.parse(tokens));
        }

        return new ProgramNode(funcDefNodes);
        
    }
    
}
