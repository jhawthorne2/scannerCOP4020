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

    Lexeme ptree = new Lexeme(Type.PTREE);

    Environment environment;

    public Parser(List<Lexeme> lexList) {

//        this.ptree.setTail(new Lexeme(Type.END_STATEMENT));
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
        if(type == Type.ANYTHING){
            if(check(Type.DIVIDES) || check(Type.MINUS) || check(Type.PLUS) || check(Type.TIMES)){
                System.out.println("Found " + type + "! Advancing.\n");
                return advance();
            }

        }
        else if(check(type)) {
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
        Lexeme tree = new Lexeme(Type.START_STATEMENT);
        tree.setHead(expressionLine());
        if(check(Type.END)){
            tree.setTail(match(Type.END));
            this.ptree.setTail(tree);
            return;
        }
        parse();
        tree.setTail(this.ptree.rightChild);
        this.ptree.setTail(tree);
    }

    public void displayEnvironment(){
        this.environment.printEnvironment();
    }

    public Lexeme expressionLine() {
        Lexeme tree, tmp;
        tmp = unary();
        if(equalsPending()){
            Lexeme var = this.old;
            tree = equals();
            tree.setHead(tmp);
            tree.setTail(expression());
            //this.environment.addVariable(var,this.old);
        }
        else if(operatorPending()) {
            tree = operator();
            tree.setHead(tmp);
            tree.setTail(expression());
        }
        else{
            tree = tmp;
        }
        match(Type.END_STATEMENT);

        return tree;
    }

    public void printThePretty(){

        //printTree(this.ptree.rightChild.leftChild);
        //printTree(this.ptree.rightChild);
        //while(this.ptree)

        Lexeme tmp = this.ptree.rightChild;
        while(tmp != null){
            prettyPrint(tmp.leftChild);
            System.out.println();
            tmp = tmp.rightChild;
        }

    }

    private void printTree(Lexeme tree){
        if(tree != null) {
            System.out.println("My leftChild:");
            printTree(tree.leftChild);
            System.out.println("Me: |" + tree.ch + "| type: |" + tree.type + "|");
            System.out.println("My rightChild:");
            printTree(tree.rightChild);
        }
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
         return match(Type.EQUALS);
    }

    public Lexeme unary() {
        Lexeme tree = null;
        if(check(Type.VARIABLE)) {
            tree = match(Type.VARIABLE);
            if(oparenPending()){
                advance();
                tree.setTail(argumentList());
                if(check(Type.CPAREN)){
                    advance();
                    tree.type = Type.FUNC_CALL;
                }
            }
        }
        else if(check(Type.NUMERIC)) {
            tree = match(Type.NUMERIC);
        }
        else if(check(Type.OPAREN)) {
            tree = match(Type.OPAREN);
            tree.setHead(null);
            tree.setTail(expression());
            tree = match(Type.CPAREN);

        }
        else if(check(Type.MINUS)){
            tree = match(Type.MINUS);
            tree.type = Type.MINFORK;
            tree.setHead(null);
            tree.setTail(unary());
        }
        else if(check(Type.STRING)){
            tree = match(Type.STRING);
        }
        return tree;
    }

    public void prettyPrint(Lexeme tree) {
        switch(tree.type) {
            case NUMERIC:
                System.out.print(tree.getCh());
                break;
            case VARIABLE:
                System.out.print(tree.getCh());
                if(tree.rightChild != null) {
                    if (tree.rightChild.type == Type.VARIABLE) {
                        System.out.print(" , ");
                        prettyPrint(tree.rightChild);
                    }
                }
                break;
            case STRING:
                System.out.print("\"" + tree.getCh() + "\"");
                break;
            case FUNC_CALL:
                //System.out.println("got a func call");
                System.out.print(tree.getCh());
                System.out.print(" ( ");
                if(tree.rightChild != null) {
                    if (tree.rightChild.type == Type.VARIABLE) {
                        prettyPrint(tree.rightChild);
                    }
                }
                System.out.print(" )");
                break;
            case OPAREN:
                System.out.print("( ");
                prettyPrint(tree.rightChild);
                System.out.print(" )");
                break;
            case MINFORK:
                System.out.print("-");
                System.out.print(tree.rightChild.ch);
                break;
            case PLUS:
                prettyPrint(tree.leftChild);
                System.out.print(" + ");
                prettyPrint(tree.rightChild);
                break;
            case MINUS:
                prettyPrint(tree.leftChild);
                System.out.print(" - ");
                prettyPrint(tree.rightChild);
                break;
            case DIVIDES:
                prettyPrint(tree.leftChild);
                System.out.print(" / ");
                prettyPrint(tree.rightChild);
                break;
            case TIMES:
                prettyPrint(tree.leftChild);
                System.out.print(" * ");
                prettyPrint(tree.rightChild);
                break;
            case EQUALS:
                prettyPrint(tree.leftChild);
                System.out.print(" = ");
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

    public Lexeme argumentList() {
        Lexeme tree = null;
        if(argumentListPending()) tree = advance();
        if(check(Type.COMMA)) {
            match(Type.COMMA);
            tree.setTail(argumentList());
        }
        return tree;
    }

    public boolean operatorPending() {
        return check(Type.PLUS) || check(Type.MINUS) || check(Type.TIMES) || check(Type.DIVIDES);
    }

    public boolean oparenPending() {
        return check(Type.OPAREN);
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
