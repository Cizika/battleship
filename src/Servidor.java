import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor extends MultiplayerFrame implements Runnable {

    private ServerSocket serverSocket;
    private Socket clienteSocket;

    private boolean ligado;

    private ObjectOutputStream output;
    private ObjectInputStream input;


    public Servidor(String nome) {
        super(nome);
        this.ligado = false;

        // Desabilitando grelha inimiga
        this.tabuleiroOponente.habilitarGrelha(false);

        try {
            // Criando servidor na porta 8080
            this.serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ligar servidor
    public void ligar(){
        this.ligado = true;

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while(ligado){
            try {
                // Esperando conexão do cliente
                clienteSocket = serverSocket.accept();

                // Obtendo fluxos de entrada e saída
                input = new ObjectInputStream(clienteSocket.getInputStream());
                output = new ObjectOutputStream(clienteSocket.getOutputStream());

                // Recebendo tabuleiro inimigo
                if(input != null){
                    String tabuleiroOponente = (String) input.readObject();
                    oponenteLabel.setText(tabuleiroOponente);
                }

                // Enviando tabuleiro
                output.writeObject(this.tabuleiroUsuario.getJogador().getNome());
                output.flush();

            } catch (IOException | ClassNotFoundException e) {
            }
        }
    }

    // Iniciando partida
    public void iniciarPartida() {

        // Escondendo botão de Start
        this.startButton.setVisible(false);

        // Habilitando clicks na Grelha do Oponente
        this.tabuleiroOponente.habilitarGrelha(true);

        // Desabilitando clicks na Grelha do Usuário
        this.tabuleiroUsuario.habilitarGrelha(false);

        // Computador Posiciona Navios Aleatoriamente
        this.tabuleiroOponente.posicionarAleatoriamente();

        // Escondendo Navios do Usuário
        this.tabuleiroOponente.esconderNavios();

        // Iniciando Game
        this.gameStarted = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ação para Start Button
        if (e.getSource() == this.startButton) {
            // Caso todos os navios tenham sido posicionados pelos dois usuários, o jogo inicia
            if (this.tabuleiroUsuario.getJogador().getNaviosAPosicionar().isEmpty() &&
                    tabuleiroOponente.getJogador().getNaviosAPosicionar().isEmpty())
                iniciarPartida();
            else
                JOptionPane.showMessageDialog(this, "Todos os navios devem ser posicionados", "Antes de Iniciar", JOptionPane.WARNING_MESSAGE);
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
                    Navio navio = this.tabuleiroOponente.getJogador().getNavioByParte(quadrado);

                    // Verificando se o navio foi abatido, isto é, todos seus quadrados foram atingidos
                    if (!navio.isOperante())
                        this.tabuleiroOponente.getJogador().navioAbatido(navio);

                    // Verificando se o computador perdeu o jogo
                    if (this.tabuleiroOponente.getJogador().perdeu()) {
                        String vencedor = this.tabuleiroUsuario.getJogador().getNome();
                        JOptionPane.showMessageDialog(this, vencedor + " é o grande vencedor!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                }

                // Verificando se o usuário perdeu o jogo
                if (this.tabuleiroUsuario.getJogador().perdeu()) {
                    String vencedor = this.tabuleiroOponente.getJogador().getNome();
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
