

import java.util.Random;

public class Mutacao {

    public static void mutacao() {
        System.out.println();
        System.out.println("MUTACAO.");
        Random rand = new Random();

        int qnt = rand.nextInt(Configurations.NUM_MUTACAO) + 1;
        for (int i = 0; i < qnt; i++) {
            int individuo = rand.nextInt(Configurations.geracoes);
            int posicao1 = rand.nextInt(Configurations.SIZE);
            int posicao2 = rand.nextInt(Configurations.SIZE);
            while (posicao1 == posicao2)
                posicao2 = rand.nextInt(Configurations.SIZE);

            System.out.print("VALOR ANTES DA MUTACAO:\t\t");
            Populacao.printCromossomo(individuo);

            int valor_posicao1 = Populacao.getPopulacoes()[individuo][posicao1];
            int valor_posicao2 = Populacao.getPopulacoes()[individuo][posicao2];

            Populacao.setPopulacaoPosicao(individuo, posicao2, valor_posicao1);
            Populacao.setPopulacaoPosicao(individuo, posicao1, valor_posicao2);

            System.out.print("VALOR DEPOIS DA MUTACAO:\t");
            Populacao.printCromossomo(individuo);

            System.out.println();
        }
    }
}
