package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
//        String input = """
//                config "nums_users" = 100
//                config "nums_requests" = 100
//                update "num_users" = 200
//                compute "result" = %num_users + %num_requests
//                """;
        List<Token>  tokens = Lexer.getHardCodedTokens();
        Parser parser = new Parser(tokens);
        ASTNode root = parser.parse();

        root.print("    ");





//        for(Token token : lexer){
//            System.out.println(token);
//        }
//
//        ArrayList<Token> tokens = new ArrayList<>();
//        tokens.add(new Token(Token.Type.CONFIG, "Config"));
//        tokens.add(new Token(Token.Type.STRING, "num_users"));
//        tokens.add(new Token(Token.Type.ASSIGMENT, "="));
//        tokens.add(new Token(Token.Type.NUMBER, "100"));
//
//        for(Token token :tokens){
//            System.out.println(token);
//        }



    }
}