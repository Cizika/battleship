import java.util.Random;

public class Computador extends Jogador {

    public Computador() {
        super();

        // Gerando nome aleatório ao Computador
        String[] nomes = {"NoobMaster69", "Linus Torvalds", "Minion", "Aquaman", "Vin Diesel", "Olivia Rodrigo"};
        this.setNome(nomes[new Random().nextInt(nomes.length)]);
    }
}
