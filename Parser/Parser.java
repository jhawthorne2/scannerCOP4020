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
}
