import java.util.ArrayList;

public abstract class Jogador {
    private int naviosAPoscionar;
    private ArrayList<Navio> naviosOperantes;
    private String nome;

    public Jogador() {
        this.nome = "";
        this.naviosOperantes = new ArrayList<>();
        this.naviosAPoscionar = 5;
    }

    public Jogador(String nome, boolean myTurn) {
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

    public Navio getNavioByParte(Quadrado parte){
        for(Navio navio: this.naviosOperantes){
            if(navio.containsQuadrado(parte))
                return navio;
        }
        return null;
    }

    protected void tornarNavioOperante(Navio navio){
        this.naviosOperantes.add(navio);
        this.naviosAPoscionar--;
    }

    protected void navioAbatido(Navio navio){
        this.naviosOperantes.remove(navio);
    }

    protected boolean perdeu(){
        return this.naviosOperantes.size() == 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
