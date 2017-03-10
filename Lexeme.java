public enum Type{
    END,
    OPAREN,
    CPAREN,
    COMMA,
    PLUS,
    TIMES,
    MINUS,
    DIVIDES,
    BAD_CHARACTER
}

public class Lexeme{
    public Type type;
    public char ch;

    public Lexeme(Type type){
        this.type = type;
    }

    public Lexeme(Type type, char ch){
        this.type = type;
        this.ch = ch;
    }
}
