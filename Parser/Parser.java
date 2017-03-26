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

    // Parser utility functions

    public static Lexeme advance() {
        Lexeme old = current;

        // Get the next lexeme in the input stream
        // CHANGE THIS TO TRAVERSE THROUGH LEXEMELIST
        current = lex();

        return old;
    }

    public static Lexeme match(Type type) {
        if(check(type)) {
            return advance();
        }

        System.out.println("Parse error: looking for " + type.typeOf() + ", found " + type.typeOf() + " instead\n");

        return null;
    }

    public static boolean check(Type type) {
        return (type == current.type)
    }

    public static void parse() {
        advance();
        expression();
        match(END_STATEMENT);
    }

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
        if(check(NUMERIC)) {
            match(NUMERIC);
        }
        else if(check(VARIABLE)) {
            advance();
            if(check(OPAREN)) {
                advance();
                optArgumentList();
                match(CPAREN);
            }
        }
        // Must then be a parenthesized expression
        else {
            match(OPAREN);
            expression();
            match(CPAREN);
        }
    }

    public static void optArgumentList() {
        if(argumentListPending()) {
            argumentList();
        }
    }

    public static void argumentList() {
        expression();
        if(check()) {
            match(COMMA);
            argumentList();
        }
    }

    public static boolean operatorPending() {
        return check(PLUS) || check(MINUS) || check(TIMES) || check(DIVIDES);
    }

    public static boolean expressionPending() {
        return unaryPending();
    }

    public static boolean unaryPending() {
        return check(NUMERIC) || check(VARIABLE) || check(OPAREN);
    }
}
