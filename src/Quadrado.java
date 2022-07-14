import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Quadrado extends JButton implements Serializable {
    private int coordenadaX;
    private int coordenadaY;
    private TipoQuadrado tipo;
    private boolean isAtingido;

    public Quadrado(int coordenadaX, int coordenadaY) {
        this.isAtingido = false;
        this.tipo = TipoQuadrado.AGUA;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;

        this.setBackground(new Color(19, 139, 209));

        this.setPreferredSize(new Dimension(30, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }

    public boolean isAtingido() {
        return isAtingido;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public TipoQuadrado getTipo() {
        return tipo;
    }

    // Método para esconder Quadrado do Oponente
    public void esconderQuadrado() {
        this.setBackground(new Color(19, 139, 209));
    }

    // Configurar visualmente Casco do Navio
    public void setCasco() {
        this.tipo = TipoQuadrado.CASCO;
        this.setBackground(new Color(73, 230, 11));
    }

    // Configurar visualmente Proa do Navio
    public void setProa() {
        this.tipo = TipoQuadrado.PROA;
        this.setBackground(new Color(118, 161, 10));
    }

    // Configurar visualmente Água
    public void setAgua() {
        this.tipo = TipoQuadrado.AGUA;
        this.setBackground(new Color(19, 139, 209));
    }

    // Mudando quadrado quando atingido
    public void atingido() {
        this.isAtingido = true;
        this.setEnabled(false);

        if (this.tipo != TipoQuadrado.AGUA)
            this.setBackground(new Color(209, 25, 47));
        else
            this.setBackground(new Color(32, 36, 33));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quadrado) {
            Quadrado quadrado = (Quadrado) o;
            return quadrado.getCoordenadaX() == this.coordenadaX && quadrado.getCoordenadaY() == this.coordenadaY;
        }
        return false;
    }
}
