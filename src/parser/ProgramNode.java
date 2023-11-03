package src.parser;

import java.util.ArrayList;
import java.util.HashMap;

import src.provided.JottTree;
import src.provided.Token;
// import src.provided.TokenType;

// Clarke Kennedy
public class ProgramNode implements JottTree {

    private ArrayList<FuncNode> funcDefNodes;
    public static HashMap<String, Type[]> defTable = new HashMap<>();
    
    public ProgramNode(ArrayList<FuncNode> funcDefNodes) {
        this.funcDefNodes = funcDefNodes;
    }

    @Override
    public String convertToJott() {
        String s = "";

        for(FuncNode node : funcDefNodes) {
            s = s.concat(node.convertToJott());
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
        throw new UnsupportedOperationException("Parse the tree to validate");
    }

    public static ProgramNode parse(ArrayList<Token> tokens) throws SyntaxException {
        defTable.put("print", new Type[] {Type.Any, Type.String});
        defTable.put("concat", new Type[] {Type.String, Type.String, Type.String});

        ArrayList<FuncNode> funcDefNodes = new ArrayList<>();
        while(tokens.size() > 0) {
            if(!tokens.get(0).getToken().equals("def")){
                throw new SyntaxException("Syntax Error in ProgramNode, expected def keyword", tokens.get(0));
            }

            FuncNode node = FuncNode.parse(tokens);

            Type[] types = new Type[node.funcParams.paramTypes.size() + 1];
            for(int i = 0; i < types.length; i++) {
                types[i] = node.funcParams.paramTypes.get(i);
            }
            types[types.length - 1] = node.funcReturnType.type;

            defTable.put(node.funcName.convertToJott(), types);

            funcDefNodes.add(node);
        }

        return new ProgramNode(funcDefNodes);
        
    }
    
}
