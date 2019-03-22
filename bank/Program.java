import java.util.Map;
//==================
//Author: Jordy van Essen
//TI1A

public class Program{
    public static void main(String[] args) {
        //global variables
        Client _currentClient = new Client(null, null, 0);
        CardReader _reader = new CardReader("Cardreader");
        Bank _bank = new Bank("Rabo");
        ATM _atm  = new ATM(_bank);

        String _cardnumber = "";

        boolean _loggedIn = false;

        //infinite loop
        while(true){
            try {
                //welcomes user
                _atm.setLabel("lblInsert", "Welcome", 20, 20);
                _atm.delay(1500);
                _atm.setLabelText("Insert your card");
                _cardnumber = _reader.getInput();
                _currentClient = _bank.get(_cardnumber);
                
                //if the cardnumber exists
                if (_currentClient != null) {
                    _loggedIn = _atm.authenticate(50, 20, _currentClient);
                    //if the password is correct
                    if(_loggedIn){
                        _atm.transaction(_currentClient, 50, 20);
                    }
                }
                else{
                    _atm.setLabelText("Incorrect cardnumber");
                    Thread.sleep(2500);
                }
                _cardnumber = "";
            } catch (Exception e) {

            }
        }
        
    }
}