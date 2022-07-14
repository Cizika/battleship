import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public abstract class MultiplayerFrame extends JFrame implements ActionListener{

    protected final JLayeredPane mainPanel;
    protected final JPanel buttonPanel;
    protected final JPanel tabuleirosPanel;

    protected final Tabuleiro tabuleiroUsuario;
    protected Tabuleiro tabuleiroOponente;

    protected final JButton startButton;
    protected JLabel usuarioLabel;
    protected JLabel oponenteLabel;

    protected boolean gameStarted;
    protected Font startFont;

    public MultiplayerFrame(String nome) {
        // Iniciando mainPanel
        this.mainPanel = new JLayeredPane();
        mainPanel.setBackground(new Color(23, 207, 207));
        mainPanel.setLayout(new BorderLayout(10, 40));

        // Iniciando buttonPanel
        this.buttonPanel = new JPanel();
        this.buttonPanel.setOpaque(false);
        this.buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        // Iniciando o Painel de Tabuleiros
        this.tabuleirosPanel = new JPanel();
        this.tabuleirosPanel.setOpaque(false);
        this.tabuleirosPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        // Inicializando Tabuleiro do Servidor
        Usuario usuario = new Usuario(nome);
        this.tabuleiroUsuario = new Tabuleiro(usuario, this);

        // Inicializando Tabuleiro do Cliente
        Usuario cliente = new Usuario("AGUARDANDO JOGADOR");
        this.tabuleiroOponente = new Tabuleiro(cliente, this);

        // Exibindo Nome do Computador
        oponenteLabel = new JLabel();
        oponenteLabel.setText(this.tabuleiroOponente.getJogador().getNome());
        oponenteLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        oponenteLabel.setForeground(Color.WHITE);
        oponenteLabel.setHorizontalAlignment(JLabel.CENTER);
        oponenteLabel.setVerticalAlignment(JLabel.CENTER);

        // Exibindo Nome do Usuário
        usuarioLabel = new JLabel();
        usuarioLabel.setText(this.tabuleiroUsuario.getJogador().getNome());
        usuarioLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setHorizontalAlignment(JLabel.CENTER);
        usuarioLabel.setVerticalAlignment(JLabel.CENTER);

        // Adicionando tabuleiros ao TabuleiroPanel
        this.tabuleirosPanel.add(this.tabuleiroOponente);
        this.tabuleirosPanel.add(this.tabuleiroUsuario);

        // Configurando botão de start
        this.startButton = new JButton();
        startButton.setText("Iniciar");
        try {
            this.startFont = Font.createFont(Font.TRUETYPE_FONT, new File("media/StartFont.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(this.startFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        startButton.setFont(this.startFont.deriveFont(20f));
        startButton.setBackground(Color.PINK);
        startButton.setForeground(Color.BLACK);
        startButton.addActionListener(this);
        startButton.setPreferredSize(new Dimension(200, 80));
        startButton.setFocusable(false);
        startButton.setFocusPainted(false);

        // Adicionando botão de start ao buttonPanel
        this.buttonPanel.add(Box.createRigidArea(new Dimension(0, 650)));
        this.buttonPanel.add(startButton);
        this.buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Adicionando componentes ao MainPanel
        mainPanel.add(oponenteLabel, BorderLayout.NORTH);
        mainPanel.add(usuarioLabel, BorderLayout.SOUTH);
        mainPanel.add(this.tabuleirosPanel, BorderLayout.CENTER);
        mainPanel.add(this.buttonPanel, BorderLayout.EAST);

        // Adicionando MainPanel ao Frame
        this.add(mainPanel);

        // Configurando Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 900);
        this.setTitle("Batalha Naval");
        this.setIconImage(new ImageIcon("media/logo.png").getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(8, 103, 158));
        this.setVisible(true);
    }

    // Seta o Tabuleiro do Oponente do presente GameFrame
    public void setTabuleiroOponente(Tabuleiro tabuleiroOponente) {
        this.tabuleiroOponente = tabuleiroOponente;
    }
}
