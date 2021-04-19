package myPkg.AlgoGenetico.src;



import java.util.Random;

public class App {

    public static int[][] intermediario;

    public static void algoritmo() {
        intermediario = new int[Configurations.geracoes][Configurations.SIZE + 1];
        int indexMelhor = 0;
        Populacao.init();
        Random rand = new Random();
        for (int x = 0; x < Configurations.rodadas; x++) {
            Log.addToWhole_log("\nRodada " + (x + 1) + "\n", 1);
            Aptidao.calcularAptidao();
            Populacao.printPopulacao(1);
            indexMelhor = Populacao.melhorIndividuo();

            if (Solucao.verificarSolucao(indexMelhor))
                return;

            Crossover.crossover();
            Populacao.setPopulacoes(intermediario);

            if (rand.nextInt(5) == 0)
                Mutacao.mutacao();
        }

        Aptidao.calcularAptidao();
        Populacao.printPopulacao(2);
        Log.addToWhole_log("Nao foi possivel encontrar a solucao perfeita.\n", 2);
        Log.addToWhole_log("Melhor solucao encontrada: \n", 2);
        Log.addToWhole_log("C" + indexMelhor + ": ", 2);
        for (int j = 0; j < Configurations.SIZE; j++) {
            Log.addToWhole_log(Populacao.getPopulacoes()[indexMelhor][j] + " ", 2);
        }
        Log.addToWhole_log("APT: " + Populacao.getPopulacoes()[indexMelhor][Configurations.INDEX_APTIDAO], 2);
        return;
    }
}