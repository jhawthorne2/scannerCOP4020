import java.io.*;

public class Scanner{
    public static void main(String[] args){
        System.out.println("Program start.");

        FileInputStream in = null;
        FileOutputStream out = null;

        confirmArgs(args);

        try{
            in = new FileInputStream(args[0]);
            out = new FileOutputStream(args[1]);
            int character;
            while ((character = in.read()) != -1){
                out.write(character);
            }
        }
        catch(Exception ex){
            System.out.println("Error reading from FileStreams");
        }
        try{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
        }
        catch(Exception ex){
            System.out.println("Error closing FileStreams");
        }
    }


    public void scanner(){
        Lexeme lexeme;
        lexeme = lex();
        while(lexeme.getType() != END){
            lexeme.display();
            newline();
            lexeme = lex();
        }
    }

    public Lexeme lex(){
        char ch;
        skipWhiteSpace();
        ch = Input.getCharacter();
        if(Input.failed)
            return new Lexeme(END);
        switch(ch){
            // single character tokens
            case '(':
                return new Lexeme(OPAREN);
            case ')':
                return new Lexeme(CPAREN);
            case ',':
                return new Lexeme(COMMA);
            case '+':
                return new Lexeme(PLUS);
            case '*':
                return new Lexeme(TIMES);
            case '-':
                return new Lexeme(MINUS);
            case '/':
                return new Lexeme(DIVIDES);
            default:
                // multi-character tokens
                // (only numbers, variables/keywords, and strings)
                if(isdigit(ch)){
                    Input.pushback(ch);
                    return lexNumber();
                }
                else if(isalpha(ch)){
                    Input.pushback(ch);
                    return lexVariable();
                    //and keywords!
                }
                else if(ch == '\"'){
                    Input.pushback(ch);
                    return lexString();
                }
        }
        return new Lexeme(BAD_CHARACTER, ch);
    }

    public static void confirmArgs(String[] args){
        if(args.length != 2){
            System.out.println("Incorrect number of arguments");
            System.out.println("Correct usage: java Scanner <inputFileName> <outputFileName>");
            System.exit(1);
        }
    }
}
