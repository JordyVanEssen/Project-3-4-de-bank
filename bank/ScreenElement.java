import java.awt.*;

public abstract class ScreenElement extends ATMElement{
    Point pos;

    ScreenElement(String name, Point position){
        super(name);
        //the position of the element
        this.pos = position;
    }

    public abstract void setContainer(Container screenElement);
}