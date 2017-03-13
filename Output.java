/*
 * James Hawthorne and Luke Kledzik
 * COP4020
 * Scanner functionality - Output(file io class)
 * 3/12/2017
 */
import java.io.*;

public class Output {

    private static final int pushbackLimit = 1;
    private FileOutputStream  out;
    private File file;

    public Output(String outputFileName){
        try{
            file = new File(outputFileName);
            // if file does not exists,  create it
            if (!file.exists()) {
                file.createNewFile();
            }

            this.out = new FileOutputStream(file);
        }
        catch(Exception ex){
            System.out.println("Error creating output file stream");
        }
    }

    public void writeLine(Lexeme lex){
        int character = -1;
        String line = lex.getLine();
        try{
            for(int i = 0; i < line.length(); i++){
                character = line.charAt(i);
                this.out.write(character);
            }
            this.out.write('\r');
            this.out.write('\n');
        }
        catch(Exception ex){
            System.out.println("Error reading character from file stream");
        }
    }

    public void writeLine(String line){
        int character = -1;
        try{
            for(int i = 0; i < line.length(); i++){
                character = line.charAt(i);
                this.out.write(character);
            }
            this.out.write('\r');
            this.out.write('\n');
        }
        catch(Exception ex){
            System.out.println("Error reading character from file stream");
        }
    }

    public void closeFile(){
        try{
            this.out.flush();
            this.out.close();
        }
        catch(Exception ex){
            System.out.println("Error closing the file stream");
        }
    }
}
