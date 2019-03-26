import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ATM{
    //instances of classes which are used in this class
    Bank _deBank;
    ATMScreen _as;
    DisplayText _showText;
    ScreenButton _btn;
    Keypad _keypad;

    //a frame
    Frame _f;
    //the position of the screenelements
    Point _pos;
    //arrays for the buttons to get checked
    ScreenButton[] _btnKeypad = new ScreenButton[10]; 
    ScreenButton[] _btnTransaction = new ScreenButton[4]; 
    ScreenButton[] _btnReceipt = new ScreenButton[2]; 

    //the amount the user wants to withdraw
    int _amount = 0;

    //empty strings for the password and input to be stored in
    String _password = "";
    String _input = " ";
    //Transaction mode, Withdrawel or Deposit
    String _mode = "";

    //booleans for showing labels and buttons
    boolean _setLabel = true;
    boolean _createBtn = true;
    boolean _visible = true;
    boolean _transaction = false;
    boolean _transactionFinished = false;

    ATM(Bank bank){
        _deBank = bank;

        //creates a frame
        _as = new ATMScreen();
        _f = new Frame("My ATM");
        _f.setBounds(200, 200, 450, 350);
        _f.setBackground(Color.DARK_GRAY);
        _f.addWindowListener(new MyWindowAdapter(_f));
        _f.add(_as);
        _f.setVisible(true);

        doTransaction(null);
    }

    //main function, if the user entered a valid cardnumber
    public void doTransaction(Client pCurrentClient){
        //position
        int x = 50;
        int y = 20;
        
        _transaction = false;

        //if there is a client found
        if(pCurrentClient != null){
            transaction(pCurrentClient, x, y);
        }
    }

    //prints the receipt of the user
    public void printReceipt(Client pCurrentClient){
        //to show the time
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //uses the hardware element 'Receiptprinter'
        ReceiptPrinter printer = new ReceiptPrinter("Printer");
        
        //the receipt
        String receipt = "===================================\nNaam:\t\t\t" + pCurrentClient.getName() 
                                + "\nMode:\t\t\t" + _mode 
                                    + "\nAmount:\t\t\t" + _amount
                                        + "\nNew balance:\t\t" + pCurrentClient.getBalance(_password) 
                                            + "\nTime:\t\t\t" + sdf.format(cal.getTime()) 
                                                + "\n===================================";
        //prints a digital receipt
        printer.giveOutput(receipt);
    }

    //creates the buttons 'Yes' & 'No'
    public void choiceBtn(String pAnswer1, String pAnswer2,int pX, int pY){
        String btnName = "";
        //to create 2 buttons
        for (int i = 0; i < 2; i++) {
            pY += 40;//creating a new position
            _pos = new Point(pX, pY);//position of the button
    
            switch (i) {
                case 0:
                    btnName = pAnswer1;
                    break;
                case 1:
                    btnName = pAnswer2;
                    break;
                default:
                    break;
            }

            //ads the buttons to the array
            _btn = new ScreenButton(btnName, _pos);
            _btn.setContainer(_as);
            _btnReceipt[i] = _btn;
        }
    }

    //checks for the yes & no button input, pMode == 'T' or 'C', transaction or choice
    private boolean choice(char pMode, int pX, int pY){
        int c = 0;
        if(pMode == 'T'){
            choiceBtn("Withdraw","Deposit",pX, pY);
        }
        else{
            choiceBtn("Yes","No",pX, pY);
        }
        //checks if a button is pressed
        while (true) {
            _input = _btnReceipt[c].getInput();

            //if they are pressed
            if(_input != null){
                if(_input.equals("Yes") || _input == "Withdraw"){//display the receipt
                    setButton(' ', false);                        
                    return true;
                }
                else{//break of the function showing nog receipt
                    if(_input.equals("No")  || _input == "Deposit"){
                        setButton(' ', false);
                        return false;
                    }
                }
            }

            c++;
            if(c == 2){
                c = 0;
                //to pause the thread for a bit
                Thread.yield();
            }
        }

    }

    //after the transaction, asks for a receipt
    public void receipt(int pX, int pY, Client pCurrentClient){
        setButton('T', false);

        //changes the text of the label, asks for a receipt
        setLabelText("Want a receipt?");

        //if Yes is pressed   
        if(choice('C', pX, pY)){
            if(_mode.equals("Withdrawel")){
                setLabelText("Now dispensing \u20AC " + _amount);
            }
            else{
                setLabelText("Adding amount: \u20AC " + _amount);
            }
            delay(750);
            if(_mode.equals("Withdrawel")){
                setLabelText("Please take your cash.");
                delay(1250);
            }
            printReceipt(pCurrentClient);
            setLabelText("Goodbye");
            delay(2000);
        }
    }

    //checks if the user enters a valid password
    public boolean authenticate(int pX, int pY, Client pCurrentClient){
        Point pos = new Point(160, 70);
        DisplayText showX = new DisplayText("showX", pos);
        showX.setContainer(_as);
        showX.giveOutput("");

        //creates a keypad
        _keypad = new Keypad("Keypad");
        //empty password 
        _password = "";
        //when a character is entered a 'X' is shown on screen
        String text = "";
        //keypad input
        String kInput = "";
        //console keypad input
        String cInput = "";
        //counter in the while loop
        int c = 0;

        setLabelText("Enter your password " + pCurrentClient.getName());
        //if the buttons are not yet created
        if(_createBtn){
            createButton(20, 70);
            _createBtn = false;
        }
        else{//if they are created, just set them visible
            setButton('T', false);
            setButton('K', true);
        }
            
        //checks for input from the buttons
        while (true) {
            //_input = _btnKeypad[c].getInput();
            cInput = _keypad.getInput();
            kInput = _btnKeypad[c].getInput();

            //check for input from the buttons or keypad
            if(kInput != null){
                //set the input
                _input = kInput;
            }
            else if(cInput != null){
                //set the input
                _input = cInput;
            }
            else{
                //reset input values
                _input = "";
                cInput = "";
                kInput = "";
            }

            //if there is input
            if (_input != null) {
                //if the first character is entered
                if(_input.length() == 1){
                    _password += _input;
                    text += "X";
                    showX.giveOutput(text);
                }

                //if the user is done entering his password
                if(_password.length() == pCurrentClient.getPin().length()){
                    //checks if the password is correct
                    if(pCurrentClient.getPin().equals(_password)){
                        setButton('K',false);
                        delay(200);
                        showX.getLabel().setVisible(false);
                        return true;
                    }
                    else{//if not try again
                        _password = "";
                        text = "";
                        showX.giveOutput(" ");
                        setLabelText("Incorrect password");
                        delay(1250);
                        setLabelText("Please try again");
                    }
                }
            }   
            
            if(c == 9){
                c = 0;
                Thread.yield();
            }
            else{
                c++;
            }
        }
    }

    //When the user is logged 
    public void transaction(Client pCurrentClient, int pX, int pY){
        boolean withdraw = false;
        _keypad = new Keypad("Keypad");
        //the balanc of the current user
        //counter
        int c = 0;

        setLabelText("Choose:");
        if(choice('T', pX, pY)){
            withdraw = true;
        }
        else{
            withdraw = false;
        }

        if (withdraw) {
            setLabelText("Withdraw, choose amount");
        }
        else{
            setLabelText("Deposit, choose amount");
        }

        //creates the buttons to choose from
        createSaldoButton(pX, pY);

        //checks for input
        while (true) {
            _input = _btnTransaction[c].getInput();

            //if there is any input except with the value 'null'
            if(_input != null){
                if(withdraw){
                    _amount = Integer.valueOf(_input);
                    //withdraw the amount
                    if(pCurrentClient.withDraw(Integer.valueOf(_input), _password)){
                        _mode = "Withdrawel";
                        //asks for a receipt
                        receipt(pX, pY, pCurrentClient);
                        break;
                    }
                    else{
                        //if not, show an error
                        setLabelText("Your balance is not enough");
                        delay(2500);
                        break;
                    }
                }
                else{//deposit
                    _amount = Integer.valueOf(_input);
                    _mode = "Deposit";

                    //deposit the amount
                    pCurrentClient.deposit(_amount);
                    receipt(pX, pY, pCurrentClient);
                    break;
                }
                
            }

            //counter to loop through the array
            c++;
            if(c == 4){
                c = 0;
                Thread.yield();
            }
        }
        setButton(' ', false);
        setButton('T', false);
    }

    //Creates the label
    public void setLabel(String pLabelName, String pText, int pX, int pY){
        //if there is yet no label, create one
        if (_setLabel) {
            _pos = new Point(pX, pY);
            _showText = new DisplayText(pLabelName, _pos);
            _showText.setContainer(_as);
            _showText.giveOutput(pText);
            _setLabel = false;    
        }
    }

    //sets the text of the label
    public void setLabelText(String text){
        _showText.giveOutput(text);
    }

    //sets the buttons visible or unvisible
    private void setButton(char p, boolean pVisible){
        ScreenButton currentBtn;

        if (pVisible) {
            if(p == 'K'){//keypad buttons
                for (int x = 0; x < 10; x++){
                    currentBtn = _btnKeypad[x];
                    currentBtn.getButton().setVisible(pVisible);
                }
            }
            else if(p == 'T'){//amount buttons
                for (int x = 0; x < 4; x++) {
                    currentBtn = _btnTransaction[x];
                    currentBtn.getButton().setVisible(pVisible);
                }
            }
            else{//yes and no buttons
                for (int x = 0; x < 2; x++) {
                    currentBtn = _btnReceipt[x];
                    currentBtn.getButton().setVisible(pVisible);
                }
            }
            
        }
        else{
            if(p == 'K'){
                for (int x = 0; x < 10; x++) {
                    currentBtn = _btnKeypad[x];
                    currentBtn.getButton().setVisible(pVisible);
                }
            }
            else if(p == 'T'){
                for (int x = 0; x < 4; x++) {
                    currentBtn = _btnTransaction[x];
                    currentBtn.getButton().setVisible(pVisible);
                }
            }
            else{
                for (int x = 0; x < 2; x++) {
                    currentBtn = _btnReceipt[x];
                    currentBtn.getButton().setVisible(pVisible);
                }
            }

        }
    }

    //Creates the buttons to choose from when you are logged in
    public void createSaldoButton(int pX, int pY){
        //the name of the button
        String btnName = "";
        for (int i = 0; i < 4; i++) {
            pY += 40;
            _pos = new Point(pX, pY);//position of the buttons
    
            switch (i) {
                case 0:
                    btnName = "20";
                    break;
                case 1:
                    btnName = "50";
                    break;
                case 2:
                    btnName = "100";
                    break;
                case 3:
                    btnName = "200";
                    break;
                default:
                    break;
            }

            //adds them to the array
            _btn = new ScreenButton(btnName, _pos);
            _btn.setContainer(_as);
            _btnTransaction[i] = _btn;
        }
    }

    //Creates the buttons of the keypad
    private void createButton(int pX, int pY){
        //indicates the rows
        int a = 0;
        //default x and y values
        int defX = pX;
        String btnName = "";

        //creates the keypad buttons
        for (int x = 0; x < 10; x++) {
            
            if(x == 9){
                pX += 60;
            }
            else{
                pX += 30;
            }
            _pos = new Point(pX, pY);//position of the button

            a++;//counter for the rows
            if(a == 3){
                pY += 30;
                pX = defX;
                a = 0;
            }
            btnName = Integer.toString(x);

            /*
            if(x < 10){
            }
            else{
                if(x == 10){
                    pY += 120;
                    pX -= 90;
                    btnName = "Enter";

                }
            }*/
            
            //adds them to the array
            _btn = new ScreenButton(btnName, _pos);
            _btn.setContainer(_as);
            _btnKeypad[x] = _btn;
            btnName = "";
        }
    }

    //A delay for in the while loop when the buttons are checked
    public void delay(int pMillis){
        try {
            Thread.sleep(pMillis);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}
