import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CardReader extends HardwareElement implements InputDevice{
    //input readers
    BufferedReader bufferedReader;
    InputStreamReader inputReader;

    CardReader(String name){
        super(name);

        //the inutstream is the console, system.in
        inputReader = new InputStreamReader(System.in);
        bufferedReader = new BufferedReader(inputReader);
    }

    @Override
    public String getInput() {
        try {
            //reads the input
            Scanner reader = new Scanner(bufferedReader);
            String cardnumber = "";
            System.out.println("To simulate inserting card, enter card number...");
            //saves the input
            cardnumber = reader.nextLine();
            return cardnumber;
        } catch (Exception e) {
            //TODO: handle exception
            return null;
        }
    }
}