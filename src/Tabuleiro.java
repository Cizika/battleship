import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro extends JPanel {
    private final int dimensao = 10;
    private final ArrayList<Quadrado> grelha;
    private Jogador jogador;

    public Tabuleiro(Jogador jogador, ActionListener frame) {

        // Iniciando tabuleiro do jogador
        this.jogador = jogador;

        // Configurando visualmente Tabuleiro
        this.setOpaque(false);
        this.setLayout(new GridLayout(dimensao, dimensao, 0, 0));

        // Criando Grelha
        this.grelha = new ArrayList<>();

        // Gerando (dimensao * dimensao) quadrados para grelha
        for (int i = 0; i < this.dimensao; i++) {
            for (int j = 0; j < this.dimensao; j++) {
                Quadrado quadrado = new Quadrado(j, i);

                quadrado.addActionListener(frame);

                this.add(quadrado);

                this.grelha.add(quadrado);
            }
        }

        // Não permitir clicks no tabuleiro do computador
        if (jogador instanceof Computador)
            this.habilitarGrelha(false);
    }

    // Método para habilitar ou desabilitar grelha
    public void habilitarGrelha(boolean enable) {
        for (Quadrado quadrado : this.grelha)
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

    // Método para encontrar um Quadrado dentro da Grelha através das suas coordenadas
    public Quadrado findQuadrado(int x, int y) {
        for (Quadrado quadrado : this.grelha) {
            if (quadrado.getCoordenadaX() == x && quadrado.getCoordenadaY() == y)
                return quadrado;
        }
        return null;
    }

    // Método que verifica se há sobreposição entre os Navios do Jogador
    public boolean verificarSobreposicao(Quadrado proa, int tamanho, boolean vertical) {
        for (int i = 0; i < tamanho; i++) {
            Quadrado futuroCorpo = vertical ?
                    this.findQuadrado(proa.getCoordenadaX(), proa.getCoordenadaY() + i) :
                    this.findQuadrado(proa.getCoordenadaX() + i, proa.getCoordenadaY());
            if (futuroCorpo == null || futuroCorpo.getTipo() != TipoQuadrado.AGUA)
                return true;
        }
        return false;
    }

    // Método para posicionar um Navio no Tabuleiro conforme sua orientação
    public void posicionarNavio(Navio navio, boolean vertical) {
        int newX = navio.getProa().getCoordenadaX();
        int newY = navio.getProa().getCoordenadaY();

        if (navio.getTamanho() > 0) {
            for (int i = 1; i < navio.getTamanho(); i++) {
                Quadrado newCorpo = vertical ? this.findQuadrado(newX, newY + i) : this.findQuadrado(newX + i, newY);
                navio.adicionarCorpo(newCorpo);
            }
            this.getJogador().tornarNavioOperante(navio);
        }
    }

    // Método para rotacionar o Navio no Tabuleiro
    public void rotacionarNavio(Navio navio) {
        if (navio.getTamanho() > 0) {
            for (int i = 1; i < navio.getTamanho(); i++) {
                Quadrado corpoRotacionado = navio.isVertical() ?
                        this.findQuadrado(navio.getCorpo().get(i).getCoordenadaX() + i, navio.getCorpo().get(i).getCoordenadaY() - i)
                        : this.findQuadrado(navio.getCorpo().get(i).getCoordenadaX() - i, navio.getCorpo().get(i).getCoordenadaY() + i);
                navio.trocarCorpo(corpoRotacionado, i);
            }
            // Alterando orientação
            navio.rotacionar();
        }
    }

    // Método para Esconder os Navios do Jogador Adversário
    public void esconderNavios() {
        for (Navio navio : this.getJogador().getNaviosOperantes()) {
            for (Quadrado quadrado : navio.getCorpo())
                quadrado.esconderQuadrado();
        }
    }

    // Posiciona Aleatoriamente os Navios no Tabuleiro
    public void posicionarAleatoriamente() {
        while (!this.getJogador().getNaviosAPosicionar().isEmpty()) {
            int randomX = new Random().nextInt(this.getDimensao());
            int randomY = new Random().nextInt(this.getDimensao());
            boolean orientacao = new Random().nextBoolean();

            Quadrado proa = this.findQuadrado(randomX, randomY);

            if (!verificarSobreposicao(proa, this.getJogador().proximoNavioAPosicionar(), orientacao)) {
                Navio navio = new Navio(proa, this.getJogador().proximoNavioAPosicionar(), orientacao);
                this.posicionarNavio(navio, navio.isVertical());
            }
        }
    }

    // Método para atingir aleatoriamente um quadrado do tabuleiro
    public void atirarAleatoriamente() {
        int randomX = new Random().nextInt(this.getDimensao());
        int randomY = new Random().nextInt(this.getDimensao());

        Quadrado quadradoAtingido = this.findQuadrado(randomX, randomY);

        // Gerando quadrado aleatório até ele ser válido
        while (quadradoAtingido.isAtingido()) {
            randomX = new Random().nextInt(this.getDimensao());
            randomY = new Random().nextInt(this.getDimensao());
            quadradoAtingido = this.findQuadrado(randomX, randomY);
        }

        // Atigindo quadrado
        quadradoAtingido.atingido();

        // Caso tenha atingido um quadrado Navio, ele deve ser abatido
        if (quadradoAtingido.getTipo() != TipoQuadrado.AGUA) {

            // Coletando navio através do quadrado atingido
            Navio navio = this.getJogador().getNavioByParte(quadradoAtingido);

            // Verificando se o navio foi abatido, isto é, todos seus quadrados foram atingidos
            if (!navio.isOperante())
                this.getJogador().navioAbatido(navio);
        }
    }
}
