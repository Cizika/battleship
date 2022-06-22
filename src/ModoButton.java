import javax.swing.*;
import java.awt.*;

public class ModoButton extends JButton  {
    boolean isSingleplayer;

    public ModoButton(boolean isSingleplayer) {
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setHorizontalTextPosition(JButton.LEFT);
        this.setVerticalAlignment(JButton.CENTER);
        this.setFont(new Font("Cooper Black", Font.BOLD, 20));
        this.setIconTextGap(15);
        this.setForeground(Color.black);
        this.setBackground(Color.yellow);

        if(isSingleplayer){
            this.setIcon(new ImageIcon("singleplayer.png"));
            this.setText("Singleplayer");
        } else{
            this.setIcon(new ImageIcon("multiplayer.png"));
            this.setText("Multiplayer");
        }
    }
}
