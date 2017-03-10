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

    public Type getType(){
        return this.type;
    }

    public char getCh(){
        return this.ch;
    }

    public void display(){
        switch(this.type){
            case END:
                System.out.println("END");
                break;
            case OPAREN:
                System.out.println("OPAREN");
                break;
            case CPAREN:
                System.out.println("CPAREN");
                break;
            case COMMA:
                System.out.println("COMMA");
                break;
            case PLUS:
                System.out.println("PLUS");
                break;
            case TIMES:
                System.out.println("TIMES");
                break;
            case MINUS:
                System.out.println("MINUS");
                break;
            case DIVIDES:
                System.out.println("DIVIDES");
                break;
            case BAD_CHARACTER:
                System.out.println("BAD CHARACTER: " + this.ch);
                break;
        }
    }
}
