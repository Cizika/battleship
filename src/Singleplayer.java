import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Singleplayer extends JFrame implements ActionListener {

    private final JLayeredPane mainPanel;
    private final JPanel buttonPanel;
    private final JPanel tabuleirosPanel;
    private final Tabuleiro tabuleiroUsuario;
    private final Tabuleiro tabuleiroComputador;
    private final JButton startButton;
    private boolean gameStarted;
    private Font startFont;

    public Singleplayer(String nome) {
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

        // Inicializando Tabuleiro do Usuário
        Usuario usuario = new Usuario(nome);
        this.tabuleiroUsuario = new Tabuleiro(usuario, this);

        // Inicializando Tabuleiro do Computador
        Computador computador = new Computador();
        this.tabuleiroComputador = new Tabuleiro(computador, this);

        // Exibindo Nome do Computador
        JLabel oponenteLabel = new JLabel();
        oponenteLabel.setText(this.tabuleiroComputador.getJogador().getNome());
        oponenteLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        oponenteLabel.setForeground(Color.WHITE);
        oponenteLabel.setHorizontalAlignment(JLabel.CENTER);
        oponenteLabel.setVerticalAlignment(JLabel.CENTER);

        // Exibindo Nome do Usuário
        JLabel usuarioLabel = new JLabel();
        usuarioLabel.setText(this.tabuleiroUsuario.getJogador().getNome());
        usuarioLabel.setFont(new Font("MV Boli", Font.PLAIN, 30));
        usuarioLabel.setForeground(Color.WHITE);
        usuarioLabel.setHorizontalAlignment(JLabel.CENTER);
        usuarioLabel.setVerticalAlignment(JLabel.CENTER);

        // Adicionando tabuleiros ao TabuleiroPanel
        this.tabuleirosPanel.add(this.tabuleiroComputador);
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
        startButton.setPreferredSize(new Dimension(200, 80));
        startButton.setFocusable(false);
        startButton.setFocusPainted(false);
        startButton.addActionListener(this);

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

    // Método para configurar início da partida
    public void iniciarPartida() {

        // Escondendo botão de Start
        this.startButton.setVisible(false);

        // Habilitando clicks na Grelha do Oponente
        this.tabuleiroComputador.habilitarGrelha(true);

        // Desabilitando clicks na Grelha do Usuário
        this.tabuleiroUsuario.habilitarGrelha(false);

        // Computador Posiciona Navios Aleatoriamente
        this.tabuleiroComputador.posicionarAleatoriamente();

        // Escondendo Navios do Usuário
        this.tabuleiroComputador.esconderNavios();

        // Iniciando Game
        this.gameStarted = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Ação para Start Button
        if (e.getSource() == this.startButton) {
            // Caso todos os navios tenham sido posicionados pelo usuário, o jogo inicia
            if (this.tabuleiroUsuario.getJogador().getNaviosAPosicionar().isEmpty())
                iniciarPartida();
            else
                JOptionPane.showMessageDialog(this, "Posicione todos seus Navios!", "Antes de Iniciar", JOptionPane.WARNING_MESSAGE);
        }
        // Ação realizada sobre quadrado do tabuleiro
        else if (e.getSource() instanceof Quadrado) {
            Quadrado quadrado = (Quadrado) e.getSource();

            // Caso o Jogo tenha iniciado, o quadrado selecionado deve ser atingido
            if (this.gameStarted) {

                // Atingindo quadrado selecionado
                quadrado.atingido();

                // Caso seja um navio do oponente, ele deve ser abatido
                if (quadrado.getTipo() != TipoQuadrado.AGUA) {

                    // Coletando navio pelo quadrado selecionado
                    Navio navio = this.tabuleiroComputador.getJogador().getNavioByParte(quadrado);

                    // Verificando se o navio foi abatido, isto é, todos seus quadrados foram atingidos
                    if (!navio.isOperante())
                        this.tabuleiroComputador.getJogador().navioAbatido(navio);

                    // Verificando se o computador perdeu o jogo
                    if (this.tabuleiroComputador.getJogador().perdeu()) {
                        String vencedor = this.tabuleiroUsuario.getJogador().getNome();
                        JOptionPane.showMessageDialog(this, vencedor + " é o grande vencedor!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }

                // Turno do Computador atirar
                this.tabuleiroUsuario.atirarAleatoriamente();

                // Verificando se o usuário perdeu o jogo
                if (this.tabuleiroUsuario.getJogador().perdeu()) {
                    String vencedor = this.tabuleiroComputador.getJogador().getNome();
                    JOptionPane.showMessageDialog(this, vencedor + " é o grande vencedor!", "Fim de Jogo", JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                }
            }
            // Jogo não iniciado
            // Ao clicar na água um novo Navio será posicionado
            else if (quadrado.getTipo() == TipoQuadrado.AGUA && !this.tabuleiroUsuario.getJogador().getNaviosAPosicionar().isEmpty()) {

                // Verificando se haverá sobreposição entre o novo Navio e os já posicionados
                if (!this.tabuleiroUsuario.verificarSobreposicao(quadrado, this.tabuleiroUsuario.getJogador().proximoNavioAPosicionar(), true)) {

                    // Criando Navio com o quadrado como Proa
                    Navio navio = new Navio(quadrado, this.tabuleiroUsuario.getJogador().proximoNavioAPosicionar());

                    // Posicionando Resto do Navio na vertical
                    this.tabuleiroUsuario.posicionarNavio(navio, navio.isVertical());
                }
            }
            // Ao clicar em uma Proa, o Navio será rotacionado
            else if (quadrado.getTipo() == TipoQuadrado.PROA) {

                // Coletando Navio a ser rotacionado
                Navio navio = this.tabuleiroUsuario.getJogador().getNavioByParte(quadrado);

                // Realizando Análise de Sobreposição para o Casco, pois a Proa continuará no mesmo lugar após a rotação
                Quadrado firstCasco = navio.isVertical() ?
                        this.tabuleiroUsuario.findQuadrado(quadrado.getCoordenadaX() + 1, quadrado.getCoordenadaY()) :
                        this.tabuleiroUsuario.findQuadrado(quadrado.getCoordenadaX(), quadrado.getCoordenadaY() + 1);

                // Verificando se após a rotação haverá sobreposição entre os Navios
                if (firstCasco != null && !this.tabuleiroUsuario.verificarSobreposicao(firstCasco, navio.getTamanho() - 1, !navio.isVertical())) {

                    // Rotacionando Navio no Tabuleiro
                    this.tabuleiroUsuario.rotacionarNavio(navio);
                }
            }
        }
    }
}
