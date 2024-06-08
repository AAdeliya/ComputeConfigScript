package org.example;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class LexerLoop {

        // Define token types using regular expressions
        private static final String NUMBER = "\\d+";
        private static final String KEYWORD = "\\b(loop|from|to|end)\\b";
        private static final String IDENTIFIER = "[a-zA-Z_][a-zA-Z0-9_]*";
        private static final String STRING = "\"[^\"]*\"";
        private static final String OPERATOR = "=";
        private static final String PLACEHOLDER = "%[a-zA-Z_][a-zA-Z0-9_]*";
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

        // Method to tokenize the source script
        public ArrayList<Token> tokenize(String source) {
            ArrayList<Token> tokens = new ArrayList<>();
            String patternString = String.format("(%s)|(%s)|(%s)|(%s)|(%s)|(%s)|(%s)",
                    NUMBER, KEYWORD, IDENTIFIER, STRING, OPERATOR, PLACEHOLDER, WHITESPACE);

            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(source);

            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    tokens.add(new Token("NUMBER", matcher.group(1)));
                } else if (matcher.group(2) != null) {
                    tokens.add(new Token("KEYWORD", matcher.group(2)));
                } else if (matcher.group(3) != null) {
                    tokens.add(new Token("IDENTIFIER", matcher.group(3)));
                } else if (matcher.group(4) != null) {
                    tokens.add(new Token("STRING", matcher.group(4)));
                } else if (matcher.group(5) != null) {
                    tokens.add(new Token("OPERATOR", matcher.group(5)));
                } else if (matcher.group(6) != null) {
                    tokens.add(new Token("PLACEHOLDER", matcher.group(6)));
                } else if (matcher.group(7) != null) {
                    // Ignore whitespace
                }
            }

            return tokens;
        }

        public static void main(String[] args) {
            String inputScript = """
        loop "i" from 1 to 5
        compute "iteration_result_%i" = %num_users * %i
        end""";
            Lexer lexer = new Lexer();
            ArrayList<Lexer.Token> tokens = lexer.tokenize(inputScript);
            for (Lexer.Token token : tokens) {
                System.out.println(token);
            }
        }
    }
