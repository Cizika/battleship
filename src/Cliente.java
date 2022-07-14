import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente extends MultiplayerFrame implements Runnable{

    private Socket socket;
    private boolean conectado;

    private ObjectOutputStream output;
    private ObjectInputStream input;


    public Cliente(String nome) {
        super(nome);
        this.conectado = false;

        // Desabilitando grelha inimiga
        this.tabuleiroOponente.habilitarGrelha(false);

        // Desabilitando Botão de Start => Somente o servidor pode iniciar
        this.startButton.setEnabled(false);

        try {
            // Conectando-se ao servidor
            this.socket = new Socket("localhost", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Iniciando Thread
        Thread t = new Thread(this);
        t.start();

        // Desconectando do servidor
//        desconectar();
    }

    @Override
    public void run() {
        conectado = true;
        while(conectado){
            try {
                // Obtendo fluxos de entrada e saída
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());

                // Enviando tabuleiro
                output.writeObject(this.tabuleiroUsuario.getJogador().getNome());
                output.flush();

                // Pegando tabuleiro inimigo
                if(input != null){
                    String nomeOponente = (String) input.readObject();
                    oponenteLabel.setText(nomeOponente);
                }
            } catch (IOException | ClassNotFoundException e) {
            }
        }
    }

    // Método para o cliente ser desconectado do servidor
    public void desconectar(){
        this.conectado = false;
        try {
            this.input.close();
            this.output.close();
            this.socket.close();
        } catch (IOException e) {
        }
    }

    // Método Estático para verificar se o Servidor existe
    public static boolean servidorEncontrado(){
        try (Socket s = new Socket("localhost", 8080)) {
            s.close();
            return true;
        } catch (IOException ex) {
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ação para Start Button
        if (e.getSource() == this.startButton)
            JOptionPane.showMessageDialog(this, "Somente o Servidor pode iniciar a partida", "Antes de Iniciar", JOptionPane.WARNING_MESSAGE);
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

                // Turno do Computador atirar
                this.tabuleiroUsuario.atirarAleatoriamente();

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
                    Navio navio = new Navio(quadrado, this.tabuleiroUsuario.getJogador().proximoNavioAPosicionar()); // Alterar lógica do tamanho do navio

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
                if (!this.tabuleiroUsuario.verificarSobreposicao(firstCasco, navio.getTamanho() - 1, !navio.isVertical())) {

                    // Rotacionando Navio no Tabuleiro
                    this.tabuleiroUsuario.rotacionarNavio(navio);
                }
            }
        }
    }
}

