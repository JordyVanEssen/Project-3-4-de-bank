import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Keypad extends HardwareElement implements InputDevice{
    BufferedReader bufferedReader;
    InputStreamReader inputReader;

    Keypad(String name){
        super(name);
        //reads the input
        inputReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputReader);
    }

    @Override
    public String getInput() {
        //the input
        String input = "";
        String password = "";
        //System.out.println("Enter character: ");
        try {
            //reader to read the input
            Scanner reader = new Scanner(System.in);
            //saves input
            char strChar = ' ';

            if (bufferedReader.ready()) {
                strChar = reader.next().charAt(0);
                input = String.valueOf(strChar);
                return input;
            }
            else{
                return null;
            }
        } catch (Exception e) {
            //TODO: handle exception
            return null;
        }
    }
}