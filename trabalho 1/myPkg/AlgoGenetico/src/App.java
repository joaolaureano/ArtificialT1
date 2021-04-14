
import java.util.Random;
public class App {

    public static int[][] intermediario = new int[Configurations.geracoes][Configurations.SIZE + 1];

    public static void main(String[] args) {
        int indexMelhor = 0;
        Populacao.init();
        Random rand = new Random();
        for (int x = 0; x < Configurations.rodadas; x++) {
            System.out.println("\nRodada " + x);
            Aptidao.calcularAptidao();
            Populacao.printPopulacao();
            indexMelhor = Populacao.melhorIndividuo();

            if (Solucao.verificarSolucao(indexMelhor))
                return;

            Crossover.crossover();
            Populacao.setPopulacoes(intermediario);

            if (rand.nextInt(5) == 0)
                Mutacao.mutacao();

        }
        Aptidao.calcularAptidao();
        Populacao.printPopulacao();
        System.out.println("Nao foi possivel encontrar a solucao perfeita.");
        System.out.println("Melhor solucao encontrada: ");
            for (int j = 0; j < Configurations.SIZE; j++) {
                System.out.print(Populacao.getPopulacoes()[indexMelhor][j] + " ");
            }
            System.out.println("APT: " + Populacao.getPopulacoes()[indexMelhor][Configurations.INDEX_APTIDAO]);
        return;
    }

}