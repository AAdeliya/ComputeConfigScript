package org.example;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int currentPos;
    private Token currentToken;


    Parser(List<Token> tokens) {

        this.tokens = tokens;
        currentPos = 0;
        currentToken = tokens.get(currentPos);

    }
    public ASTNode parse(){
        return expression();


    }

    private ASTNode expression() {
        return term();
    }

    private ASTNode term() {
       ASTNode node = factor();
       while (currentToken != null && (currentToken.type == Token.Type.MULTIPLY || currentToken.type ==Token.Type.DIVIDE)){
           Token token = currentToken;
           consume(currentToken.type);
           node = new BinaryOpNode(node, factor(), token);

       }
       return node;

    }

    private void consume(Token.Type type) {
        //we know this is a token parse it along, let's go to the next one, verify if it is multiply and go to the next
        //we want to consume multiple and divide if not we will get an error
        if(currentToken.type ==type){
            currentPos++;
            if(currentPos <tokens.size()){
                currentToken = tokens.get(currentPos);

            }else{
                currentToken= null; //we reached the end
            }
        }else{
            throw  new ParserException("Unexpected token: " + type);
        }
    }

    private ASTNode factor() {
        Token token = currentToken;
        consume(Token.Type.NUMBER);
        return new NumberNode(token);


    }
}


