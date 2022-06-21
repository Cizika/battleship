import javax.swing.*;
import java.awt.*;

public class Tabuleiro extends JFrame {
    JPanel backgroundPanel;
    JPanel textoPanel;
    JPanel buttonsPanel;

    public Tabuleiro() {
        // Imagem de Fundo Conteúdo
        ImageIcon backgroundIcon = new ImageIcon("background.jpg");
        JLabel background = new JLabel();
        background.setIcon(backgroundIcon);
        background.setHorizontalAlignment(JLabel.CENTER);

        // Painel com Imagem de Fundo
        this.backgroundPanel = new JPanel();
        this.backgroundPanel.setBounds(0, 0, 3000, 700);
        this.backgroundPanel.setLayout(new BorderLayout());

        // Conteúdo Texto
        JLabel texto = new JLabel();
        texto.setText("ESCOLHA UM MODO DE JOGO");
        texto.setFont(new Font("Comic Sans", Font.BOLD, 25));
        texto.setForeground(new Color(70 ,208, 130));
        texto.setHorizontalAlignment(JLabel.CENTER);

        // Painel com Texto
        this.textoPanel = new JPanel();
        this.textoPanel.setBounds(350, 80, 500, 50);
        this.textoPanel.setOpaque(false);
        this.textoPanel.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 700);
        this.setTitle("Battleship");
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.setLocationRelativeTo(null);

        this.textoPanel.add(texto);
        this.backgroundPanel.add(background);

        this.add(this.textoPanel);
        this.add(this.backgroundPanel);
        this.setVisible(true);
    }
}
