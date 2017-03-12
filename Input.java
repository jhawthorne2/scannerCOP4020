import java.io.*;

public class Input{
    public FileInputStream in;
    public boolean eof;

    public Input(String inputFileName){
        try{
            this.in = new FileInputStream(inputFileName);
        }
        catch(Exception ex){
            System.out.println("Error creating input file stream");
        }

        this.eof = false;
    }

    public char getCharacter(){
        int character;

        try{
            character = in.read();
        }
        catch(Exception ex){
            System.out.println("Error reading character from file stream");
        }

        if(character == -1){
            eof = true;
            return '';
        }
    }

    // Return true if failed
    public boolean failed(){
        return eof;
    }
    public void pushback(){

    }
}
