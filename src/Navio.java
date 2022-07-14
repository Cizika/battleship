import java.util.ArrayList;

public class Navio {
    private final ArrayList<Quadrado> corpo;
    private int tamanho;
    private boolean vertical;

    public Navio(Quadrado proa, int tamanho) {

        // Estabelecendo Proa do Navio (Cabeça)
        this.corpo = new ArrayList<>();
        proa.setProa();
        this.corpo.add(proa);

        // Orientação Vertical
        this.vertical = true;

        // Estabelecendo tamanho
        this.tamanho = tamanho;
    }

    public Navio(Quadrado proa, int tamanho, boolean orientacao) {
        // Estabelecendo Proa do Navio (Cabeça)
        this.corpo = new ArrayList<>();
        proa.setProa();
        this.corpo.add(proa);

        // Estabelecendo orientação
        this.vertical = orientacao;

        // Estabelecendo tamanho
        this.tamanho = tamanho;
    }

    // Verifica se o Navio ainda está operante, isto é, ainda há algum corpo operante/vivo
    public boolean isOperante() {
        for (Quadrado quadrado : this.corpo)
            if (!quadrado.isAtingido())
                return true;
        return false;
    }

    // Método para adicionar um Quadrado como Casco/Corpo do navio
    public void adicionarCorpo(Quadrado quadrado) {
        quadrado.setCasco();
        this.corpo.add(quadrado);
    }

    // Método para trocar um Quadrado do Corpo do Navio por outro
    public void trocarCorpo(Quadrado novoCorpo, int index) {
        this.corpo.get(index).setAgua();
        novoCorpo.setCasco();
        this.corpo.set(index, novoCorpo);
    }

    public ArrayList<Quadrado> getCorpo() {
        return corpo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Quadrado getProa() {
        return this.corpo.get(0);
    }

    // Método para rotacionar Navio
    public void rotacionar() {
        // Aplicando Rotação
        this.vertical = !this.vertical;
    }

    // Verifica se o Navio possui um Quadrado em seu corpo
    public boolean containsQuadrado(Quadrado quadrado) {
        for (Quadrado corpoNavio : this.corpo) {
            if (corpoNavio.equals(quadrado))
                return true;
        }
        return false;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Navio) {
            Navio navio = (Navio) o;
            return navio.getProa().equals(this.getProa());
        }
        return false;
    }

}
