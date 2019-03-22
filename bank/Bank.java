import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank{
    //save the clients in a hashmap
    Map<String, Client> accounts;

    //the name of the bank
    private String bankName = "";

    Bank(String bankName){
        this.bankName = bankName;

        //accounts is a new hasmap, with the cardnumber and matching client
        accounts = new HashMap<String, Client>();
        
        //create new clients
        Client client1 = new Client("Jordy", "1234", 100);
        Client client2 = new Client("Klaas", "2321", 200);
        Client client3 = new Client("Piet", "3123", 300);
        
        //put them in the accounts hashmap
        accounts.put("1", client1);
        accounts.put("2", client2);
        accounts.put("3", client3);
    }

    public Client get(String pAccountNumber){
        //the currentclient
        Client currentClient;

        //checks if the accountnumber exists
        if(accounts.get(pAccountNumber) != null){
            //stores the found client and returns it
            currentClient = accounts.get(pAccountNumber);

            return currentClient;
        }
        return null;
    }
}