import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenButton extends ScreenElement implements InputDevice{
    ATMScreen screen = new ATMScreen();
    String name;
    Button button;
    boolean inputAvailable = false;
    ActionListener listener;

    ScreenButton(String name, Point position){
        super(name, position);
        this.name = name;
        //function to check if the button is pressed
        listener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inputAvailable = true;
            }
            
        };

        //creates a button
        button = new Button(name);
        button.setBounds(pos.x, pos.y, 10 + 15 * name.length(), 25);
        button.addActionListener(listener);
        button.setVisible(true);
        //screen.add(ScreenElement(button));
    }

    //returns the current button
    public Button getButton(){
        return this.button;
    }

    @Override
    public String getInput() {
        //returns the name of the button if the button is pressed
        //if the button is not pressed, returns null
        if (inputAvailable) {
            inputAvailable = false;
            return this.name;            
        }
        else{
            return null;
        }
    }

    @Override
    public void setContainer(Container pScreenElement) {
        //adds the button to the screen
        pScreenElement.add(button);
    }
}