import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GrelhaPanel extends JPanel implements ActionListener {
    private final int dimensao = 10;
    private boolean gameStarted;
    private Jogador jogador;
    private ArrayList<Quadrado> quadrados;

    public GrelhaPanel(Jogador jogador) {
        // Setando Atributos
        this.gameStarted = false;
        this.jogador = jogador;
        this.quadrados = new ArrayList<>();

        // Configurações do Panel
        this.setOpaque(false);
        this.setLayout(new GridLayout(dimensao, dimensao, 0, 0));

        for (int i = 0; i < this.dimensao; i++){
            for (int j = 0; j < this.dimensao; j++) {
                Quadrado quadrado = new Quadrado(j, i);

                if(this.jogador instanceof Computador)
                    quadrado.setEnabled(false);

                quadrado.addActionListener(this);

                this.add(quadrado);

                this.quadrados.add(quadrado);
            }
        }
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

    public Quadrado findQuadrado(int x, int y){
        for (Quadrado quadrado: this.quadrados) {
            if(quadrado.getCoordenadaX() == x && quadrado.getCoordenadaY() == y)
                return quadrado;
        }
        return null;
    }

    public void posicionarNavio(Quadrado quadrado){
        if(this.jogador.naviosAPoscionar > 0){
            quadrado.setProa();

            for (int i = 1; i < this.jogador.naviosAPoscionar; i++) {
                Quadrado corpoNavio = findQuadrado(quadrado.getCoordenadaX(), quadrado.getCoordenadaY() + i);
                corpoNavio.setCasco();
            }
            this.jogador.navioPoscionado();
        }
    }

    public void rotacionarNavio(Quadrado quadrado){
        int i = 1;
        int oldX = quadrado.getCoordenadaX();
        int oldY = quadrado.getCoordenadaY();

        // Verificando se Navio está na Vertical
        if(findQuadrado(oldX, oldY + 1).getTipo() == TipoQuadrado.CASCO){
            Quadrado oldNavio = findQuadrado(oldX, ++oldY);

            // Rotacionando para Horizontal
            while(oldNavio.getTipo() == TipoQuadrado.CASCO){
                Quadrado newNavio = findQuadrado(oldX + i, oldY - i);

                // Realizar Trocar
                oldNavio.setAgua();
                newNavio.setCasco();

                // Próximo Casco/Corpo do Navio
                oldNavio = findQuadrado(oldX, ++oldY);
                i++;
            }
        }
        // Verificando se Navio está na Horizontal
        else if(findQuadrado(oldX + 1, oldY ).getTipo() == TipoQuadrado.CASCO){
            Quadrado oldNavio = findQuadrado(++oldX, oldY);

            // Rotacionando para Vertical
            while(oldNavio.getTipo() == TipoQuadrado.CASCO){
                Quadrado newNavio = findQuadrado(oldX - i, oldY + i);

                // Realizar Trocar
                oldNavio.setAgua();
                newNavio.setCasco();

                // Próximo Casco/Corpo do Navio
                oldNavio = findQuadrado(++oldX, oldY);
                i++;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Quadrado quadrado = (Quadrado) e.getSource();

        if(gameStarted)
            quadrado.atingido();
        else if(quadrado.getTipo() == TipoQuadrado.AGUA){
            // Poscionar Navio
            posicionarNavio(quadrado);
        }
        else if(quadrado.getTipo() == TipoQuadrado.PROA){
            // Rotacionar Navio
            rotacionarNavio(quadrado);
        }
    }
}
