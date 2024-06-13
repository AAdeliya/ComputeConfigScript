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

    public ASTNode parse() {
        return expression();


    }

    private ASTNode expression() {

        ASTNode node = term();
        while (currentToken != null && (currentToken.type == Token.Type.PLUS || currentToken.type == Token.Type.MINUS)) {
            Token token = currentToken;
            consume(currentToken.type);
            node = new BinaryOpNode(node, term(), token);

        }//while loop better than recursion
        return node;
    }

    private ASTNode term() {
        ASTNode node = factor();
        while (currentToken != null && (currentToken.type == Token.Type.MULTIPLY || currentToken.type == Token.Type.DIVIDE)) {
            Token token = currentToken;
            consume(currentToken.type);
            node = new BinaryOpNode(node, factor(), token);

        }
        return node;

    }


    private void consume(Token.Type type) {
        //we know this is a token parse it along, let's go to the next one, verify if it is multiply and go to the next
        //we want to consume multiple and divide if not we will get an error
        if (currentToken.type == type) {
            currentPos++;
            if (currentPos < tokens.size()) {
                currentToken = tokens.get(currentPos);

            } else {
                currentToken = null; //we reached the end
            }
        } else {
            throw new ParserException("Unexpected token: " + type);
        }
    }

    private ASTNode factor() {
        Token token = currentToken;


        if (token.type == Token.Type.NUMBER) {


            consume(Token.Type.NUMBER);
            return new NumberNode(token);


        }
        if (token.type == Token.Type.LPAREN) {
            consume(Token.Type.LPAREN);
            ASTNode node= expression();
            consume(Token.Type.RPAREN);
            return node;


        }
        throw new ParserException("Unexpected token found for FACTOR: " + token);
    }
}


