import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SingleplayerFrame extends JFrame implements ActionListener {

    private JLayeredPane mainPanel;
    private JPanel buttonPanel;
    private JPanel tabuleiroPanel;
    private TabuleiroPanel usuarioGrelha;
    private TabuleiroPanel oponenteGrelha;
    private JButton startButton;
    private Font startFont;

    public SingleplayerFrame(String nome) {
        // Iniciando mainPanel
        this.mainPanel = new JLayeredPane();
        mainPanel.setBackground(new Color(23, 207, 207));
        mainPanel.setLayout(new BorderLayout(10, 40));

        // Iniciando buttonPanel
        this.buttonPanel = new JPanel();
        this.buttonPanel.setOpaque(false);
        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Iniciando tabuleiroPanel
        this.tabuleiroPanel = new JPanel();
        this.tabuleiroPanel.setOpaque(false);
        this.tabuleiroPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        // Inicializando Tabuleiros dos Jogadores
        Usuario usuario = new Usuario(nome);
        Computador computador = new Computador();
        this.usuarioGrelha = new TabuleiroPanel(usuario);
        this.oponenteGrelha = new TabuleiroPanel(computador);
        this.usuarioGrelha.setGrelhaOponente(oponenteGrelha);
        this.oponenteGrelha.setGrelhaOponente(usuarioGrelha);

        // Nome do Computador
        JLabel oponenteLabel = new JLabel();
        oponenteLabel.setText(this.oponenteGrelha.getJogador().getNome());
        oponenteLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        oponenteLabel.setForeground(Color.WHITE);
        oponenteLabel.setHorizontalAlignment(JLabel.CENTER);
        oponenteLabel.setVerticalAlignment(JLabel.CENTER);

        // Nome do Usuário
        JLabel usuarioLabel = new JLabel();
        usuarioLabel.setText(this.usuarioGrelha.getJogador().getNome());
        usuarioLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setHorizontalAlignment(JLabel.CENTER);
        usuarioLabel.setVerticalAlignment(JLabel.CENTER);

        // Adicionando tabuleiros ao TabuleiroPanel
        tabuleiroPanel.add(this.oponenteGrelha);
        tabuleiroPanel.add(this.usuarioGrelha);

        // Adicionando botão ao buttonPanel
        this.startButton = new JButton();
        startButton.setText("Iniciar");
        try {
            this.startFont = Font.createFont(Font.TRUETYPE_FONT, new File("StartFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.startFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        startButton.setFont(this.startFont.deriveFont(20f));
        startButton.setBackground(Color.PINK);
        startButton.setForeground(Color.BLACK);
        startButton.setPreferredSize(new Dimension(200, 80));
        startButton.setFocusable(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(this);

        // Adicionando botão ao buttonPanel
        this.buttonPanel.add(Box.createRigidArea(new Dimension(0, 650)));
        this.buttonPanel.add(startButton);
        this.buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Adicionando componentes ao MainPanel
        mainPanel.add(oponenteLabel, BorderLayout.NORTH);
        mainPanel.add(usuarioLabel,BorderLayout.SOUTH);
        mainPanel.add(this.tabuleiroPanel, BorderLayout.CENTER);
        mainPanel.add(this.buttonPanel, BorderLayout.EAST);

        // Adicionando MainPanel ao Frame
        this.add(mainPanel);

        // Configurando Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 900);
        this.setTitle("Batalha Naval");
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(8, 103, 158));
        this.setVisible(true);
    }

    public void iniciarPartida(){

        // Habilitando clicks na Grelha do Oponente
        this.oponenteGrelha.habilitarGrelha(true);
        this.usuarioGrelha.habilitarGrelha(false);

        // Computador Posiciona Navios Aleatoriamente
        this.oponenteGrelha.posicionarAleatoriamente();
        this.oponenteGrelha.esconderNavios();

        // Iniciando jogo para Ambos os Jogadores
        this.usuarioGrelha.setGameStarted(true);
        this.oponenteGrelha.setGameStarted(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.startButton){
            if(this.usuarioGrelha.getJogador().getNaviosAPosicionar() == 0){
                this.startButton.setVisible(false);
                iniciarPartida();
            } else
                JOptionPane.showMessageDialog(this, "Posicione todos seus Navios!", "Antes de Iniciar", JOptionPane.WARNING_MESSAGE);
        }
    }
}
