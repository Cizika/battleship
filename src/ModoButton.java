import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModoButton extends JButton implements ActionListener {
    boolean isSingleplayer;

    public ModoButton(boolean isSingleplayer) {

        this.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
