import java.awt.*;

public class DisplayText extends ScreenElement implements OutputDevice{
    ATMScreen screen = new ATMScreen();
    Label lbl;

    DisplayText(String name, Point position){
        super(name, position);

        //creates a label
        lbl = new Label();
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 30));
        lbl.setBounds(pos.x, pos.y, 400, 30);
    }

    //returns the current label
    public Label getLabel(){
        return this.lbl;
    }

    @Override
    public void setContainer(Container screenElement) {
        //adds the label to the screen
        screenElement.add(lbl);
    }

    @Override
    public void giveOutput(String output) {
        //sets the text of the label
        lbl.setText(output);
    }
}