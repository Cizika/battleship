import java.util.Random;

public class Computador extends Jogador{

    public Computador() {
        super();

        // Gerando nome aleatório ao Computador
        String[] nomes = {"NoobMaster69", "Cizika", "Manzato", "Arts", "PSGzalez"};
        this.setNome(nomes[new Random().nextInt(nomes.length)]);
    }
}
