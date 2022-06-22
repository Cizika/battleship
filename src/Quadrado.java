import javax.swing.*;
import java.awt.*;

public class Quadrado extends JButton {
    private int coordenadaX;
    private int coordenadaY;
    private TipoQuadrado tipo;
    protected boolean isAtingido;

    public Quadrado(int coordenadaX, int coordenadaY) {
        this.isAtingido = false;
        this.tipo = TipoQuadrado.AGUA;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;

        this.setBackground(new Color(19, 139, 209));

        this.setPreferredSize(new Dimension(30, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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

    public void setCasco() {
        this.tipo = TipoQuadrado.CASCO;
        this.setBackground(new Color(73, 230, 11));
    }

    public void setProa() {
        this.tipo = TipoQuadrado.PROA;
        this.setBackground(new Color(118, 161, 10));
    }

    public void setAgua(){
        this.tipo = TipoQuadrado.AGUA;
        this.setBackground(new Color(19, 139, 209));
    }

    public boolean isAtingido() {
        return isAtingido;
    }

    public void atingido(){
        this.isAtingido = true;

        if(this.tipo == TipoQuadrado.AGUA)
            this.setBackground(new Color(32, 36, 33));
        else
            this.setBackground(new Color(209, 25, 47));
    }
}
