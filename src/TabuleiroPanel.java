import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class TabuleiroPanel extends JPanel implements ActionListener {
    private final int dimensao = 10;
    private boolean gameStarted;
    private Jogador jogador;
    private TabuleiroPanel grelhaOponente;
    private ArrayList<Quadrado> grelha;

    public TabuleiroPanel(Jogador jogador) {
        // Setando Atributos
        this.gameStarted = false;
        this.grelha = new ArrayList<>();
        this.jogador = jogador;

        // Configurações do Panel
        this.setOpaque(false);
        this.setLayout(new GridLayout(dimensao, dimensao, 0, 0));

        for (int i = 0; i < this.dimensao; i++){
            for (int j = 0; j < this.dimensao; j++) {
                Quadrado quadrado = new Quadrado(j, i);

                quadrado.addActionListener(this);

                this.add(quadrado);

                this.grelha.add(quadrado);
            }
        }

        if(this.jogador instanceof Computador)
            habilitarGrelha(false);
    }

    public TabuleiroPanel getGrelhaOponente() {
        return grelhaOponente;
    }

    public void setGrelhaOponente(TabuleiroPanel grelhaOponente) {
        this.grelhaOponente = grelhaOponente;
    }

    public void habilitarGrelha(boolean enable){
        for(Quadrado quadrado: this.grelha)
            quadrado.setEnabled(enable);
    }

    public int getDimensao() {
        return dimensao;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    // Método para encontrar um Quadrado dentro da Grelha através das suas coordenadas
    public Quadrado findQuadrado(int x, int y){
        for (Quadrado quadrado: this.grelha) {
            if(quadrado.getCoordenadaX() == x && quadrado.getCoordenadaY() == y)
                return quadrado;
        }
        return null;
    }

    // Método que verifica se há sobreposição entre os Navios do Jogador
    public boolean verificarSobreposicao(Quadrado proa, int tamanho, boolean vertical){
        for (int i = 0; i < tamanho; i++) {
            Quadrado futuroCorpo = vertical ?
                    this.findQuadrado(proa.getCoordenadaX(), proa.getCoordenadaY() + i) :
                    this.findQuadrado(proa.getCoordenadaX() + i, proa.getCoordenadaY());
            if(futuroCorpo == null || futuroCorpo.getTipo() != TipoQuadrado.AGUA)
                return true;
        }
        return false;
    }

    // Método para posicionar um Navio no Tabuleiro conforme sua orientação
    public void posicionarNavio(Navio navio, boolean vertical){
        int newX = navio.getProa().getCoordenadaX();
        int newY = navio.getProa().getCoordenadaY();

        if(navio.getTamanho() > 0){
            for (int i = 1; i < navio.getTamanho(); i++) {
                Quadrado newCorpo = vertical ? this.findQuadrado(newX, newY + i) : this.findQuadrado(newX + i, newY);
                navio.adicionarCorpo(newCorpo);
            }
            this.jogador.tornarNavioOperante(navio);
        }
    }

    public void rotacionarNavio(Navio navio){
        if(navio.getTamanho() > 0) {
            for (int i = 1; i < navio.getTamanho(); i++) {
                Quadrado corpoRotacionado = navio.isVertical() ?
                        this.findQuadrado(navio.getCorpo().get(i).getCoordenadaX() + i, navio.getCorpo().get(i).getCoordenadaY() - i)
                        : this.findQuadrado(navio.getCorpo().get(i).getCoordenadaX() - i, navio.getCorpo().get(i).getCoordenadaY() + i);
                navio.trocarCorpo(corpoRotacionado, i);
            }
        }
    }

    public void esconderNavios(){
        for(Navio navio: this.jogador.getNaviosOperantes()){
            for(Quadrado quadrado: navio.getCorpo())
                quadrado.esconderQuadrado();
        }
    }

    // Posiciona Aleatoriamente os Navios no Tabuleiro
    public void posicionarAleatoriamente(){
        while(this.getJogador().getNaviosAPosicionar() > 0){
            int randomX = new Random().nextInt(this.getDimensao());
            int randomY = new Random().nextInt(this.getDimensao());
            boolean orientacao = new Random().nextBoolean();

            Quadrado proa = this.findQuadrado(randomX, randomY);

            if(!verificarSobreposicao(proa, this.getJogador().getNaviosAPosicionar(), orientacao)){
                Navio navio = new Navio(proa, this.getJogador().getNaviosAPosicionar(), orientacao);
                this.posicionarNavio(navio, navio.isVertical());
            }
        }
    }

    public void serAtingidoAleatoriamente(){
        int randomX = new Random().nextInt(this.getDimensao());
        int randomY = new Random().nextInt(this.getDimensao());

        Quadrado quadradoAtingido = this.findQuadrado(randomX, randomY);

        quadradoAtingido.atingido();

        if(quadradoAtingido.getTipo() != TipoQuadrado.AGUA){
            Navio navio = this.getJogador().getNavioByParte(quadradoAtingido);
            if(!navio.isOperante())
                this.getJogador().navioAbatido(navio);
            if(this.jogador.perdeu())
                JOptionPane.showMessageDialog(this, "Você perdeu! :(", "Fim de Jogo", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Mudar para SingleplayerFrame
    @Override
    public void actionPerformed(ActionEvent e) {
        Quadrado quadrado = (Quadrado) e.getSource();

        // Caso o Jogo tenha iniciado, o quadrado escolhido deve ser atingido
        if(gameStarted){
            quadrado.atingido();
            if(quadrado.getTipo() != TipoQuadrado.AGUA){
                Navio navio = this.getJogador().getNavioByParte(quadrado);
                if(!navio.isOperante())
                    this.getJogador().navioAbatido(navio);

                if(this.jogador.perdeu())
                    JOptionPane.showMessageDialog(this, "Você ganhou! :)", "Fim de Jogo", JOptionPane.WARNING_MESSAGE);
            }
            this.grelhaOponente.serAtingidoAleatoriamente();
        }
        // Jogo não iniciado
        // Ao clicar na água um novo Navio será posicionado
        else if(quadrado.getTipo() == TipoQuadrado.AGUA && this.jogador.getNaviosAPosicionar() > 0){

            // Verificando se haverá sobreposição entre o novo Navio e os já posicionados
            if(!this.verificarSobreposicao(quadrado, this.jogador.getNaviosAPosicionar(), true)){
                // Criando Navio com o quadrado como Proa
                Navio navio = new Navio(quadrado, this.jogador.getNaviosAPosicionar());

                // Posicionando Resto do Navio no Quadrado Escolhido
                this.posicionarNavio(navio, navio.isVertical());
            }
        }
        else if(quadrado.getTipo() == TipoQuadrado.PROA){
            // Coletando Navio a ser rotacionado
            Navio navio = this.jogador.getNavioByParte(quadrado);

            // Realizando Análise de Sobreposição para o Casco, pois a proa continuará no mesmo lugar
            Quadrado firstCasco = !navio.isVertical() ?
                    this.findQuadrado(quadrado.getCoordenadaX(), quadrado.getCoordenadaY() + 1) :
                    this.findQuadrado(quadrado.getCoordenadaX() + 1, quadrado.getCoordenadaY());;

            if(!this.verificarSobreposicao(firstCasco, navio.getTamanho() - 1, !navio.isVertical())){
                // Rotacionando Navio no Tabuleiro
                this.rotacionarNavio(navio);

                // Rotacionando orientação
                navio.rotacionar();
            }
        }
    }
}
