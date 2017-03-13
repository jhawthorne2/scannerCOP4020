import java.io.*;

public class Input{

    private static final int pushbackLimit = 1;
    private PushbackInputStream in;
    private boolean eof;

    public Input(String inputFileName){
        try{
            this.in = new PushbackInputStream(new FileInputStream(inputFileName), pushbackLimit);
        }
        catch(Exception ex){
            System.out.println("Error creating input file stream");
        }

        this.eof = false;
    }

    public char getCharacter(){
        int character = -1;

        try{
            character = in.read();
        }
        catch(Exception ex){
            System.out.println("Error reading character from file stream");
        }

        if(character == -1){
            eof = true;
            return '\0';
        }

        return (char)character;
    }

    public boolean available(){
        try{
            return in.available() > 0 && !this.eof;
        }
        catch(Exception ex){
            System.out.println("Error reading character from file stream");
            return false;
        }
    }

    // Return true if failed
    public boolean failed(){
        return eof;
    }

    public void pushback(char c){
        try{
            in.unread(c);
        }
        catch(Exception ex){
            System.out.println("Error pushing character to the file stream");
        }
    }

    public void closeFile(){
        try{
            this.in.close();
        }
        catch(Exception ex){
            System.out.println("Error closing the file stream");
        }
    }
}
