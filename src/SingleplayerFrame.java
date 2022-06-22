import javax.swing.*;
import java.awt.*;

public class SingleplayerFrame extends JFrame {

    private JLayeredPane mainPanel;
    private JPanel tabuleiroPanel;
    private GrelhaPanel usuarioGrelha;
    private GrelhaPanel oponenteGrelha;

    public SingleplayerFrame(String nome) {
        // Iniciando mainPanel
        this.mainPanel = new JLayeredPane();
        mainPanel.setBackground(new Color(23, 207, 207));
        mainPanel.setLayout(new BorderLayout(10, 40));

        // Iniciando tabuleiroPanel
        this.tabuleiroPanel = new JPanel();
        this.tabuleiroPanel.setOpaque(false);
        this.tabuleiroPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));

        // Inicializando Tabuleiros dos Jogadores
        this.usuarioGrelha = new GrelhaPanel(new Usuario(nome));
        this.oponenteGrelha = new GrelhaPanel(new Computador());

        // Nome do Computador
        JLabel oponenteLabel = new JLabel();
        oponenteLabel.setText(this.oponenteGrelha.getJogador().getNome());
        oponenteLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        oponenteLabel.setForeground(Color.WHITE);
        oponenteLabel.setHorizontalAlignment(JLabel.CENTER);

        // Nome do Usuário
        JLabel usuarioLabel = new JLabel();
        usuarioLabel.setText(this.usuarioGrelha.getJogador().getNome());
        usuarioLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setHorizontalAlignment(JLabel.CENTER);

        // Adicionando tabuleiros ao TabuleiroPanel
        tabuleiroPanel.add(this.oponenteGrelha);
        tabuleiroPanel.add(this.usuarioGrelha);

        // Adicionando componentes ao MainPanel
        mainPanel.add(oponenteLabel, BorderLayout.NORTH);
        mainPanel.add(usuarioLabel,BorderLayout.SOUTH);
        mainPanel.add(this.tabuleiroPanel, BorderLayout.CENTER);

        // Adicionando MainPanel ao Frame
        this.add(mainPanel);

        // Configurando Frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 900);
        this.setTitle("Batalha Naval");
        this.setIconImage(new ImageIcon("logo.png").getImage());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(8, 103, 158));
        this.setVisible(true);

        // Iniciando Partida
//        this.iniciarPartida();
    }

    private void posicionarNavios(){

    }

    public void iniciarPartida(){

        this.oponenteGrelha.setGameStarted(true);
        this.usuarioGrelha.setGameStarted(true);

        // Enquanto ninguém perdeu, o jogo continua
        while(!this.usuarioGrelha.getJogador().perdeu() && !this.oponenteGrelha.getJogador().perdeu()){

        }

    }
}
