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
BAD_CHARACTER
*/

// Pseudocode from Eddy

import java.util.List;

import static java.lang.System.exit;

public class Parser {

    Lexeme current;
    Lexeme old = null;
    List<Lexeme> lexList;
    int currentIndex = 0;
    int listSize;

    Environment environment;

    public Parser(List<Lexeme> lexList) {

        this.lexList = lexList;
        listSize = this.lexList.size();
        this.current = lex();

        this.environment = new Environment();
    }

    private Lexeme lex(){
        return currentIndex < listSize ? lexList.get(currentIndex++) : null;
    }

    public void processList(){
        expression();
        match(Type.END_STATEMENT);
        parse();
    }

    // Parser utility functions

    public Lexeme advance() {
        Lexeme old = current;

        // Get the next lexeme in the input stream
        // CHANGE THIS TO TRAVERSE THROUGH LEXEMELIST
        current = lex();
        this.old = old;
        return old;
    }

    public Lexeme match(Type type) {
        if(check(type)) {
            System.out.println("Found " + type + "! Advancing.\n");
            return advance();
        }

        System.out.println("Parse error at lexeme(" + currentIndex + "): looking for " + type + ", found " + current.type + " instead\n");

        if(check(Type.END))
            exit(1);

        advance();

        return null;
    }

    public boolean check(Type type) {
        if(current == null) exit(2);
        return (type == current.type);
    }

    public void parse() {
        expressionLine();
        if(check(Type.END)){
            match(Type.END);
            return;
        }
        parse();
    }

    public void displayEnvironment(){
        this.environment.printEnvironment();
    }

    public void expressionLine() {
        unary();
        if(equalsPending()){
            Lexeme var = this.old;
            equals();
            expression();
            this.environment.addVariable(var,this.old);
        }
        else if(operatorPending()) {
            operator();
            expression();
        }
        match(Type.END_STATEMENT);

    }

    public Lexeme expression() {
        Lexeme tree;
        tree = unary();
        if(operatorPending()) {
            Lexeme temp;
            temp = operator();
            temp.setHead(tree);
            temp.setTail(expression());
            tree = temp;
        }
        return tree;
    }

    public Lexeme operator() {
        return match(Type.ANYTHING);
    }

    public Lexeme equals() {
         return advance();
    }

    public Lexeme unary() {
        Lexeme tree;
        if(check(Type.VARIABLE)) {
            tree = match(Type.VARIABLE);
        }
        else if(check(Type.NUMERIC)) {
            tree = match(Type.NUMERIC);
        }
        else if(check(Type.OPAREN)) {
            tree = match(Type.OPAREN);
            tree.setHead(null);
            tree.setTail(expression());
        }
        else {
            tree = match(Type.MINUS);
            tree.type = Type.MINFORK;
            tree.setHead(null);
            tree.setTail(unary());
        }
        return tree;
    }

    public void prettyPrint(Lexeme tree) {
        switch(tree.type) {
            case Type.NUMBER:
                System.out.print(tree.getCh());
                break;
            case Type.VARIABLE:
                System.out.print(tree.getCh());
                break;
            case Type.STRING:
                System.out.print("\"" + tree.getCh() + "\"");
                break;
            case Type.OPAREN:
                System.out.print("(");
                prettyPrint(tree.rightChild);
                System.out.print(")");
                break;
            case Type.MINFORK:
                System.out.print("-");
                System.out.print(tree.rightChild.ch);
                break;
            case Type.PLUS:
                prettyPrint(tree.leftChild);
                System.out.print("+");
                prettyPrint(tree.rightChild);
                break;
            case Type.MINUS:
                prettyPrint(tree.leftChild);
                System.out.print("-");
                prettyPrint(tree.rightChild);
                break;
            case Type.DIVIDES:
                prettyPrint(tree.leftChild);
                System.out.print("/");
                prettyPrint(tree.rightChild);
                break;
            case Type.TIMES:
                prettyPrint(tree.leftChild);
                System.out.print("*");
                prettyPrint(tree.rightChild);
                break;
            default:
                System.out.print("Bad expression");
                break;
        }
    }

    public void optArgumentList() {
        if(argumentListPending()) {
            argumentList();
        }
    }

    public boolean argumentListPending(){
        return check(Type.VARIABLE) || check(Type.NUMERIC) || check(Type.STRING);
    }

    public void argumentList() {
        if(argumentListPending()) advance();
        if(check(Type.COMMA)) {
            match(Type.COMMA);
            argumentList();
        }
    }

    public boolean operatorPending() {
        return check(Type.PLUS) || check(Type.MINUS) || check(Type.TIMES) || check(Type.DIVIDES);
    }

    public boolean equalsPending() {
        return check(Type.EQUALS);
    }

    public boolean expressionPending() {
        return unaryPending();
    }

    public boolean unaryPending() {
        return check(Type.NUMERIC) || check(Type.VARIABLE) || check(Type.OPAREN);
    }
}
