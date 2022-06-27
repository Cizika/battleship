import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private final Image backgroundImage;
    private Font warFont;

    public MenuPanel() {

        // Configurando imagem de fundo
        this.backgroundImage = new ImageIcon("background.jpg").getImage();
        this.setPreferredSize(new Dimension(1050, 600));

        // Criando fonte personalizada
        try {
            this.warFont = Font.createFont(Font.TRUETYPE_FONT, new File("WarFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.warFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    // Método para "desenhar" elementos na tela
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        Dimension d = this.getSize();

        // Desenhando imagem de fundo
        g2D.drawImage(this.backgroundImage, 0, 0, d.width, d.height, this);

        // Desenhando Texto de Battleship
        String battleship = "BATTLESHIP";
        g2D.setPaint(Color.BLACK);
        g2D.setFont(this.warFont.deriveFont(80f));
        g2D.drawString(battleship, (d.width - g2D.getFontMetrics().stringWidth(battleship)) / 2, 100);

        // Desenhando Texto de Modo de Jogo
        String modo = "Escolha o modo de jogo!";
        g2D.setPaint(Color.BLACK);
        g2D.setFont(this.warFont.deriveFont(30f));
        g2D.setStroke(new BasicStroke(22));
        g2D.drawString(modo, (d.width - g2D.getFontMetrics().stringWidth(modo)) / 2, 150);
    }
}
