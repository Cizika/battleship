import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel implements ActionListener {

    private Image backgroundImage;
    private Font warFont;

    public MenuPanel() {
        this.backgroundImage = new ImageIcon("background.jpg").getImage();
        this.setPreferredSize(new Dimension(1050, 600));

        try {
            this.warFont = Font.createFont(Font.TRUETYPE_FONT, new File("WarFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.warFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension d = this.getSize();

        // BackgroundImage
        g2D.drawImage(this.backgroundImage, 0, 0, d.width, d.height, this);

        // Texto de Battleship
        String battleship = "BATTLESHIP";
        g2D.setPaint(Color.BLACK);
        g2D.setFont(this.warFont.deriveFont(80f));
        g2D.drawString(battleship, (d.width - g2D.getFontMetrics().stringWidth(battleship)) / 2, 100);

        // Texto de Modo de Jogo
        String modo = "Escolha o modo de jogo!";
        g2D.setPaint(Color.BLACK);
        g2D.setFont(this.warFont.deriveFont(30f));
        g2D.setStroke(new BasicStroke(22));
        g2D.drawString(modo, (d.width - g2D.getFontMetrics().stringWidth(modo)) / 2, 150);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
