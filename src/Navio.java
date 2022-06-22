import java.awt.*;

public class Navio extends Quadrado{
    private  int tamanho;
    private boolean vertical;

    public Navio(int tamanho, int coordenadaX, int coordenadaY) {
        super(coordenadaX, coordenadaY);
        this.setBackground(new Color(73, 230, 11));
        this.tamanho = tamanho;
        this.vertical = true;
    }

    public void mudarSentido(){
        this.vertical = !this.vertical;
    }
//
//    // Esse método deveria estar em Grelha
//    public boolean navioAbatido(){
//        return this.atingido;
//    }
//
//    @Override
//    public void atingido() {
//
//    }
}
