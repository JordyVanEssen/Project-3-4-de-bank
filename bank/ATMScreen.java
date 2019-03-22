import java.awt.*;

public class ATMScreen extends Container{
    ATMScreen(){
        super();   
        setLayout(null);
    }

    public void add(ScreenElement element){
        element.setContainer(this);
    }

    public void clear(){
        //removes all the elements from the screen
        removeAll();
    }

    public void paint(Graphics g){
        //paints the logo in the right bottomcorner
        super.paint(g);
        g.setColor(Color.RED);
        g.fillRoundRect(390, 270, 35, 35, 10, 10);//347 230
        g.fillRect(420, 300, 5, 5);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        g.drawString("HR", 393, 290);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("bank", 394, 300);
    }
}