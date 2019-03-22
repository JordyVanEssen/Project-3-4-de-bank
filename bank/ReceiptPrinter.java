public class ReceiptPrinter extends HardwareElement implements OutputDevice{

    ReceiptPrinter(String name){
        super(name);
    }

    //gives the output
    @Override
    public void giveOutput(String output) {
        //prints the output
        System.out.println(output);
    }
}