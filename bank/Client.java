public class Client{
    //name, pin and balance of the client
    private String name = "";
    private String pin = "";
    private int balance = 0;

    //sets the client info
    Client(String name, String pin, int balance){
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    //retur the pin of the client
    public String getPin(){
        return this.pin;
    }

    //returns the name of the client
    public String getName(){
        return this.name;
    }

    //checks if the entered pin is correct
    public boolean checkPin(String pin){
        boolean isEqual = this.pin.equals(pin);

        return isEqual;
    }

    /**
     * @return the balance
     */
    public int getBalance(String pin) {
        if (checkPin(pin)) {
            return balance;
        }
        else{
            return Integer.MIN_VALUE;
        }
    }

    //adds the amount to the users balance
    public void deposit(int deposit){
        balance += deposit;
    }

    //withdraws the amount
    public boolean withDraw(int amount, String pin){
        //if the client has enough money
        if ((amount <= balance) && checkPin(pin)) {
            this.balance -= amount;
            return true;
        }
        else{
            return false;
        }
    }
}