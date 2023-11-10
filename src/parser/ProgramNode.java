package src.parser;

import java.util.ArrayList;
import java.util.HashMap;

import src.provided.JottTree;
import src.provided.Token;
// import src.provided.TokenType;
import src.provided.TokenType;

// Clarke Kennedy
public class ProgramNode implements JottTree {

    private ArrayList<FuncNode> funcDefNodes;
    // Symbol table for function definitions where key is the function name and
    // value is an arr of the parameters' data types; except for the last value
    // which is the function's return type
    public static HashMap<String, Type[]> defTable = new HashMap<>();
    
    public ProgramNode(ArrayList<FuncNode> funcDefNodes) {
        this.funcDefNodes = funcDefNodes;
    }

    @Override
    public String convertToJott() {
        String s = "";

        for(FuncNode node : funcDefNodes) {
            s = s.concat(node.convertToJott())+"\n\n";
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
    public boolean validateTree() throws SemanticException{
        if(!defTable.containsKey("main")) {
            throw new SemanticException("Semantic Error in ProgramNode, no main function", new Token("", "", 0, TokenType.ID_KEYWORD));
        }
        Type[] mainTypes = defTable.get("main");
        FuncNode mainNode = null;
        for (FuncNode node : funcDefNodes) {
            if (node.funcName.convertToJott().equals("main")) {
                mainNode = node;
            }
        }
        if(mainTypes.length != 1) {
            throw new SemanticException("Semantic Error in ProgramNode, main function must have no parameters", mainNode.funcName.getToken());
        }
        if(mainTypes[mainTypes.length - 1] != Type.Void) {
            throw new SemanticException("Semantic Error in ProgramNode, main function must return void", mainNode.funcName.getToken());
        }
        return true;
    }

    public static ProgramNode parse(ArrayList<Token> tokens) throws SyntaxException, SemanticException {
        defTable.put("print", new Type[] {Type.Any, Type.Void});
        defTable.put("concat", new Type[] {Type.String, Type.String, Type.String});
        defTable.put("length", new Type[] {Type.String, Type.Integer});

        ArrayList<FuncNode> funcDefNodes = new ArrayList<>();
        while(tokens.size() > 0) {
            if(!tokens.get(0).getToken().equals("def")){
                throw new SyntaxException("Syntax Error in ProgramNode, expected def keyword", tokens.get(0));
            }

            FuncNode node = FuncNode.parse(tokens);

            Type[] types = new Type[node.funcParams.paramTypes.size() + 1];
            for(int i = 0; i < types.length - 1; i++) {
                types[i] = node.funcParams.paramTypes.get(i);
            }
            types[types.length - 1] = node.funcReturnType.type;

            defTable.put(node.funcName.getName(), types);

            funcDefNodes.add(node);
        }

        ProgramNode programNode = new ProgramNode(funcDefNodes);
        programNode.validateTree();
        return programNode;
        
    }
    
}
