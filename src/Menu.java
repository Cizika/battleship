import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {

    private final JLayeredPane mainPanel;
    private final Image logo;
    private final JButton singleplayerButton;
    private final JButton multiplayerButton;


    public Menu() {
        // Iniciando Componentes
        this.logo = new ImageIcon("logo.png").getImage();
        this.mainPanel = new JLayeredPane();
        mainPanel.setLayout(new OverlayLayout(mainPanel));

        // Adicionando Painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        buttonPanel.setOpaque(false);

        // Adicionando Botão de Singleplayer
        this.singleplayerButton = new ModoButton(true);
        this.singleplayerButton.addActionListener(this);

        // Adicionando Botão de Multiplayer
        this.multiplayerButton = new ModoButton(false);
        this.multiplayerButton.addActionListener(this);

        // Adicionando botões ao ButtonPanel
        buttonPanel.add(this.singleplayerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(this.multiplayerButton);

        // Adicionando MainPanel
        this.mainPanel.add(new MenuPanel(), Integer.valueOf(0));
        this.mainPanel.add(buttonPanel, Integer.valueOf(1));
        this.add(this.mainPanel);

        // Configurando Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Batalha Naval");
        this.setIconImage(this.logo);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.singleplayerButton) {
            // Questionando nome do Jogador
            String nome = JOptionPane.showInputDialog("Qual seu nome?");

            //  Iniciando Modo Singleplayer
            this.dispose();
            new Singleplayer(nome);

        } else if (e.getSource() == this.multiplayerButton) {
            // Modo Multiplayer ainda não implementado
            JOptionPane.showMessageDialog(null, "Multiplayer ainda não implementado!", "Multiplayer", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
