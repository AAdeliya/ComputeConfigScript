package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lexer  implements Iterable<Lexer.Token> {
    private final String input;
    private final List<Token> tokens;
    private int current;



    public Lexer(String input){
        this.input= input;
        this.tokens = new ArrayList<Token>();
        this.current = 0;

        tokenize();


    }
    private void tokenize(){
        while(current< input.length()){
            char ch = input.charAt(current);
            switch(ch){
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    current++;
                    break;
                case '=':
                    tokens.add(new Token(TokenType.ASSIGMENT, "="));
                    current++;

                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    tokens.add(new Token(TokenType.OPERATOR, Character.toString(ch)));
                    current++;

                    break;
                case '"':
                    tokens.add(new Token(TokenType.STRING, readString()));
                    break;
                case '%':
                    tokens.add(new Token(TokenType.REFERENCES, readReference()));
                default:
                    if(isDigit(ch)){
                        tokens.add(new Token(TokenType.NUMBER, readNumber()));
                    }else if(isAlpha(ch)){
                        String indentifier = readIndentifier();
                        tokens.add(new Token(deriveTokenType(indentifier), indentifier));

                    }else {
                        throw  new LexerExeption("Unsupported character: " + ch);
                    }


            }
        }

    }

    private TokenType deriveTokenType(String indentifier) {
        switch(indentifier){
            case "config":
                return TokenType.CONFIG;
            case "update":
                return TokenType.UPDATE;
                //...........
            default:
                return TokenType.IDENTIFIER;
        }

    }

    private String readIndentifier() {
        StringBuilder builder = new StringBuilder();
        while(current <input.length() && isAlphaNumeric(input.charAt(current))){
            builder.append(input.charAt(current));
            current++;

        }
        return builder.toString();

    }

    private String readNumber() {

      StringBuilder builder =  new StringBuilder();
      while(current< input.length() && isDigit(input.charAt(current))){
          builder.append(input.charAt(input.charAt(current)));
          current++;
      }
          return builder.toString();


    }


    private String readReference() {
        StringBuilder builder = new StringBuilder();
        current++;

        while (current < input.length() && isAlphaNumeric(input.charAt(current))) {
            builder.append((input.charAt(current)));
            current++;


        }
        return builder.toString();
    }


    private boolean isAlphaNumeric(char c ){
        return isAlpha(c) | isDigit(c);

    }

    private boolean isAlpha(char c) {
        return ('a' <= c && c  <='z') || ('A' <= c &&  c <='Z');
    }

    private boolean isDigit(char c){
        return '0' <= c && c <='9';

    }
    private String readString() {
        StringBuilder builder = new StringBuilder();
        while(current< input.length() && input.charAt(current) != '"'){
            builder.append((input.charAt(current)));
            current++;



        }
        return builder.toString();

    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }
    //enum - enumeration

    //TOKEN type and value

    static class Token{
        final TokenType type;
        final String value;

        Token(TokenType type, String value){
            this.type=type;
            this.value=value;


        }

        @Override
        public String toString() {
            return "Token{" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }


    }
    enum TokenType{
        CONFIG, UPDATE, COMPUTE, SHOW, CONFIGS, ASSIGMENT, STRING, NUMBER, OPERATOR, IDENTIFIER, REFERENCES




    }




}
