import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Scanner{

    public static final boolean debug = false;
    public static List<Lexeme> lexList;

    public static void main(String[] args){
        System.out.println("Program start...\n");

        confirmArgs(args);

        if(debug)System.out.println(args[0]);
        if(debug)System.out.println(args[1]);

        lexList = new LinkedList<Lexeme>();

        Input inHandler = new Input(args[0]);

        scanner(inHandler);

        //close the file stream
        inHandler.closeFile();

        Output outHandler = new Output(args[1]);

        //write info to console and output file
        for(int i = 0; i < lexList.size(); i++){
            lexList.get(i).display();
            outHandler.writeLine(lexList.get(i));
            if(i < lexList.size()-1) {
                System.out.println();
                outHandler.writeLine("");
            }

        }

        //close the file stream
        outHandler.closeFile();
    }


    public static void scanner(Input in){
        Lexeme lexeme;
        lexeme = lex(in);
        lexList.add(lexeme);
        while(lexeme.getType() != Type.END){
            lexeme = lex(in);
            lexList.add(lexeme);
        }
    }

    public static Lexeme lex(Input in){
        char ch;

        do{
            ch = in.getCharacter();
        }while((ch == ' ' || ch == '\t' || ch == '\r') && in.available()); // Loops while whitespace is read and more chars are available

        if(!in.available()) // Should check if EOF
            return new Lexeme(Type.END, ch);

        switch(ch){
            // single character tokens
            case '(':
                return new Lexeme(Type.OPAREN, ch);
            case ')':
                return new Lexeme(Type.CPAREN, ch);
            case ',':
                return new Lexeme(Type.COMMA, ch);
            case '+':
                return new Lexeme(Type.PLUS, ch);
            case '*':
                return new Lexeme(Type.TIMES, ch);
            case '-':
                return new Lexeme(Type.MINUS, ch);
            case '/':
                return new Lexeme(Type.DIVIDES, ch);
            case '=':
                return new Lexeme(Type.EQUALS, ch);
            case '\n':
                return new Lexeme(Type.END_STATEMENT, ch);
            default:
                // multi-character tokens
                // (only numbers, variables/keywords, and strings)
                if(isdigit(ch) || isperiod(ch)){
                    in.pushback(ch);
                    return lexNumber(in);
                }
                else if(isalpha(ch)){
                    in.pushback(ch);
                    return lexVariable(in);
                    //and keywords!
                }
                else if(ch == '\"'){
                    //in.pushback(ch);
                    return lexString(in);
                }
        }
        return new Lexeme(Type.BAD_CHARACTER, ch);
    }

    public static boolean isdigit(char ch){
        if((int)ch >= 48 && (int)ch <= 57)
            return true;
        // If not a digit, return false
        return false;
    }

    public static boolean isperiod(char ch){
        if((int)ch == 46)
            return true;
        // If not a period, return false
        return false;
    }

    public static boolean isalpha(char ch){
        if((int)ch >= 65 && (int)ch <= 90) // Uppercase
            return true;
        if((int)ch >= 97 && (int)ch <= 122) // Lowercase
            return true;
        // If not alpha, return false
        return false;
    }

    public static void confirmArgs(String[] args){
        if(args.length != 2){
            System.out.println("Incorrect number of arguments");
            System.out.println("Correct usage: java Scanner <inputFileName> <outputFileName>");
            System.exit(1);
        }
    }

    public static Lexeme lexNumber(Input in){
        String s = "";
        char ch;
        boolean period = false;
        boolean error = false;
        int numDigits = 0;

        do{
            ch = in.getCharacter();
            if(isperiod(ch) && !period){
                s += ch;
                period = true;
                if(debug)System.out.println("period");
            }
            else if(isperiod(ch)){
                error = true;
                if(debug)System.out.println("2periods!");
            }
            else if(isdigit(ch)){
                s += ch;
                numDigits++;
                if(debug)System.out.println("digit(" + numDigits + ")");
            }
            else if((ch != ' ' && ch != '\t' && ch != '\n' && ch != '\r')){
                error = true;
                s += ch;
                if(debug)System.out.println("bad character: " + (int)ch);
            }
        }while((ch != ' ' && ch != '\t' && ch != '\n' && ch != '\r') && in.available()); // Loops while whitespace is not read and more chars are available

        if(numDigits > 0 && !error){
            return new Lexeme(Type.NUMERIC, s);
        }

        return new Lexeme(Type.BAD_CHARACTER, s);
    }

    public static Lexeme lexVariable(Input in){
        String s = "";
        char ch;

        do{
            ch = in.getCharacter();
            s += ch;
        }while((ch != ' ' && ch != '\t' && ch != '\n' && ch != '\r') && in.available()); // Loops while whitespace is not read and more chars are available

        return new Lexeme(Type.VARIABLE, s);
    }

    public static Lexeme lexString(Input in){
        String s = "";
        char ch = ' ';
        char prevCh = ' ';
        int numSlashes = 0;

        do{
            prevCh = ch;
            ch = in.getCharacter();
            if(ch =='\\') numSlashes++;
            if((ch != '\"' || prevCh == '\\') && (ch != '\\' || numSlashes%2 == 0)) s += ch;
            if(ch !='\\') numSlashes = 0;
        }while((ch != '"' || prevCh == '\\') && in.available()); // Loops while whitespace is not read and more chars are available

        if(ch != '\"') return new Lexeme(Type.BAD_CHARACTER, s);

        return new Lexeme(Type.STRING, s);

    }
}
