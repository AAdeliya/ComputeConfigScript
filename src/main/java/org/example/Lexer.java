package org.example;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    // Define token types using regular expressions
    private static final String NUMBER = "\\d+";
    private static final String ASSIGN = "=";
    private static final String IDENTIFIER = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String WHITESPACE = "\\s+";

    // Token class to store type and value of each token
    public static class Token {
        public final String type;
        public final String value;

        public Token(String type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Token{" +
                    "type='" + type + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    // List to hold the tokens
    public ArrayList<Token> tokenize(String source) {
        ArrayList<Token> tokens = new ArrayList<>();
        String patternString = String.format("(%s)|(%s)|(%s)|(%s)",
                NUMBER, ASSIGN, IDENTIFIER, WHITESPACE);

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                tokens.add(new Token("NUMBER", matcher.group(1)));
            } else if (matcher.group(2) != null) {
                tokens.add(new Token("ASSIGN", matcher.group(2)));
            } else if (matcher.group(3) != null) {
                tokens.add(new Token("IDENTIFIER", matcher.group(3)));
            } else if (matcher.group(4) != null) {
                // Ignore whitespace
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String inputScript = "CPU = 4\nMemory = 8192";
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.tokenize(inputScript);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}