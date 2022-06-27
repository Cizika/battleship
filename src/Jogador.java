import java.util.ArrayList;

public abstract class Jogador {
    private final ArrayList<Navio> naviosOperantes;
    private int naviosAPoscionar;
    private String nome;

    public Jogador() {
        this.nome = "";
        this.naviosOperantes = new ArrayList<>();
        this.naviosAPoscionar = 5;
    }

    public Jogador(String nome) {
        this.nome = nome;
        this.naviosOperantes = new ArrayList<>();
        this.naviosAPoscionar = 5;
    }

    public int getNaviosAPosicionar() {
        return naviosAPoscionar;
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
        this.naviosAPoscionar--;
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
