import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MyWindowAdapter extends WindowAdapter{
    Frame f;
    MyWindowAdapter(Frame f){
        this.f = f;
    }

    public void windowClosing(WindowEvent e){
        //window is able to close when 'X' is pressed
        f.dispose();
        System.exit(0);
    }
}