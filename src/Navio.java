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

        this.vertical = orientacao;

        this.tamanho = tamanho;
    }

    public boolean isOperante() {
        for (Quadrado quadrado : this.corpo)
            if (!quadrado.isAtingido())
                return true;
        return false;
    }

    public void adicionarCorpo(Quadrado quadrado) {
        quadrado.setCasco();
        this.corpo.add(quadrado);
    }

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

    public void rotacionar() {
        // Aplicando Rotação
        this.vertical = !this.vertical;
    }

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
