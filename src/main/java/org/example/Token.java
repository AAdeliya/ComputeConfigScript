package org.example;

public class Token {
    public enum Type{
        NUMBER, MULTIPLY, DIVIDE, CONFIG, UPDATE, COMPUTE, SHOW, CONFIGS, ASSIGMENT, STRING, OPERATOR, IDENTIFIER, REFERENCES

    }

    public final Type type;
    public final String value;

    Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
