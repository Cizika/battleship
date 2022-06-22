public abstract class Jogador {
    protected int naviosOperantes;
    protected int naviosAPoscionar;
    private String nome;

    public Jogador() {
        this.nome = "";
        this.naviosOperantes = 0;
        this.naviosAPoscionar = 5;
    }

    public Jogador(String nome) {
        this.nome = nome;
        this.naviosOperantes = 0;
        this.naviosAPoscionar = 5;
    }

    protected void navioPoscionado(){
        this.naviosAPoscionar--;
        this.naviosOperantes++;
    }

    protected void navioAbatido(){
        this.naviosOperantes--;
    }

    protected boolean perdeu(){
        return this.naviosOperantes == 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
