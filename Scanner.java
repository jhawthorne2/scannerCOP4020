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
        }
        catch(Exception ex){
            System.out.println("Error creating file streams");
        }

        scanner(in, out);

        /*
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
        */
    }


    public void scanner(FileInputStream in, FileOutputStream out){
        Lexeme lexeme;
        lexeme = lex();
        while(lexeme.getType() != END){
            lexeme.display();
            newline();
            lexeme = lex();
        }
    }

    public Lexeme lex(FileInputStream in){
        char ch;
        skipWhiteSpace();
        ch = Input.getCharacter(in);
        if(Input.failed())
            return new Lexeme(Type.END);
        switch(ch){
            // single character tokens
            case '(':
                return new Lexeme(Type.OPAREN);
            case ')':
                return new Lexeme(Type.CPAREN);
            case ',':
                return new Lexeme(Type.COMMA);
            case '+':
                return new Lexeme(Type.PLUS);
            case '*':
                return new Lexeme(Type.TIMES);
            case '-':
                return new Lexeme(Type.MINUS);
            case '/':
                return new Lexeme(Type.DIVIDES);
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
        return new Lexeme(Type.BAD_CHARACTER, ch);
    }

    public static void confirmArgs(String[] args){
        if(args.length != 2){
            System.out.println("Incorrect number of arguments");
            System.out.println("Correct usage: java Scanner <inputFileName> <outputFileName>");
            System.exit(1);
        }
    }
}
