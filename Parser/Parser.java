/*
END
OPAREN
CPAREN
COMMA
PLUS
TIMES
MINUS
DIVIDES
EQUALS
END_STATEMENT
VARIABLE
NUMERIC
STRING
BAD CHARACTER
*/

// Pseudocode from Eddy

public class Parser {

    Lexeme current;

    public static void main(String[] args) {

    }

    public static void parse() {
        advance();
        expression();
        match(SEMI);
    }

    // Recursive descent parsing functions

    public static void expression() {
        unary();
        if(operatorPending()) {
            operator();
            expression();
        }
    }

    public static void operator() {
        advance();
    }

    public static void unary() {
        if(check(INTEGER)) {
            match(INTEGER);
        }
        else if(check(VARIABLE)) {
            advance();
            if(check(OPAREN)) {
                advance();
                optArgumentList();
                match(CPAREN);
            }
        }
        else {
            match(OPAREN);
            expression();
            match(CPAREN);
        }
    }

    // Parser utility functions

    public static Lexeme advance() {
        Lexeme old = current;

        // Get the next lexeme in the input stream
        current = lex();

        return old;
    }

    public static Lexeme match(Lexeme lexeme) {
        if(check(lexeme)) {
            return advance();
        }

        System.out.println("Parse error: looking for " + lexeme.getLine() + ", found " + current.getLine() + " instead\n");

        return null;
    }

    public static boolean check(Lexeme lexeme) {
        return (lexeme.type == current.type)
    }
}
