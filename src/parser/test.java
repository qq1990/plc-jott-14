package src.parser;

import src.provided.JottTokenizer;
import src.provided.Token;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<Token> tokens = JottTokenizer.tokenize("parserTestCases/custom_test.jott");
        printTokens(tokens);
        System.out.println("\n============================\n");
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ParamsNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println(ExprNode.parse(tokens).convertToJott());
        System.out.println("\n============================\n");
        printTokens(tokens);
    }

    public static void printTokens(ArrayList<Token> tokens) {
        for (Token t : tokens) {
            System.out.println(t.getLineNum()+": \'"+t.getToken()+
            "\' ["+(int) t.getToken().charAt(0)+"] ("+t.getTokenType()+")");
        }
    }
}
