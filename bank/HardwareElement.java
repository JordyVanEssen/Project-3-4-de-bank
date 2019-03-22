public abstract class HardwareElement extends ATMElement{
     boolean power = false;

    HardwareElement(String name){
        super(name);
    }

    public void powerOn(){
        power = true;
    }

    public void powerOff(){
        power = false;
    }
}