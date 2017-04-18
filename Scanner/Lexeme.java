/*
 * James Hawthorne and Luke Kledzik
 * COP4020
 * Scanner functionality - Lexeme(Lexeme object: Type and contents)
 * 3/12/2017
 */

public class Lexeme{
    public Type type;
    public String ch;
    public Lexeme leftChild = null;
    public Lexeme rightChild = null;
    public Lexeme value = null;

    public Lexeme(Type type){
        this.type = type;
    }

    public Lexeme(Type type, char ch){
        this.type = type;
        this.ch = "" + ch;
    }

    public Lexeme(Type type, String ch){
        this.type = type;
        this.ch = ch;
    }

    // Sets the left child of the lexeme
    public void setHead(Lexeme lexeme) {
        this.leftChild = lexeme;
    }

    // Sets the right child of the lexeme
    public void setTail(Lexeme lexeme) {
        this.rightChild = lexeme;
    }

    // Sets the vale of the lexeme
    public void setValue(Lexeme lexeme) {
        this.value = lexeme;
    }

    public Type getType(){
        return this.type;
    }

    public String getCh(){
        return this.ch;
    }

    public void display(){
        System.out.println(getLine());
    }

    public String getLine(){
        switch(this.type){
            case END:
                return "END";
            case OPAREN:
                return "OPAREN";
            case CPAREN:
                return "CPAREN";
            case COMMA:
                return "COMMA";
            case PLUS:
                return "PLUS";
            case TIMES:
                return "TIMES";
            case MINUS:
                return "MINUS";
            case DIVIDES:
                return "DIVIDES";
            case EQUALS:
                return "EQUALS";
            case END_STATEMENT:
                return "END_STATEMENT";
            case VARIABLE:
                return "VARIABLE: " + this.ch;
            case NUMERIC:
                return "NUMERIC: " + this.ch;
            case STRING:
                return "STRING: " + this.ch;
            case BAD_CHARACTER:
                return "BAD CHARACTER: " + this.ch;
        }
        return "Invalid Lexeme type!";
    }

    public String toString() {
        return "Lexeme(type:|" + this.type + "|, value:|" + this.ch + "|)";
    }
}
