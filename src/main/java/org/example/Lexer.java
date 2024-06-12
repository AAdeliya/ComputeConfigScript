package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Lexer  {
    private final String input;
    private final List<Token> tokens;
    private int current;
    public static List<Token> getHardCodedTokens(){
        return Arrays.asList(
                new Token(Token.Type.NUMBER, "3"),
                new Token(Token.Type.MULTIPLY, "*" ),
                new Token(Token.Type.NUMBER, "5"),
                new Token(Token.Type.MULTIPLY, "*"),
                new Token(Token.Type.NUMBER, "10"),
                new Token(Token.Type.MULTIPLY, "*"),
                new Token(Token.Type.NUMBER, "4"));


    }



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
                    tokens.add(new Token(Token.Type.ASSIGMENT, "="));
                    current++;

                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    tokens.add(new Token(Token.Type.OPERATOR, Character.toString(ch)));
                    current++;

                    break;
                case '"':
                    tokens.add(new Token(Token.Type.STRING, readString()));
                    current++; //only for strings
                    break;
                case '%':
                    tokens.add(new Token(Token.Type.REFERENCES, readReference()));
                    break;

                    default:
                    if(isDigit(ch)){
                        tokens.add(new Token(Token.Type.NUMBER, readNumber()));
                    }else if(isAlpha(ch)){
                        String indentifier = readIndentifier();
                        tokens.add(new Token(deriveTokenType(indentifier), indentifier));

                    }else {
                        throw  new LexerExeption("Unsupported character: " + ch);
                    }


            }
        }

    }

    public  Token.Type deriveTokenType(String indentifier) {
        return switch (indentifier) {
            case "config" -> Token.Type.CONFIG;
            case "update" -> Token.Type.UPDATE;
            case "compute" -> Token.Type.COMPUTE;
            case "show" -> Token.Type.SHOW;
            case "configs" -> Token.Type.CONFIGS;
            default -> Token.Type.IDENTIFIER;
        };

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
        return ('a' <= c && c  <='z') || ('A' <= c &&  c <='Z') || c == '_';
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

    public Iterator<Token> iterator() {
        return tokens.iterator();
    }
    //enum - enumeration

    //TOKEN type and value



//        @Override
//        public String toString() {
//            return "Token{" +
//                    "type=" + type +
//                    ", value='" + value + '\'' +
//                    '}';
//        }


    }


