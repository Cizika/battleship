import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Jogador implements Serializable {
    private final ArrayList<Navio> naviosOperantes;
    private final ArrayList<Integer>  naviosAPosicionar;
    private String nome;

    public Jogador() {
        this.nome = "";
        this.naviosOperantes = new ArrayList<>();
        this.naviosAPosicionar = new ArrayList<>();
        Collections.addAll(naviosAPosicionar, 5, 4, 4, 3, 3, 3, 2, 2, 2, 2);
    }

    public Jogador(String nome) {
        this.nome = nome;
        this.naviosOperantes = new ArrayList<>();
        this.naviosAPosicionar = new ArrayList<>();
        Collections.addAll(naviosAPosicionar, 5, 4, 4, 3, 3, 3, 2, 2, 2, 2);
    }

    public ArrayList<Integer> getNaviosAPosicionar() {
        return naviosAPosicionar;
    }

    public int proximoNavioAPosicionar() {
        return naviosAPosicionar.get(0);
    }

    public ArrayList<Navio> getNaviosOperantes() {
        return naviosOperantes;
    }

    // Método para recuperar um Navio através de um Quadrado que o compõe
    public Navio getNavioByParte(Quadrado parte) {
        for (Navio navio : this.naviosOperantes) {
            if (navio.containsQuadrado(parte)) return navio;
        }
        return null;
    }

    // Uma vez posicionado, o navio se torna Operante
    protected void tornarNavioOperante(Navio navio) {
        this.naviosOperantes.add(navio);
        this.naviosAPosicionar.remove((Integer) navio.getTamanho());
    }

    // Método para abater um navio (todos os quadrados foram atingidos)
    protected void navioAbatido(Navio navio) {
        this.naviosOperantes.remove(navio);
    }

    // Lógica para verificar se jogador perdeu
    protected boolean perdeu() {
        return this.naviosOperantes.size() == 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
