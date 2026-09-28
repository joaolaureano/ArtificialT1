package myPkg.AlgoGenetico.src;

import java.util.Random;

public class Mutacao {

    public static void mutacao() {
        Log.addToWhole_log("MUTACAO.\n" , 1);
        Random rand = new Random();

        int qnt = rand.nextInt(Configurations.NUM_MUTACAO) + 1;
        for (int i = 0; i < qnt; i++) {
            // indice 0 guarda o melhor individuo (elitismo) e nao deve ser mutado
            int individuo = 1 + rand.nextInt(Configurations.geracoes - 1);
            int posicao1 = rand.nextInt(Configurations.SIZE);
            int posicao2 = rand.nextInt(Configurations.SIZE);
            while (posicao1 == posicao2)
                posicao2 = rand.nextInt(Configurations.SIZE);

            Log.addToWhole_log("\nVALOR ANTES DA MUTACAO:\t\t", 1);
            Populacao.printCromossomo(individuo);

            int valor_posicao1 = Populacao.getPopulacoes()[individuo][posicao1];
            int valor_posicao2 = Populacao.getPopulacoes()[individuo][posicao2];

            Populacao.setPopulacaoPosicao(individuo, posicao2, valor_posicao1);
            Populacao.setPopulacaoPosicao(individuo, posicao1, valor_posicao2);

            Log.addToWhole_log("VALOR DEPOIS DA MUTACAO:\t" , 1);
            Populacao.printCromossomo(individuo);
        }
    }
}
